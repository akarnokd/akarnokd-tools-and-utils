/*
 * Copyright 2012-2014 David Karnok
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

import hu.akarnokd.utils.generator.CodeCreator;
import hu.akarnokd.utils.lang.Action1E;
import hu.akarnokd.utils.lang.Action2E;
import hu.akarnokd.utils.lang.Func1E;
import hu.akarnokd.utils.lang.ReflectionUtils;
import hu.akarnokd.utils.sequence.SequenceUtils;
import ix.internal.operators.Interactive;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func1;
import edu.umd.cs.findbugs.annotations.CheckForNull;
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
		
		List<Field> fields = ReflectionUtils.allFields(clazz, SQLColumn.class);
		// order them according to their index
		Collections.sort(fields, new Comparator<Field>() {
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
		
		for (Field f : fields) {
			String fn = fieldName(f);

			if (f.isAnnotationPresent(SQLID.class)) {
				upd2.add(fn);
			} else {
				upd.add(fn);
				ins.add(fn);
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
		
		this.create = CodeCreator.createConstructor(clazz);
		
		this.select = DBCodeCreator.createSelect(clazz);
		this.insert = DBCodeCreator.createInsert(clazz);
		this.update = DBCodeCreator.createUpdate(clazz);
		this.delete = DBCodeCreator.createDelete(clazz);

		sqlResult = new SQLResult<T>() {
			@Override
			public T call(ResultSet t) throws SQLException {
				T r = create.call();
				select.call(t, r);
				return r;
			}
		};

	}
	/**
	 * The associated table.
	 * @return the associated table
	 */
	public String table() {
		return table;
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
	 * Selects a single from the database filtered by the
	 * given where clause and parameters.
	 * @param db the database connection
	 * @param where the WHERE clause without the keyword WHERE
	 * @param params the optional parameters
	 * @return the first item or null if not found
	 * @throws SQLException on error
	 */
	@CheckForNull
	public T selectFirst(@NonNull DB db, @NonNull CharSequence where, Object... params) throws SQLException {
		List<T> list = selectSome(db, where, params);
		return list.isEmpty() ? null : list.get(0);
	}
	/**
	 * Selects a single from the database filtered by the
	 * given where clause and parameters.
	 * @param db the database connection
	 * @param where the WHERE clause without the keyword WHERE
	 * @param params the optional parameters
	 * @return the first item or null if not found
	 * @throws SQLException on error
	 */
	@CheckForNull
	public T selectFirst(@NonNull DB db, @NonNull CharSequence where, Iterable<?> params) throws SQLException {
		List<T> list = selectSome(db, where, params);
		return list.isEmpty() ? null : list.get(0);
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
	 * Insert a sequence of values into an auto-increment table without
	 * retrieveing the new identifiers.
	 * @param db the database connection
	 * @param values the values to insert
	 * @throws SQLException on error
	 */
	public void insertBatch(@NonNull DB db, Iterable<T> values) throws SQLException {
		try (PreparedStatement pstmt = db.prepare(insertSql)) {
			for (T v : values) {
				insert.call(pstmt, v);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		}
	}
	/**
	 * Deletes the record which is identified through the value.
	 * @param db the database connection
	 * @param value the record sample
	 * @return the changed record count
	 * @throws SQLException on error
	 */
	public int deleteOne(@NonNull DB db, T value) throws SQLException {
		try (PreparedStatement pstmt = db.prepare(deleteSql)) {
			delete.call(pstmt, value);
			return pstmt.executeUpdate();
		}
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
				if (insert.call(v)) {
					long id = db.insertAuto(insertSql, this.insert, v);
					setId.call(v, id);
				} else {
					update.call(pstmt, v);
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
				if (insert.call(v)) {
					update.call(pstmtInsert, v);
					pstmtInsert.addBatch();
				} else {
					update.call(pstmtUpdate, v);
					pstmtUpdate.addBatch();
				}
			}
			pstmtInsert.executeBatch();
			pstmtUpdate.executeBatch();
		}
	}
	/**
	 * Select a single value into an existing object,
	 * which should have its @SQLID fields prepared.
	 * @param db the database connection
	 * @param out the output object
	 * @return true if the object was loaded
	 * @throws SQLException on error
	 */
	public boolean selectInto(@NonNull DB db, T out) throws SQLException {
		try (PreparedStatement pstmt = db.prepare(selectOneSql)) {
			delete.call(pstmt, out);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					select.call(rs, out);
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Select all records and place them into the output collection.
	 * @param db the database connection
	 * @param coll the output collection
	 * @throws SQLException on error
	 */
	public void selectAllInto(@NonNull DB db, Collection<? super T> coll) throws SQLException {
		db.query(selectSql, SequenceUtils.into(coll, sqlResult));
	}
	/**
	 * Select some records based on a where clause (without the WHERE keyword)
	 * and place the objects in the collection.
	 * @param db he database connection
	 * @param coll the output colllection
	 * @param where the where clause without WHERE
	 * @param params the parameters
	 * @throws SQLException on error
	 */
	public void selectSomeInto(@NonNull DB db, Collection<? super T> coll, String where, Object... params) throws SQLException {
		db.query(selectSql + " WHERE " + where, SequenceUtils.into(coll, sqlResult), params);
		
	}
	/**
	 * Select some records based on a where clause (without the WHERE keyword)
	 * and place the objects in the collection.
	 * @param db he database connection
	 * @param coll the output colllection
	 * @param where the where clause without WHERE
	 * @param params the parameters
	 * @throws SQLException on error
	 */
	public void selectSomeInto(@NonNull DB db, Collection<? super T> coll, String where, Iterable<?> params) throws SQLException {
		db.query(selectSql + " WHERE " + where, SequenceUtils.into(coll, sqlResult), params);
	}
	/**
	 * Wraps the given action into an SQLException-throwing action
	 * which extracts a record from the query.
	 * @param action the action to wrap
	 * @return the wrapped action
	 */
	@NonNull
	protected Action1E<ResultSet, SQLException> wrap(@NonNull final Action1<? super T> action) {
		return new Action1E<ResultSet, SQLException>() {
			@Override
			public void call(ResultSet t) throws SQLException {
				action.call(sqlResult.call(t));
			}
		};
	}
	/**
	 * Returns all records and hands them over one-by-one
	 * to the given consumer callback.
	 * @param db the database connection
	 * @param consumer the consumner callback
	 * @throws SQLException on error
	 */
	public void selectAll(@NonNull DB db, @NonNull Action1<? super T> consumer) throws SQLException {
		db.query(selectSql, wrap(consumer));
	}
	/**
	 * Returns some records and hands them over one-by-one
	 * to the given consumer callback.
	 * @param db the database connection
	 * @param consumer the consumner callback
	 * @param where the where clause without the WHERE keyword
	 * @param params the parameters
	 * @throws SQLException on error
	 */	
	public void selectSome(@NonNull DB db, @NonNull Action1<? super T> consumer, String where, Object... params) throws SQLException {
		db.query(selectSql + " WHERE " + where, wrap(consumer), params);
	}
	/**
	 * Returns some records and hands them over one-by-one
	 * to the given consumer callback.
	 * @param db the database connection
	 * @param consumer the consumner callback
	 * @param where the where clause without the WHERE keyword
	 * @param params the parameters
	 * @throws SQLException on error
	 */	
	public void selectSome(@NonNull DB db, @NonNull Action1<? super T> consumer, String where, Iterable<?> params) throws SQLException {
		db.query(selectSql + " WHERE " + where, wrap(consumer), params);
	}
}
