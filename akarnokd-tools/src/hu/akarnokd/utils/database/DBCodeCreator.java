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
import hu.akarnokd.utils.lang.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Time;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

/**
 * Generate code for the database access.
 * @author akarnokd, 2013.11.02.
 */
public final class DBCodeCreator {
	/** Utility class. */
	private DBCodeCreator() { }
	/** Compare fields based on their SQLColumn index annotation. */
	public static final Comparator<Field> FIELD_SQLCOLUMN_COMPARE = new Comparator<Field>() {
		@Override
		public int compare(Field o1, Field o2) {
			return Integer.compare(o1.getAnnotation(SQLColumn.class).index(), o2.getAnnotation(SQLColumn.class).index());
		}
	};
	/**
	 * Generates a callback which fills the fields of the class annotated by SQLColumn.
	 * @param <T> the target type
	 * @param clazz the class to use
	 * @return the action
	 */
	@SuppressWarnings("unchecked")
	public static <T> SQLLoad<T> createSelect(Class<T> clazz) {
		try {
			ClassPool pool = CodeCreator.POOL;

			String classname = clazz.getName() + "$Select";
			
			// generate once
			if (pool.getOrNull(classname) != null) {
				return (SQLLoad<T>)Class.forName(classname).newInstance();
			}

			pool.importPackage(DB.class.getPackage().getName());
			
			CtClass c = pool.makeClass(classname);
			c.setModifiers(Modifier.FINAL);
			c.setModifiers(Modifier.PUBLIC);
			c.addInterface(pool.get(SQLLoad.class.getName()));
			
			StringBuilder b = new StringBuilder();
			
			b.append("\r\npublic final void call(Object t0, Object u0) throws java.sql.SQLException {\r\n");
			b.append("\tjava.sql.ResultSet t = (java.sql.ResultSet)t0;\r\n");
			b.append("\t").append(clazz.getName()).append(" u = (").append(clazz.getName()).append(")u0;\r\n");
			
			List<Field> fields = ReflectionUtils.allFields(clazz, SQLColumn.class);
			Collections.sort(fields, FIELD_SQLCOLUMN_COMPARE);
			
			int p = 1;
			for (Field f : fields) {
				Class<?> ft = f.getType();
				b.append("\tu.").append(f.getName()).append(" = ");
				if (byte[].class.equals(ft)) {
					b.append("t.getBytes(").append(p).append(")");
				} else
				if (Boolean.TYPE.equals(ft)) {
					b.append("t.getBoolean(").append(p).append(")");
				} else
				if (Byte.TYPE.equals(ft)) {
					b.append("t.getByte(").append(p).append(")");
				} else
				if (Short.TYPE.equals(ft)) {
					b.append("t.getShort(").append(p).append(")");
				} else
				if (Integer.TYPE.equals(ft)) {
					b.append("t.getInt(").append(p).append(")");
				} else
				if (Long.TYPE.equals(ft)) {
					b.append("t.getLong(").append(p).append(")");
				} else
				if (Float.TYPE.equals(ft)) {
					b.append("t.getFloat(").append(p).append(")");
				} else
				if (Double.TYPE.equals(ft)) {
					b.append("t.getDouble(").append(p).append(")");
				} else
				if (String.class.equals(ft)) {
					b.append("t.getString(").append(p).append(")");
				} else
				if (Boolean.class.equals(ft)) {
					b.append("DB.getBoolean(t, ").append(p).append(")");
				} else
				if (Byte.class.equals(ft)) {
					b.append("DB.getByte(t, ").append(p).append(")");
				} else
				if (Short.class.equals(ft)) {
					b.append("DB.getShort(t, ").append(p).append(")");
				} else
				if (Integer.class.equals(ft)) {
					b.append("DB.getInt(t, ").append(p).append(")");
				} else
				if (Long.class.equals(ft)) {
					b.append("DB.getLong(t, ").append(p).append(")");
				} else
				if (Float.class.equals(ft)) {
					b.append("DB.getFloat(t, ").append(p).append(")");
				} else
				if (Double.class.equals(ft)) {
					b.append("DB.getDouble(t, ").append(p).append(")");
				} else
				if (Time.class.equals(ft)) {
					b.append("t.getTime(").append(p).append(")");
				} else
				if (java.sql.Date.class.equals(ft)) {
					b.append("t.getDate(").append(p).append(")");
				} else
				if (java.sql.Timestamp.class.equals(ft)) {
					b.append("t.getTimestamp(").append(p).append(")");
				} else
				if (LocalTime.class.equals(ft)) {
					b.append("DB.getTime(t, ").append(p).append(")");
				} else
				if (LocalDate.class.equals(ft)) {
					b.append("DB.getDay(t, ").append(p).append(").toLocalDate()");
				} else
				if (DateTime.class.equals(ft)) {
					b.append("DB.getDateTime(t, ").append(p).append(")");
				} else
				if (DateMidnight.class.equals(ft)) {
					b.append("DB.getDay(t, ").append(p).append(")");
				} else
				if (ft.isEnum()) {
					b.append(ft.getName()).append(".values()[t.getInt(").append(p).append(")]");
				} else {
					throw new IllegalArgumentException(f.getName() + " of type " + ft + " not supported");
				}
				b.append(";\r\n");
				p++;
			}
			
			b.append("}\r\n");

//			LOG.debug(b.toString());

			c.addMethod(CtMethod.make(b.toString(), c));
			
			
			return (SQLLoad<T>)c.toClass().newInstance();
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}
	/**
	 * Generate an INSERT callback for the given SQLColumn annotated class which
	 * inserts all but the SQLID annotated fields.
	 * @param <T> the target type
	 * @param clazz the target class
	 * @return the save callback instance
	 */
	@SuppressWarnings("unchecked")
	public static <T> SQLSave<T> createInsert(Class<T> clazz) {
		try {
			ClassPool pool = CodeCreator.POOL;
			String classname = clazz.getName() + "$Insert";
			
			// generate once
			if (pool.getOrNull(classname) != null) {
				return (SQLSave<T>)Class.forName(classname).newInstance();
			}

			pool.importPackage(DB.class.getPackage().getName());
			
			CtClass c = pool.makeClass(classname);
			c.setModifiers(Modifier.FINAL);
			c.setModifiers(Modifier.PUBLIC);
			c.addInterface(pool.get(SQLSave.class.getName()));
			
			StringBuilder b = new StringBuilder();

			b.append("\r\npublic final void call(Object t0, Object u0) throws java.sql.SQLException {\r\n");
			b.append("\tjava.sql.PreparedStatement t = (java.sql.PreparedStatement)t0;\r\n");
			b.append("\t").append(clazz.getName()).append(" u = (").append(clazz.getName()).append(")u0;\r\n");
			b.append("\tDBParams p = new DBParams(t);\r\n");
			List<Field> fields = ReflectionUtils.allFields(clazz, SQLColumn.class);
			Collections.sort(fields, FIELD_SQLCOLUMN_COMPARE);

			Field idField = ReflectionUtils.declaredField(clazz, SQLID.class);
			if (idField == null) {
				throw new IllegalArgumentException("No field with @SQLID annotation found.");
			}
			
			for (Field f : fields) {
				if (!f.isAnnotationPresent(SQLID.class)) {
					if (f.getType().isEnum()) {
						b.append("\tp.add(u.").append(f.getName()).append(".ordinal());\r\n");
					} else {
						b.append("\tp.add(u.").append(f.getName()).append(");\r\n");
					}
				}
			}
			
			b.append("}\r\n");

//			LOG.debug(b.toString());

			c.addMethod(CtMethod.make(b.toString(), c));
			

			return (SQLSave<T>)c.toClass().newInstance();
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}
	/**
	 * Generate an UPDATE callback for the given SQLColumn annotated class.
	 * @param <T> the target type
	 * @param clazz the target class
	 * @return the save callback instance
	 */
	@SuppressWarnings("unchecked")
	public static <T> SQLSave<T> createUpdate(Class<T> clazz) {
		try {
			ClassPool pool = CodeCreator.POOL;
			String classname = clazz.getName() + "$Update";
			
			// generate once
			if (pool.getOrNull(classname) != null) {
				return (SQLSave<T>)Class.forName(classname).newInstance();
			}

			pool.importPackage(DB.class.getPackage().getName());
			
			CtClass c = pool.makeClass(classname);
			c.setModifiers(Modifier.FINAL);
			c.setModifiers(Modifier.PUBLIC);
			c.addInterface(pool.get(SQLSave.class.getName()));
			
			StringBuilder b = new StringBuilder();

			b.append("\r\npublic final void call(Object t0, Object u0) throws java.sql.SQLException {\r\n");
			b.append("\tjava.sql.PreparedStatement t = (java.sql.PreparedStatement)t0;\r\n");
			b.append("\t").append(clazz.getName()).append(" u = (").append(clazz.getName()).append(")u0;\r\n");
			b.append("\tDBParams p = new DBParams(t);\r\n");
			List<Field> fields = ReflectionUtils.allFields(clazz, SQLColumn.class);
			Collections.sort(fields, FIELD_SQLCOLUMN_COMPARE);

			Field idField = ReflectionUtils.declaredField(clazz, SQLID.class);
			if (idField == null) {
				throw new IllegalArgumentException("No field with @SQLID annotation found.");
			}
			
			for (Field f : fields) {
				if (!f.isAnnotationPresent(SQLID.class)) {
					if (f.getType().isEnum()) {
						b.append("\tp.add(u.").append(f.getName()).append(".ordinal());\r\n");
					} else {
						b.append("\tp.add(u.").append(f.getName()).append(");\r\n");
					}
				}
			}
			for (Field f : fields) {
				if (f.isAnnotationPresent(SQLID.class)) {
					if (f.getType().isEnum()) {
						b.append("\tp.add(u.").append(f.getName()).append(".ordinal());\r\n");
					} else {
						b.append("\tp.add(u.").append(f.getName()).append(");\r\n");
					}
				}
			}
			
			b.append("}\r\n");

//			LOG.debug(b.toString());

			c.addMethod(CtMethod.make(b.toString(), c));
			

			return (SQLSave<T>)c.toClass().newInstance();
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}
	/**
	 * Generate an DELETE callback for the given SQLColumn annotated class.
	 * @param <T> the target type
	 * @param clazz the target class
	 * @return the save callback instance
	 */
	@SuppressWarnings("unchecked")
	public static <T> SQLSave<T> createDelete(Class<T> clazz) {
		try {
			ClassPool pool = CodeCreator.POOL;
			String classname = clazz.getName() + "$Delete";
			
			// generate once
			if (pool.getOrNull(classname) != null) {
				return (SQLSave<T>)Class.forName(classname).newInstance();
			}

			pool.importPackage(DB.class.getPackage().getName());
			
			CtClass c = pool.makeClass(classname);
			c.setModifiers(Modifier.FINAL);
			c.setModifiers(Modifier.PUBLIC);
			c.addInterface(pool.get(SQLSave.class.getName()));
			
			StringBuilder b = new StringBuilder();

			b.append("\r\npublic final void call(Object t0, Object u0) throws java.sql.SQLException {\r\n");
			b.append("\tjava.sql.PreparedStatement t = (java.sql.PreparedStatement)t0;\r\n");
			b.append("\t").append(clazz.getName()).append(" u = (").append(clazz.getName()).append(")u0;\r\n");
			b.append("\tDBParams p = new DBParams(t);\r\n");
			List<Field> fields = ReflectionUtils.allFields(clazz, SQLColumn.class);
			Collections.sort(fields, FIELD_SQLCOLUMN_COMPARE);

			Field idField = ReflectionUtils.declaredField(clazz, SQLID.class);
			if (idField == null) {
				throw new IllegalArgumentException("No field with @SQLID annotation found.");
			}
			
			for (Field f : fields) {
				if (f.isAnnotationPresent(SQLID.class)) {
					if (f.getType().isEnum()) {
						b.append("\tp.add(u.").append(f.getName()).append(".ordinal());\r\n");
					} else {
						b.append("\tp.add(u.").append(f.getName()).append(");\r\n");
					}
				}
			}
			
			b.append("}\r\n");

//			LOG.debug(b.toString());

			c.addMethod(CtMethod.make(b.toString(), c));
			

			return (SQLSave<T>)c.toClass().newInstance();
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}
}
