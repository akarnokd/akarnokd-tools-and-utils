/*
 * Copyright 2012-2013 David Karnok
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.akarnokd.utils.database;

import hu.akarnokd.reactive4java.base.Action2;
import hu.akarnokd.reactive4java.base.Action2E;
import hu.akarnokd.reactive4java.base.Func0;
import hu.akarnokd.reactive4java.base.Func1;
import hu.akarnokd.reactive4java.base.Func1E;
import hu.akarnokd.reactive4java.interactive.Interactive;
import hu.akarnokd.utils.lang.ReflectionUtils;
import hu.akarnokd.utils.sequence.SequenceUtils;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Routines that handle the annotated POJOs with simple auto-ids.
 * @author akarnokd, 2013.11.01.
 * @param <T> the record class
 */
public class DBPojo<T> {
	/** Select query. */
	protected final String selectSql;
	/** Select query. */
	protected final String selectOneSql;
	/** Insert query. */
	protected final String insertSql;
	/** Insert query with all fields. */
	protected final String insertExactSql;
	/** Update query. */
	protected final String updateSql;
	/** Delete query. */
	protected final String deleteSql;
	/** Select callback. */
	protected final Action2E<ResultSet, T, SQLException> select;
	/** Insert callback. */
	protected final Action2E<PreparedStatement, T, SQLException> insert;
	/** Update callback. */
	protected final Action2E<PreparedStatement, T, SQLException> update;
	/** Update callback. */
	protected final Action2E<PreparedStatement, T, SQLException> delete;
	/** Create function. */
	protected final Func0<T> create;
	/** Fields. */
	private List<Field> allFields;
	/** Fields. */
	private List<Field> idFields;
	/** Fields. */
	private List<Field> nonIdFields;
	/** The SQL result converter function. */
	private Func1E<ResultSet, T, SQLException> sqlResult;
	/** The table name. */
	protected String table;
	/**
	 * Constructor, prepares the structure-dependant internal objects.
	 * @param clazz the POJO class.
	 */
	public DBPojo(Class<T> clazz) {
		// Figure out the table name
		SQLTable atable = clazz.getAnnotation(SQLTable.class);
		table = atable != null ? atable.value() : clazz.getSimpleName();
		
		allFields = ReflectionUtils.allFields(clazz, SQLColumn.class);
		idFields = new ArrayList<>();
		nonIdFields = new ArrayList<>();
		// order them according to their index
		Collections.sort(allFields, new Comparator<Field>() {
			@Override
			public int compare(Field o1, Field o2) {
				return Integer.compare(
						o1.getAnnotation(SQLColumn.class).index(), 
						o2.getAnnotation(SQLColumn.class).index());
			}
		});
		
		List<String> sel = new ArrayList<>();
		List<String> ins = new ArrayList<>();
		List<String> upd = new ArrayList<>();
		List<String> upd2 = new ArrayList<>();
		
		for (Field f : allFields) {
			String fn = fieldName(f);

			if (f.isAnnotationPresent(SQLID.class)) {
				upd2.add(fn);
				idFields.add(f);
			} else {
				upd.add(fn);
				ins.add(fn);
				nonIdFields.add(f);
			}
			sel.add(fn);
		}
		
		selectSql = "SELECT " + SequenceUtils.join(sel, ", ")
				+ " FROM " + table + " ";
		
		selectOneSql = selectSql + " WHERE " + SequenceUtils.join(upd2, " AND ", "%s = ?");
		
		insertSql = "INSERT INTO " + table + " ("
				+ SequenceUtils.join(ins, ", ")
				+ ") VALUES ("
				+ SequenceUtils.join(Interactive.repeat("?", ins.size()), ",")
				+ ")";

		insertExactSql = "INSERT INTO " + table + " ("
				+ SequenceUtils.join(Interactive.concat(ins, upd2), ", ")
				+ ") VALUES ("
				+ SequenceUtils.join(Interactive.repeat("?", sel.size()), ",")
				+ ")";
		
		updateSql = "UPDATE " + table + " SET "
				+ SequenceUtils.join(upd, ", ", "%s = ?")
				+ " WHERE " + SequenceUtils.join(upd2, " AND ", "%s = ?");
		
		deleteSql = "DELETE FROM " + table + " WHERE "
				 + SequenceUtils.join(upd2, " AND ", "%s = ?");
		
		this.create = findCreator(clazz);
		
		this.select = findSelect(clazz);
		this.insert = findInsert(clazz);
		this.update = findUpdate(clazz);

		sqlResult = new SQLResult<T>() {
			@Override
			public T invoke(ResultSet t) throws SQLException {
				T r = create.invoke();
				select.invoke(t, r);
				return r;
			}
		};

		delete = defaultDelete();
	}
	/**
	 * Finds or creates a default select function callback.
	 * @param clazz the target class
	 * @return the function callback
	 */
	@NonNull
	protected Action2E<ResultSet, T, SQLException> findSelect(Class<T> clazz) {
		Field fselect = ReflectionUtils.declaredField(clazz, SQLSelect.class);
		if (fselect == null) {
			return defaultSelect();
		}
		return ReflectionUtils.get(fselect);
	}
	/**
	 * Finds or creates a default insert function callback.
	 * @param clazz the target class
	 * @return the function callback
	 */
	@NonNull
	protected Action2E<PreparedStatement, T, SQLException> findInsert(Class<T> clazz) {
		Field finsert = ReflectionUtils.declaredField(clazz, SQLInsert.class);
		
		if (finsert == null) {
			return defaultInsert();
		}
		return ReflectionUtils.get(finsert);
	}
	/**
	 * Finds or creates a default update function callback.
	 * @param clazz the target class
	 * @return the function callback
	 */
	protected Action2E<PreparedStatement, T, SQLException> findUpdate(Class<T> clazz) {
		Field fupdate = ReflectionUtils.declaredField(clazz, SQLUpdate.class);
		
		if (fupdate == null) {
			return defaultUpdate();
		}
		return ReflectionUtils.get(fupdate);
	}
	/**
	 * Find a creator Func0 on the class.
	 * @param clazz the class
	 * @return the function
	 */
	protected Func0<T> findCreator(Class<T> clazz) {
		Field fcreate = ReflectionUtils.declaredField(clazz, SQLCreate.class);
		if (fcreate == null) {
			return ReflectionUtils.constructor(clazz);
		}
		return ReflectionUtils.get(fcreate);
	}
	/**
	 * Extracts the SQL field name from the field.
	 * @param f the field
	 * @return the name
	 */
	protected String fieldName(Field f) {
		SQLColumn c = f.getAnnotation(SQLColumn.class);
		if (c != null && !c.name().isEmpty()) {
			return c.name();
		}
		return f.getName();
	}
	/**
	 * Creates a default selector callback.
	 * @return the default selector callback
	 */
	protected Action2E<ResultSet, T, SQLException> defaultSelect() {
		return new Action2E<ResultSet, T, SQLException>() {
			@Override
			public void invoke(ResultSet t, T u) throws SQLException {
				int i = 1;
				for (Field f : allFields) {
					ReflectionUtils.set(f, u, t.getObject(i));
					i++;
				}
			}
		};
	}
	/**
	 * Creates a default update callback.
	 * @return the default selector callback
	 */
	protected Action2E<PreparedStatement, T, SQLException> defaultUpdate() {
		return new Action2E<PreparedStatement, T, SQLException>() {
			@Override
			public void invoke(PreparedStatement t, T u) throws SQLException {
				int i = 1;
				for (Field f : nonIdFields) {
					t.setObject(i, ReflectionUtils.get(f, u));
					i++;
				}
				for (Field f : idFields) {
					t.setObject(i, ReflectionUtils.get(f, u));
					i++;
				}
			}
		};
	}
	/**
	 * Creates a default update callback.
	 * @return the default selector callback
	 */
	protected Action2E<PreparedStatement, T, SQLException> defaultInsert() {
		return new Action2E<PreparedStatement, T, SQLException>() {
			@Override
			public void invoke(PreparedStatement t, T u) throws SQLException {
				int i = 1;
				for (Field f : nonIdFields) {
					t.setObject(i, ReflectionUtils.get(f, u));
					i++;
				}
			}
		};
	}
	/**
	 * Creates a default update callback.
	 * @return the default selector callback
	 */
	protected Action2E<PreparedStatement, T, SQLException> defaultDelete() {
		return new Action2E<PreparedStatement, T, SQLException>() {
			@Override
			public void invoke(PreparedStatement t, T u) throws SQLException {
				int i = 1;
				for (Field f : idFields) {
					t.setObject(i, ReflectionUtils.get(f, u));
					i++;
				}
			}
		};
	}
	/**
	 * Returns all records from the database.
	 * @param db the database
	 * @return the list of records.
	 * @throws SQLException on error
	 */
	@NonNull 
	public List<T> selectAll(@NonNull DB db) throws SQLException {
		return db.query(selectSql, sqlResult);
	}
	/**
	 * Selects some records from the database filtered by the
	 * given where clause and parameters.
	 * @param db the database connection
	 * @param where the WHERE clause without the keyword WHERE
	 * @param params the optional parameters
	 * @return the list of items
	 * @throws SQLException on error
	 */
	@NonNull 
	public List<T> selectSome(@NonNull DB db, @NonNull CharSequence where, Object... params) throws SQLException {
		return db.query(selectSql + " WHERE " + where, sqlResult, params);
	}
	/**
	 * Selects some records from the database filtered by the
	 * given where clause and parameters.
	 * @param db the database connection
	 * @param where the WHERE clause without the keyword WHERE
	 * @param params the optional parameters
	 * @return the list of items
	 * @throws SQLException on error
	 */
	@NonNull 
	public List<T> selectSome(@NonNull DB db, @NonNull CharSequence where, Iterable<?> params) throws SQLException {
		return db.query(selectSql + " WHERE " + where, sqlResult, params);
	}
	/**
	 * Select a single record identified by the key parameters.
	 * @param db the database connection
	 * @param keys the key parameters
	 * @return the single value or null if not found
	 * @throws SQLException one error
	 */
	@NonNull 
	public T selectOne(@NonNull DB db, Object... keys) throws SQLException {
		return db.querySingle(selectOneSql, sqlResult, keys);
	}
	/**
	 * Update a single record.
	 * @param db the database connection
	 * @param value the record
	 * @return the update count, 1 indicating success
	 * @throws SQLException on error
	 */
	public int update(@NonNull DB db, T value) throws SQLException {
		return db.update(updateSql, update, value);
	}
	/**
	 * Insert the value and return the generated long identifier.
	 * @param db the database connection
	 * @param value the record
	 * @return the new identifier
	 * @throws SQLException on error
	 */
	public long insert(@NonNull DB db, T value) throws SQLException {
		return db.insertAuto(insertSql, insert, value);
	}
	/**
	 * Insert the value with all fields used in the SQL statement.
	 * It uses the @SQLUpdate callback to fill in the insert fields.
	 * <p>May be used for inserting non-auto records.
	 * @param db the database connection
	 * @param value the record
	 * @throws SQLException on error
	 */
	public void insertAll(@NonNull DB db, T value) throws SQLException {
		db.update(insertExactSql, update, value);
	}
	/**
	 * Deletes the record which is identified through the value.
	 * @param db the database connection
	 * @param value the record sample
	 * @throws SQLException on error
	 */
	public void deleteOne(@NonNull DB db, T value) throws SQLException {
	}
	/**
	 * Deletes the record identified by the key parameters.
	 * @param db the database connection
	 * @param keys the keys
	 * @return the update count
	 * @throws SQLException on error
	 */
	public int delete(@NonNull DB db, Object... keys) throws SQLException {
		return db.update(deleteSql, keys);
	}
	/**
	 * Deletes the record which match the given WHERE clause and parameters.
	 * @param db the database connection
	 * @param where the where clause without the WHERE keyword
	 * @param params the parameters
	 * @return the update count
	 * @throws SQLException on error
	 */
	public int deleteSome(@NonNull DB db, CharSequence where, Object... params) throws SQLException {
		return db.update("DELETE FROM " + table + " WHERE " + where, params);
	}
	/**
	 * Deletes the record which match the given WHERE clause and parameters.
	 * @param db the database connection
	 * @param where the where clause without the WHERE keyword
	 * @param params the parameters
	 * @return the update count
	 * @throws SQLException on error
	 */
	public int deleteSome(@NonNull DB db, CharSequence where, Iterable<?> params) throws SQLException {
		return db.update("DELETE FROM " + table + " WHERE " + where, params);
	}
	/**
	 * Saves multiple items and uses a callback to distinguish between
	 * items to update or insert, and uses a callback to update the auto-generated
	 * long identifier on the same record.
	 * <p>Updates are performed in one batch but inserts are done one-by-one
	 * as some databases don't support batch auto-ids.</p>
	 * @param db the database connection
	 * @param items the sequence of items
	 * @param insert predicate to tell to insert
	 * @param setId the callback to set the generated auto-id
	 * @throws SQLException on error
	 */
	public void save(@NonNull DB db, 
			@NonNull Iterable<? extends T> items, 
			@NonNull Func1<? super T, Boolean> insert,
			@NonNull Action2<? super T, ? super Long> setId) throws SQLException {
		try (PreparedStatement pstmt = db.prepare(updateSql)) {
			for (T v : items) {
				if (insert.invoke(v)) {
					long id = db.insertAuto(insertSql, insert, v);
					setId.invoke(v, id);
				} else {
					update.invoke(pstmt, v);
					pstmt.addBatch();
				}
			}
			pstmt.executeBatch();
		}
	}
	/**
	 * Saves multiple items and uses a callback to distinguish between
	 * items to update or insert.
	 * @param db the database connection
	 * @param items the sequence of items
	 * @param insert predicate to tell to insert
	 * @throws SQLException on error
	 */
	public void saveExact(@NonNull DB db, 
			@NonNull Iterable<? extends T> items, 
			@NonNull Func1<? super T, Boolean> insert) throws SQLException {
		try (PreparedStatement pstmtUpdate = db.prepare(updateSql);
				PreparedStatement pstmtInsert = db.prepare(insertExactSql)) {
			for (T v : items) {
				if (insert.invoke(v)) {
					update.invoke(pstmtInsert, v);
					pstmtInsert.addBatch();
				} else {
					update.invoke(pstmtUpdate, v);
					pstmtUpdate.addBatch();
				}
			}
			pstmtInsert.executeBatch();
			pstmtUpdate.executeBatch();
		}
	}
}
