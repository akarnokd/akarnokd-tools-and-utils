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

import hu.akarnokd.utils.Base64;
import hu.akarnokd.utils.io.Closeables;
import hu.akarnokd.utils.xml.XElement;
import ix.util.Action1E;
import ix.util.Action2E;
import ix.util.CloseableIterator;
import ix.util.Func1E;
import ix.util.Func2E;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;
import javax.xml.stream.XMLStreamException;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

/**
 * Simple collection of JDBC access methods.
 * @author karnokd, 2012.09.10.
 */
public class DB implements Closeable {
	/**
	 * The database information.
	 * @author karnokd, 2012.09.10.
	 */
	public static class DBInfo {
		/** The connection URL. */
		public String connectionURL;
		/** The driver class. */
		public String driverClass;
		/** The password was encoded? */
		public boolean encodePassword;
		/** The database identifier. */
		public String id;
		/** Maximum number of connections. */
		public int maxConnection;
		/** The password. */
		public String password;
		/** The schema. */
		public String schema;
		/** The user. */
		public String user;
		/** Default constructor. */
		public DBInfo() {
			
		}
		/**
		 * Copy constructor.
		 * @param other the other object
		 */
		public DBInfo(@NonNull DBInfo other) {
			this.id = other.id;
			this.driverClass = other.driverClass;
			this.connectionURL = other.connectionURL;
			this.user = other.user;
			this.password = other.password;
			this.encodePassword = other.encodePassword;
			this.schema = other.schema;
			this.maxConnection = other.maxConnection;
		}
	}
	/** The connection data file. */
	protected static final String CONNECTION_DATA = "/db.xml";
	/** The available connection infos. */
	protected static final Map<String, DBInfo> CONNECTION_INFOS = Maps.newConcurrentMap();
	/** The name of the default connection. */
	protected static String defaultId;
	/**
	 * Sets a single parameter to the long value, used for delete auto-keyed items.
	 */
	public static final SQLAction<Long> DELETE_LONG = new SQLAction<Long>() {
		@Override
		public void call(PreparedStatement t, Long u)
				throws SQLException {
			t.setLong(1, u);
		}
	};
	/** Returns a single boolean value. */
	public static final SQLResult<Boolean> SELECT_BOOLEAN = new SQLResult<Boolean>() {
		@Override
		public Boolean call(ResultSet param1)
				throws SQLException {
			return param1.getBoolean(1);
		}
	};
	/** Returns a single boolean value. */
	public static final SQLResult<Boolean> SELECT_BOOLEAN_OPTION = new SQLResult<Boolean>() {
		@Override
		public Boolean call(ResultSet param1)
				throws SQLException {
			return getBoolean(param1, 1);
		}
	};
	/** Returns a single datemidnight value. */
	public static final SQLResult<DateMidnight> SELECT_DATEMIDNIGHT = new SQLResult<DateMidnight>() {
		@Override
		public DateMidnight call(ResultSet param1)
				throws SQLException {
			Timestamp timestamp = param1.getTimestamp(1);
			if (timestamp != null) {
				return new DateMidnight(timestamp.getTime());
			}
			return null;
		}
	};
	/** Returns a single datetime value. */
	public static final SQLResult<DateTime> SELECT_DATETIME = new SQLResult<DateTime>() {
		@Override
		public DateTime call(ResultSet param1)
				throws SQLException {
			Timestamp timestamp = param1.getTimestamp(1);
			if (timestamp != null) {
				return new DateTime(timestamp.getTime());
			}
			return null;
		}
	};
	/** Returns a single byte value. */
	public static final SQLResult<Byte> SELECT_BYTE = new SQLResult<Byte>() {
		@Override
		public Byte call(ResultSet param1)
				throws SQLException {
			return param1.getByte(1);
		}
	};
	/** Returns a single byte value or null. */
	public static final SQLResult<Byte> SELECT_BYTE_OPTION = new SQLResult<Byte>() {
		@Override
		public Byte call(ResultSet param1)
				throws SQLException {
			return DB.getByte(param1, 1);
		}
	};
	/** Returns a single short value. */
	public static final SQLResult<Short> SELECT_SHORT = new SQLResult<Short>() {
		@Override
		public Short call(ResultSet param1)
				throws SQLException {
			return param1.getShort(1);
		}
	};
	/** Returns a single short value or null. */
	public static final SQLResult<Short> SELECT_SHORT_OPTION = new SQLResult<Short>() {
		@Override
		public Short call(ResultSet param1)
				throws SQLException {
			return DB.getShort(param1, 1);
		}
	};
	/** Returns a single long value. */
	public static final SQLResult<Integer> SELECT_INT = new SQLResult<Integer>() {
		@Override
		public Integer call(ResultSet param1)
				throws SQLException {
			return param1.getInt(1);
		}
	};
	/** Returns a single long value or null. */
	public static final SQLResult<Integer> SELECT_INT_OPTION = new SQLResult<Integer>() {
		@Override
		public Integer call(ResultSet param1)
				throws SQLException {
			return DB.getInt(param1, 1);
		}
	};
	/** Returns a single long value. */
	public static final SQLResult<Long> SELECT_LONG = new SQLResult<Long>() {
		@Override
		public Long call(ResultSet param1)
				throws SQLException {
			return param1.getLong(1);
		}
	};
	/** Returns a single long value or null. */
	public static final SQLResult<Long> SELECT_LONG_OPTION = new SQLResult<Long>() {
		@Override
		public Long call(ResultSet param1)
				throws SQLException {
			return DB.getLong(param1, 1);
		}
	};
	/** Returns a single float value. */
	public static final SQLResult<Float> SELECT_FLOAT = new SQLResult<Float>() {
		@Override
		public Float call(ResultSet param1)
				throws SQLException {
			return param1.getFloat(1);
		}
	};
	/** Returns a single float value or null. */
	public static final SQLResult<Float> SELECT_FLOAT_OPTION = new SQLResult<Float>() {
		@Override
		public Float call(ResultSet param1)
				throws SQLException {
			return DB.getFloat(param1, 1);
		}
	};
	/** Returns a single double value. */
	public static final SQLResult<Double> SELECT_DOUBLE = new SQLResult<Double>() {
		@Override
		public Double call(ResultSet param1)
				throws SQLException {
			return param1.getDouble(1);
		}
	};
	/** Returns a single double value or null. */
	public static final SQLResult<Double> SELECT_DOUBLE_OPTION = new SQLResult<Double>() {
		@Override
		public Double call(ResultSet param1)
				throws SQLException {
			return DB.getDouble(param1, 1);
		}
	};
	/** Returns a single long value. */
	public static final SQLResult<String> SELECT_STRING = new SQLResult<String>() {
		@Override
		public String call(ResultSet param1)
				throws SQLException {
			return param1.getString(1);
		}
	};
	/** Returns a single timestamp value. */
	public static final SQLResult<Timestamp> SELECT_TIMESTAMP = new SQLResult<Timestamp>() {
		@Override
		public Timestamp call(ResultSet param1)
				throws SQLException {
			return param1.getTimestamp(1);
		}
	};
	static {
		URL res = DB.class.getResource(CONNECTION_DATA);
		if (res != null) {
			try {
				XElement xdbs = XElement.parseXML(res);
				defaultId = xdbs.get("default", "default");
				for (XElement xdb : xdbs.childrenWithName("database")) {
					DBInfo dbi = new DBInfo();
					
					dbi.id = xdb.get("id");
					dbi.driverClass = xdb.childValue("driver-class");
					dbi.connectionURL = xdb.childValue("connection-url");
					dbi.user = xdb.childValue("user");
					dbi.password = xdb.childValue("password");
					dbi.encodePassword = xdb.childElement("password").getBoolean("encoded", false);
					dbi.schema = xdb.childValue("schema");
					dbi.maxConnection = Integer.parseInt(xdb.childValue("max-connection"));
					
					if (dbi.password != null && !dbi.password.isEmpty() && dbi.encodePassword) {
						dbi.password = new String(Base64.decode(dbi.password), "UTF-8");
					}
					
					CONNECTION_INFOS.put(dbi.id, dbi);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
		}
	}
	/** No instances by default. */
	protected DB() { }
	/**
	 * Adds a new database info record to the common DB object.
	 * @param info the new info to add
	 */
	public static void addConnection(@NonNull DBInfo info) {
		CONNECTION_INFOS.put(info.id, new DBInfo(info));
	}
	/**
	 * Connect to the default database.
	 * @return the connection or the error
	 */
	@NonNull
	public static DB connect() {
		return connect(defaultId);
	}
	/**
	 * Wraps the given connection into a DB instance.
	 * @param conn the connection object
	 * @return the new DB instance
	 */
	@NonNull 
	public static DB connect(@NonNull Connection conn) {
		DB result = new DB();
		result.conn = conn;
		
		return result;
	}
	/** The info that created the connection. */
	@Nullable
	protected DBInfo dbi;
	/**
	 * Establish a connection to the given database.
	 * @param dbi the the database info 
	 * @return the database instance
	 */
	@NonNull
	public static DB connect(@NonNull DBInfo dbi) {
		if (dbi == null) {
			throw new IllegalArgumentException("dbi is null");
		}
		
		try {
			Class.forName(dbi.driverClass);
		} catch (ClassNotFoundException ex) {
			throw new IllegalArgumentException("Driver error: " + dbi.driverClass, ex);
		}
		
		DB result = new DB();
		try {
			result.conn = DriverManager.getConnection(dbi.connectionURL, dbi.user, dbi.password);
		} catch (SQLException ex) {
			Closeables.closeSilently(result);
			throw new IllegalArgumentException("Connection error: " + ex.toString(), ex);
		}
		try {
			result.conn.setAutoCommit(false);
		} catch (SQLException ex) {
			Closeables.closeSilently(result);
			throw new IllegalArgumentException("Connection error: " + ex.toString(), ex);
		}
		result.dbi = new DBInfo(dbi);
		
		return result;
	}
	/**
	 * Connect to the specified database.
	 * @param databaseId the database identifier
	 * @return the connection object or the exception
	 */
	@NonNull
	public static DB connect(@NonNull String databaseId) {
		DBInfo dbi = CONNECTION_INFOS.get(databaseId);
		return connect(dbi);
	}
	
	/**
	 * Execute an action under the default connection.
	 * @param action the action to execute
	 * @throws SQLException the exception raised when connecting or when executing the action
	 * @see SQLExecute
	 */
	public static void execute(
			@NonNull Action1E<? super DB, ? extends SQLException> action) throws SQLException {
		try (DB db = DB.connect()) {
			action.call(db);
		} catch (IOException ex) {
			throw new SQLException(ex);
		}
	}
	/**
	 * Execute a function under the default connection.
	 * @param <T> the return type 
	 * @param func the function to execute
	 * @return the value
	 * @throws SQLException the exception raised when connecting or when executing the action
	 * @see SQLCall
	 */
	public static <T> T execute(
			@NonNull Func1E<? super DB, ? extends T, ? extends SQLException> func) throws SQLException {
		try (DB db = DB.connect()) {
			return func.call(db);
		} catch (IOException ex) {
			throw new SQLException(ex);
		}
	}
	/**
	 * Execute an action under the given connection id.
	 * @param id the connection identifier 
	 * @param action the action to execute
	 * @throws SQLException the exception raised when connecting or when executing the action
	 * @see SQLExecute
	 */
	public static void execute(
			@NonNull String id, 
			@NonNull Action1E<? super DB, ? extends SQLException> action) throws SQLException {
		try (DB db = DB.connect(id)) {
			action.call(db);
		} catch (IOException ex) {
			throw new SQLException(ex);
		}
	}
	/**
	 * Execute a function under the given connection id.
	 * @param <T> the return type
	 * @param id the connection identifier 
	 * @param func the function to execute
	 * @return the value
	 * @throws SQLException the exception raised when connecting or when executing the action
	 * @see SQLCall
	 */
	public static <T> T execute(
			@NonNull String id, 
			@NonNull Func1E<? super DB, ? extends T, ? extends SQLException> func) throws SQLException {
		try (DB db = DB.connect(id)) {
			return func.call(db);
		} catch (IOException ex) {
			throw new SQLException(ex);
		}
	}
	/**
	 * Returns a nullable short field from the result set as a Byte object.
	 * @param rs the result set
	 * @param index the field index
	 * @return the integer, may be null
	 * @throws SQLException on error
	 */
	@Nullable
	public static Byte getByte(@NonNull ResultSet rs, int index) throws SQLException {
		byte value = rs.getByte(index);
		if (rs.wasNull()) {
			return null;
		}
		return value;
	}
	/**
	 * Returns a DateTime instance or null if the result field is null.
	 * @param rs the result set
	 * @param index the field index
	 * @return the day as DateMidnight or null.
	 * @throws SQLException on error
	 */
	@Nullable
	public static DateTime getDateTime(@NonNull ResultSet rs, int index) throws SQLException {
		java.sql.Timestamp ts = rs.getTimestamp(index);
		if (ts != null) {
			return new DateTime(ts);
		}
		return null;
	}
	/**
	 * Returns a DateMidnight instance or null if the result field is null.
	 * @param rs the result set
	 * @param index the field index
	 * @return the day as DateMidnight or null.
	 * @throws SQLException on error
	 */
	@Nullable
	public static DateMidnight getDay(@NonNull ResultSet rs, int index) throws SQLException {
		java.sql.Date d = rs.getDate(index);
		if (d != null) {
			return new DateMidnight(d);
		}
		return null;
	}
	/**
	 * Returns a nullable double field from the result set as a Double object.
	 * @param rs the result set
	 * @param index the field index
	 * @return the integer, may be null
	 * @throws SQLException on error
	 */
	@Nullable
	public static Double getDouble(@NonNull ResultSet rs, int index) throws SQLException {
		double value = rs.getDouble(index);
		if (rs.wasNull()) {
			return null;
		}
		return value;
	}
	/**
	 * Returns a nullable float field from the result set as a Float object.
	 * @param rs the result set
	 * @param index the field index
	 * @return the integer, may be null
	 * @throws SQLException on error
	 */
	@Nullable
	public static Float getFloat(@NonNull ResultSet rs, int index) throws SQLException {
		float value = rs.getFloat(index);
		if (rs.wasNull()) {
			return null;
		}
		return value;
	}
	/**
	 * Returns a nullable int field from the result set as an Integer object.
	 * @param rs the result set
	 * @param index the field index
	 * @return the integer, may be null
	 * @throws SQLException on error
	 */
	@Nullable
	public static Integer getInt(@Nonnull ResultSet rs, int index) throws SQLException {
		int value = rs.getInt(index);
		if (rs.wasNull()) {
			return null;
		}
		return value;
	}
	/**
	 * Returns a nullable long field from the result set as a Long object.
	 * @param rs the result set
	 * @param index the field index
	 * @return the integer, may be null
	 * @throws SQLException on error
	 */
	@Nullable
	public static Long getLong(@NonNull ResultSet rs, int index) throws SQLException {
		long value = rs.getLong(index);
		if (rs.wasNull()) {
			return null;
		}
		return value;
	}
	/**
	 * Returns a nullable short field from the result set as a Short object.
	 * @param rs the result set
	 * @param index the field index
	 * @return the integer, may be null
	 * @throws SQLException on error
	 */
	@Nullable
	public static Short getShort(@NonNull ResultSet rs, int index) throws SQLException {
		short value = rs.getShort(index);
		if (rs.wasNull()) {
			return null;
		}
		return value;
	}
	/**
	 * Returns a localTime instance or null if the result field is null.
	 * @param rs the result set
	 * @param index the field index
	 * @return the local time or null
	 * @throws SQLException on error
	 */
	public static LocalTime getTime(@NonNull ResultSet rs, int index) throws SQLException {
		java.sql.Time tm = rs.getTime(index);
		if (tm != null) {
			return toLocalTime(tm);
		}
		return null;
	}
	/**
	 * Returns a nullable short field from the result set as a Byte object.
	 * @param rs the result set
	 * @param columnName the colum name
	 * @return the integer, may be null
	 * @throws SQLException on error
	 */
	@Nullable
	public static Byte getByte(@NonNull ResultSet rs, String columnName) throws SQLException {
		byte value = rs.getByte(columnName);
		if (rs.wasNull()) {
			return null;
		}
		return value;
	}
	/**
	 * Returns a DateTime instance or null if the result field is null.
	 * @param rs the result set
	 * @param columnName the colum name
	 * @return the day as DateMidnight or null.
	 * @throws SQLException on error
	 */
	@Nullable
	public static DateTime getDateTime(@NonNull ResultSet rs, String columnName) throws SQLException {
		java.sql.Timestamp ts = rs.getTimestamp(columnName);
		if (ts != null) {
			return new DateTime(ts);
		}
		return null;
	}
	/**
	 * Returns a DateMidnight instance or null if the result field is null.
	 * @param rs the result set
	 * @param columnName the colum name
	 * @return the day as DateMidnight or null.
	 * @throws SQLException on error
	 */
	@Nullable
	public static DateMidnight getDay(@NonNull ResultSet rs, String columnName) throws SQLException {
		java.sql.Date d = rs.getDate(columnName);
		if (d != null) {
			return new DateMidnight(d);
		}
		return null;
	}
	/**
	 * Returns a nullable double field from the result set as a Double object.
	 * @param rs the result set
	 * @param columnName the colum name
	 * @return the integer, may be null
	 * @throws SQLException on error
	 */
	@Nullable
	public static Double getDouble(@NonNull ResultSet rs, String columnName) throws SQLException {
		double value = rs.getDouble(columnName);
		if (rs.wasNull()) {
			return null;
		}
		return value;
	}
	/**
	 * Returns a nullable float field from the result set as a Float object.
	 * @param rs the result set
	 * @param columnName the colum name
	 * @return the integer, may be null
	 * @throws SQLException on error
	 */
	@Nullable
	public static Float getFloat(@NonNull ResultSet rs, String columnName) throws SQLException {
		float value = rs.getFloat(columnName);
		if (rs.wasNull()) {
			return null;
		}
		return value;
	}
	/**
	 * Returns a nullable int field from the result set as an Integer object.
	 * @param rs the result set
	 * @param columnName the colum name
	 * @return the integer, may be null
	 * @throws SQLException on error
	 */
	@Nullable
	public static Integer getInt(@Nonnull ResultSet rs, String columnName) throws SQLException {
		int value = rs.getInt(columnName);
		if (rs.wasNull()) {
			return null;
		}
		return value;
	}
	/**
	 * Returns a nullable long field from the result set as a Long object.
	 * @param rs the result set
	 * @param columnName the colum name
	 * @return the integer, may be null
	 * @throws SQLException on error
	 */
	@Nullable
	public static Long getLong(@NonNull ResultSet rs, String columnName) throws SQLException {
		long value = rs.getLong(columnName);
		if (rs.wasNull()) {
			return null;
		}
		return value;
	}
	/**
	 * Returns a nullable short field from the result set as a Short object.
	 * @param rs the result set
	 * @param columnName the colum name
	 * @return the integer, may be null
	 * @throws SQLException on error
	 */
	@Nullable
	public static Short getShort(@NonNull ResultSet rs, String columnName) throws SQLException {
		short value = rs.getShort(columnName);
		if (rs.wasNull()) {
			return null;
		}
		return value;
	}
	/**
	 * Returns a localTime instance or null if the result field is null.
	 * @param rs the result set
	 * @param columnName the colum name
	 * @return the local time or null
	 * @throws SQLException on error
	 */
	public static LocalTime getTime(@NonNull ResultSet rs, String columnName) throws SQLException {
		java.sql.Time tm = rs.getTime(columnName);
		if (tm != null) {
			return toLocalTime(tm);
		}
		return null;
	}
	/**
	 * Wraps the resultset into an iterable sequence to be used by a 
	 * simple for-each loop.
	 * <p>Throws an SQLRuntimeException if the resultset's next() throws an exception.</p>
	 * @param rs the result set to wrap
	 * @return the iterable with the result set
	 */
	@NonNull 
	public static Iterable<ResultSet> iterate(
			@NonNull final ResultSet rs) {
		return new Iterable<ResultSet>() {
			@Override
			public Iterator<ResultSet> iterator() {
				return new Iterator<ResultSet>() {
					/** The resultset has finished. */
					boolean done;
					/** The cursor has been moved. */
					boolean moved;
					@Override
					public boolean hasNext() {
						if (!done) {
							if (!moved) {
								try {
									done = rs.next();
								} catch (SQLException ex) {
									throw new SQLRuntimeException(ex);
								}
								moved = true;
							}
						}
						return !done;
					}
					@Override
					public ResultSet next() {
						if (hasNext()) {
							moved = false;
							return rs;
						}
						throw new NoSuchElementException();
					}
					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
	/**
	 * Returns a token if the parameter is null.
	 * @param value the value
	 * @return the null-token or the same value
	 */
	@NonNull 
	public static Object maybeNull(BigDecimal value) {
		return value != null ? value : BigDecimal.class;
	}
	/**
	 * Returns a token if the parameter is null.
	 * @param value the value
	 * @return the null-token or the same value
	 */
	@NonNull 
	public static Object maybeNull(BigInteger value) {
		return value != null ? value : BigInteger.class;
	}
	/**
	 * Returns a token if the parameter is null.
	 * @param value the value
	 * @return the null-token or the same value
	 */
	@NonNull 
	public static Object maybeNull(Boolean value) {
		return value != null ? value : Boolean.class;
	}
	/**
	 * Returns a token if the parameter is null.
	 * @param value the value
	 * @return the null-token or the same value
	 */
	@NonNull 
	public static Object maybeNull(Byte value) {
		return value != null ? value : Byte.class;
	}
	/**
	 * Returns a token if the parameter is null.
	 * @param value the value
	 * @return the null-token or the same value
	 */
	@NonNull 
	public static Object maybeNull(byte[] value) {
		return value != null ? value : byte[].class;
	}
	/**
	 * Returns a token if the parameter is null.
	 * @param value the value
	 * @return the null-token or the same value
	 */
	@NonNull 
	public static Object maybeNull(CharSequence value) {
		return value != null ? value : String.class;
	}
	/**
	 * Returns a token if the parameter is null.
	 * @param value the value
	 * @return the null-token or the same value
	 */
	@NonNull 
	public static Object maybeNull(Date value) {
		return value != null ? value : Date.class;
	}
	/**
	 * Returns a token if the parameter is null.
	 * @param value the value
	 * @return the null-token or the same value
	 */
	@NonNull 
	public static Object maybeNull(DateMidnight value) {
		return value != null ? value : DateMidnight.class;
	}
	/**
	 * Returns a token if the parameter is null.
	 * @param value the value
	 * @return the null-token or the same value
	 */
	@NonNull 
	public static Object maybeNull(DateTime value) {
		return value != null ? value : DateTime.class;
	}
	/**
	 * Returns a token if the parameter is null.
	 * @param value the value
	 * @return the null-token or the same value
	 */
	@NonNull 
	public static Object maybeNull(Double value) {
		return value != null ? value : Double.class;
	}
	/**
	 * Returns a token if the parameter is null.
	 * @param value the value
	 * @return the null-token or the same value
	 */
	@NonNull 
	public static Object maybeNull(Float value) {
		return value != null ? value : Float.class;
	}
	/**
	 * Returns a token if the parameter is null.
	 * @param value the value
	 * @return the null-token or the same value
	 */
	@NonNull 
	public static Object maybeNull(InputStream value) {
		return value != null ? value : InputStream.class;
	}
	/**
	 * Returns a token if the parameter is null.
	 * @param value the value
	 * @return the null-token or the same value
	 */
	@NonNull 
	public static Object maybeNull(Integer value) {
		return value != null ? value : Integer.class;
	}
	/**
	 * Returns a token if the parameter is null.
	 * @param value the value
	 * @return the null-token or the same value
	 */
	@NonNull 
	public static Object maybeNull(java.util.Date value) {
		return value != null ? new Timestamp(value.getTime()) : Timestamp.class;
	}
	/**
	 * Returns a token if the parameter is null.
	 * @param value the value
	 * @return the null-token or the same value
	 */
	@NonNull 
	public static Object maybeNull(LocalTime value) {
		return value != null ? value : LocalTime.class;
	}
	/**
	 * Returns a token if the parameter is null.
	 * @param value the value
	 * @return the null-token or the same value
	 */
	@NonNull 
	public static Object maybeNull(Long value) {
		return value != null ? value : Long.class;
	}
	/**
	 * Returns a token if the parameter is null.
	 * @param value the value
	 * @return the null-token or the same value
	 */
	@NonNull 
	public static Object maybeNull(Short value) {
		return value != null ? value : Short.class;
	}
	/**
	 * Returns a token if the parameter is null.
	 * @param value the value
	 * @return the null-token or the same value
	 */
	@NonNull 
	public static Object maybeNull(Time value) {
		return value != null ? value : Time.class;
	}
	/**
	 * Returns a token if the parameter is null.
	 * @param value the value
	 * @return the null-token or the same value
	 */
	@NonNull 
	public static Object maybeNull(Timestamp value) {
		return value != null ? value : Timestamp.class;
	}
	/**
	 * Sets the parameters on a prepared statement.
	 * @param pstmt the statement
	 * @param values the list of values
	 * @throws SQLException on error
	 */
	public static void setParams(
			@NonNull PreparedStatement pstmt, 
			@NonNull Iterable<?> values)
			throws SQLException {
		setParams(pstmt, Iterables.toArray(values, Object.class));
	}
	/**
	 * Sets the parameters on a prepared statement.
	 * @param offset the parameter start offset
	 * @param pstmt the statement
	 * @param values the list of values
	 * @throws SQLException on error
	 */
	public static void setParams(
			int offset,
			@NonNull PreparedStatement pstmt, 
			@NonNull Iterable<?> values)
			throws SQLException {
		setParams(offset, pstmt, Iterables.toArray(values, Object.class));
	}
	/**
	 * Sets the parameters on a prepared statement.
	 * @param pstmt the statement
	 * @param values the list of values
	 * @throws SQLException on error
	 */
	public static void setParams(
			@NonNull PreparedStatement pstmt, 
			Object... values)
			throws SQLException {
		setParams(1, pstmt, values);
	}
	/**
	 * Sets the parameters on a prepared statement.
	 * @param offset the parameter offset to start from
	 * @param pstmt the statement
	 * @param values the list of values
	 * @throws SQLException on error
	 */
	public static void setParams(
			int offset,
			@NonNull PreparedStatement pstmt,
			Object... values)
			throws SQLException {
		int i = offset;
		for (Object o : values) {
			if (o instanceof Class<?>) {
				if (o == byte[].class) {
					pstmt.setNull(i, Types.BLOB);
				} else
				if (o == Short.class) {
					pstmt.setNull(i, Types.SMALLINT);
				} else
				if (o == Byte.class) {
					pstmt.setNull(i, Types.TINYINT);
				} else
				if (o == Boolean.class) {
					pstmt.setNull(i, Types.BOOLEAN);
				} else
				if (o == Double.class) {
					pstmt.setNull(i, Types.DOUBLE);
				} else
				if (o == Float.class) {
					pstmt.setNull(i, Types.FLOAT);
				} else
				if (o == BigDecimal.class) {
					pstmt.setNull(i, Types.DECIMAL);
				} else
				if (o == BigInteger.class) {
					pstmt.setNull(i, Types.NUMERIC);
				} else
				if (o == Timestamp.class || o == DateTime.class || o == java.util.Date.class) {
					pstmt.setNull(i, Types.TIMESTAMP);
				} else
				if (o == Time.class || o == LocalTime.class) {
					pstmt.setNull(i, Types.TIME);
				} else
				if (o == Date.class || o == DateMidnight.class || o == LocalDate.class) {
					pstmt.setNull(i, Types.DATE);
				} else
				if (o == String.class) {
					pstmt.setNull(i, Types.VARCHAR);
				} else
				if (o == Integer.class) {
					pstmt.setNull(i, Types.INTEGER);
				} else
				if (o == Long.class) {
					pstmt.setNull(i, Types.BIGINT);
				}
			} else
			if (o instanceof CharSequence) {
				pstmt.setString(i, ((CharSequence)o).toString());
			} else
			if (o instanceof Byte) {
				pstmt.setByte(i, (Byte)o);
			} else
			if (o instanceof Short) {
				pstmt.setShort(i, (Short)o);
			} else
			if (o instanceof Integer) {
				pstmt.setInt(i, (Integer)o);
			} else
			if (o instanceof Long) {
				pstmt.setLong(i, (Long)o);
			} else
			if (o instanceof Character) {
				pstmt.setString(i, ((Character)o).toString());
			} else
			if (o instanceof Date) {
				pstmt.setDate(i, (Date)o);
			} else
			if (o instanceof Timestamp) {
				pstmt.setTimestamp(i, (Timestamp)o);
			} else
			if (o instanceof java.util.Date) {
				pstmt.setTimestamp(i, new Timestamp(((java.util.Date)o).getTime()));
			} else
			if (o instanceof Double) {
				pstmt.setDouble(i, (Double)o);
			} else
			if (o instanceof Float) {
				pstmt.setFloat(i, (Float)o);
			} else
			if (o instanceof BigDecimal) {
				pstmt.setBigDecimal(i, (BigDecimal)o);
			} else
			if (o instanceof BigInteger) {
				pstmt.setBigDecimal(i, new BigDecimal((BigInteger)o));
			} else
			if (o instanceof Boolean) {
				pstmt.setBoolean(i, (Boolean)o);
			} else
			if (o instanceof Time) {
				pstmt.setTime(i, (Time)o);
			} else 
			if (o instanceof DateTime) {
				pstmt.setTimestamp(i, new Timestamp(((DateTime)o).getMillis()));
			} else
			if (o instanceof DateMidnight) {
				pstmt.setDate(i, new Date(((DateMidnight)o).getMillis()));
			} else
			if (o instanceof LocalDate) {
				pstmt.setDate(i, new Date(((LocalDate)o).toDateMidnight().getMillis()));
			} else
			if (o instanceof InputStream) {
				pstmt.setBlob(i, (InputStream)o);
			} else
			if (o instanceof byte[]) {
				pstmt.setBytes(i, (byte[])o);
			} else
			if (o instanceof LocalTime) {
				LocalTime lt = (LocalTime)o;
				pstmt.setTime(i, toSQLTime(lt));
			} else {
				throw new SQLException("Unknown type " + o + ", " + (o != null ? o.getClass() : "?") + " for parameter " + i);
			}
			i++;
		}
	}
	/**
	 * Sets the parameters on a result set.
	 * @param rs the result set
	 * @param values the list of values
	 * @throws SQLException on error
	 */
	public static void setParams(
			@NonNull ResultSet rs, 
			@NonNull Iterable<?> values)
			throws SQLException {
		setParams(rs, Iterables.toArray(values, Object.class));
	}
	/**
	 * Sets the parameters on a result set.
	 * @param rs the result set
	 * @param values the list of values
	 * @throws SQLException on error
	 */
	public static void setParams(
			@NonNull ResultSet rs, 
			Object... values) throws SQLException {
		int i = 1;
		for (Object o : values) {
			if (o instanceof Class<?>) {
				rs.updateNull(i);
			} else
			if (o instanceof CharSequence) {
				rs.updateString(i, ((CharSequence)o).toString());
			} else
			if (o instanceof Byte) {
				rs.updateByte(i, (Byte)o);
			} else
			if (o instanceof Short) {
				rs.updateShort(i, (Short)o);
			} else
			if (o instanceof Integer) {
				rs.updateInt(i, (Integer)o);
			} else
			if (o instanceof Long) {
				rs.updateLong(i, (Long)o);
			} else
			if (o instanceof Character) {
				rs.updateString(i, ((Character)o).toString());
			} else
			if (o instanceof Date) {
				rs.updateDate(i, (Date)o);
			} else
			if (o instanceof Timestamp) {
				rs.updateTimestamp(i, (Timestamp)o);
			} else
			if (o instanceof java.util.Date) {
				rs.updateTimestamp(i, new Timestamp(((java.util.Date)o).getTime()));
			} else
			if (o instanceof Double) {
				rs.updateDouble(i, (Double)o);
			} else
			if (o instanceof Float) {
				rs.updateFloat(i, (Float)o);
			} else
			if (o instanceof BigDecimal) {
				rs.updateBigDecimal(i, (BigDecimal)o);
			} else
			if (o instanceof BigInteger) {
				rs.updateBigDecimal(i, new BigDecimal((BigInteger)o));
			} else
			if (o instanceof Boolean) {
				rs.updateBoolean(i, (Boolean)o);
			} else
			if (o instanceof Time) {
				rs.updateTime(i, (Time)o);
			} else 
			if (o instanceof DateTime) {
				rs.updateTimestamp(i, new Timestamp(((DateTime)o).getMillis()));
			} else
			if (o instanceof DateMidnight) {
				rs.updateDate(i, new Date(((DateMidnight)o).getMillis()));
			} else
			if (o instanceof LocalDate) {
				rs.updateDate(i, new Date(((LocalDate)o).toDateMidnight().getMillis()));
			} else
			if (o instanceof InputStream) {
				rs.updateBlob(i, (InputStream)o);
			} else
			if (o instanceof byte[]) {
				rs.updateBytes(i, (byte[])o);
			} else
			if (o instanceof LocalTime) {
				rs.updateTime(i, toSQLTime((LocalTime)o));
			} else {
				throw new SQLException("Unknown type " + o + ", " + (o != null ? o.getClass() : "?") + " for parameter " + i);
			}
			i++;
		}
	}
	/**
	 * Convert an SQL time object into a LocalTime object.
	 * @param time the SQL time 
	 * @return the local time
	 */
	@NonNull
	public static LocalTime toLocalTime(@NonNull Time time) {
		return new LocalTime(time.getTime());
	}
	/**
	 * Convert the local time to SQL time object, considering
	 * the timezone and DST offsets.
	 * @param time the local time
	 * @return the sql time
	 */
	@NonNull
	public static Time toSQLTime(@NonNull LocalTime time) {
		DateTime epochTime = new DateTime(1970, 1, 1, 
				time.getHourOfDay(), time.getMinuteOfHour(), 
				time.getSecondOfMinute(), time.getMillisOfSecond());
		return new Time(epochTime.getMillis());
	}
	/** The underlying connection. */
	protected Connection conn;
	/** The defualt query fetch size. */
	protected int fetchSize = 0;
	/** Log the queries? */
	protected boolean logQueries;
	@Override
	public void close() throws IOException {
		Connection c = conn;
		if (c != null) {
			conn = null;
			try {
				c.close();
			} catch (SQLException ex) {
				throw new IOException(ex);
			}
		}
	}
	/**
	 * Commit the current transaction.
	 * @throws SQLException on error
	 */
	public void commit() throws SQLException {
		conn.commit();
	}
	/**
	 * Returns a list of the generated keys for the given statement as long values.
	 * @param pstmt the statement
	 * @return the list of generated keys
	 * @throws SQLException on error
	 */
	@NonNull 
	public List<Long> generatedKeys(@NonNull PreparedStatement pstmt) throws SQLException {
		List<Long> result = Lists.newArrayList();
		ResultSet rs = pstmt.getGeneratedKeys();
		try {
			if (fetchSize != 0) {
				rs.setFetchSize(fetchSize);
			}
			while (rs.next()) {
				result.add(rs.getLong(1));
			}
		} finally {
			rs.close();
		}
		return result;
	}
	/**
	 * Execute a single-valued parametric insert and invoke an action for the generated
	 * key result set.
	 * @param sql the query
	 * @param setAutoKey the action to extract the generated keys.
	 * @param params the parameter sequence
	 * @throws SQLException on error
	 */
	public void insertAuto(
			@NonNull CharSequence sql,
			@NonNull Action1E<? super ResultSet, ? extends SQLException> setAutoKey, 
			@NonNull Iterable<?> params) throws SQLException {
		insertAuto(sql, setAutoKey, Iterables.toArray(params, Object.class));
	}
	/**
	 * Execute a single-valued parametric insert and invoke an action for the generated
	 * key result set.
	 * @param sql the query
	 * @param setAutoKey the action to extract the generated keys.
	 * @param params the optional parameter array
	 * @throws SQLException on error
	 */
	public void insertAuto(
			@NonNull CharSequence sql, 
			@NonNull Action1E<? super ResultSet, ? extends SQLException> setAutoKey, 
			Object... params) throws SQLException {
		try (PreparedStatement pstmt = prepare(true, sql, params)) {
			pstmt.executeUpdate();
			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					setAutoKey.call(rs);
				}
			}
		}
		
	}
	/**
	 * Execute a single-valued parametric insert and return a generated long key.
	 * @param sql the query
	 * @param params the optional parameter array
	 * @return the long key
	 * @throws SQLException on error
	 */
	public long insertAuto(
			@NonNull CharSequence sql, 
			@NonNull Iterable<?> params) throws SQLException {
		try (PreparedStatement pstmt = prepare(true, sql, params)) {
			pstmt.executeUpdate();
			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					return rs.getLong(1);
				}
			}
		}
		return 0L;
	}
	/**
	 * Execute a single-valued parametric insert and return a generated long key.
	 * @param sql the query
	 * @param params the optional parameter array
	 * @return the long key
	 * @throws SQLException on error
	 */
	public long insertAuto(
			@NonNull CharSequence sql, 
			Object... params) throws SQLException {
		try (PreparedStatement pstmt = prepare(true, sql, params)) {
			pstmt.executeUpdate();
			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					return rs.getLong(1);
				}
			}
		}
		return 0L;
	}
	/**
	 * Prepare a query statement with the given parameters.
	 * Null-values must be represented via a class value, e.g., Integer.class
	 * @param sql the query
	 * @param autoResult should expect autogenerated keys?
	 * @param values the parameters
	 * @return the statement
	 * @throws SQLException on error
	 */
	@NonNull 
	public PreparedStatement prepare(
			boolean autoResult, 
			@NonNull CharSequence sql, 
			@NonNull Iterable<?> values) throws SQLException {
		return prepare(autoResult, sql, Iterables.toArray(values, Object.class));
	}
	/**
	 * Prepare a query statement with the given parameters.
	 * Null-values must be represented via a class value, e.g., Integer.class
	 * @param sql the query
	 * @param autoResult should expect autogenerated keys?
	 * @param values the parameters
	 * @return the statement
	 * @throws SQLException on error
	 */
	@NonNull 
	public PreparedStatement prepare(
			boolean autoResult, 
			@NonNull CharSequence sql, 
			Object... values) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql.toString(), autoResult ? PreparedStatement.RETURN_GENERATED_KEYS : PreparedStatement.NO_GENERATED_KEYS);
		setParams(pstmt, values);
		if (logQueries) {
			System.out.println(pstmt);
		}
		return pstmt;
	}
	/**
	 * Prepare a query statement with the given parameters.
	 * Null-values must be represented via a class value, e.g., Integer.class
	 * @param sql the query
	 * @param values the parameters
	 * @return the statement
	 * @throws SQLException on error
	 */
	@NonNull 
	public PreparedStatement prepare(
			@NonNull CharSequence sql, 
			@NonNull Iterable<?> values) throws SQLException {
		return prepare(false, sql, Iterables.toArray(values, Object.class));
	}
	/**
	 * Prepare a query statement with the given parameters.
	 * Null-values must be represented via a class value, e.g., Integer.class
	 * @param sql the query
	 * @param params the optional parameters
	 * @return the statement
	 * @throws SQLException on error
	 */
	@NonNull 
	public PreparedStatement prepare(
			@NonNull CharSequence sql, 
			Object... params) throws SQLException {
		return prepare(false, sql, params);
	}
	/**
	 * Create a prepared statement wit the given SQL hints and parameters.
	 * @param sql the query
	 * @param direction the read direction constant from ResultSet
	 * @param concurrency the concurrency constant from ResultSet
	 * @param values the parameter values
	 * @return the statement
	 * @throws SQLException on error
	 */
	@NonNull 
	public PreparedStatement prepare(
			int direction, 
			int concurrency,
			@NonNull CharSequence sql, 
			@NonNull Iterable<?> values) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql.toString(), direction, concurrency);
		setParams(pstmt, Iterables.toArray(values, Object.class));
		if (logQueries) {
			System.out.println(pstmt);
		}
		return pstmt;
	}
	/**
	 * Create a prepared statement.
	 * @param direction the read direction constant from ResultSet
	 * @param concurrency the concurrency constant from ResultSet
	 * @param sql the query
	 * @param params the optional parameters
	 * @return the statement
	 * @throws SQLException on error
	 */
	@NonNull 
	public PreparedStatement prepare(
			int direction, 
			int concurrency,
			@NonNull CharSequence sql,
			Object... params) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql.toString(), direction, concurrency);
		setParams(pstmt, params);
		if (logQueries) {
			System.out.println(pstmt);
		}
		return pstmt;
	}
	/**
	 * Prepare a read-only statement with minimum fetch.
	 * @param sql the query
	 * @param params the parameter sequence. 
	 * @return the statement
	 * @throws SQLException on error
	 */
	@NonNull
	public PreparedStatement prepareReadOnly(
			@NonNull CharSequence sql, 
			@NonNull Iterable<?> params) throws SQLException {
		PreparedStatement pstmt = prepare(
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY, sql, params);
		pstmt.setFetchSize(Integer.MIN_VALUE);
		if (logQueries) {
			System.out.println(pstmt);
		}
		return pstmt;
	}
	/**
	 * Prepare a read-only statement with minimum fetch.
	 * @param sql the query
	 * @param params the optional parameters. 
	 * @return the statement
	 * @throws SQLException on error
	 */
	@NonNull 
	public PreparedStatement prepareReadOnly(
			@NonNull CharSequence sql, 
			Object... params) throws SQLException {
		PreparedStatement pstmt = prepare(
				ResultSet.TYPE_FORWARD_ONLY, 
				ResultSet.CONCUR_READ_ONLY, sql, params);
		pstmt.setFetchSize(Integer.MIN_VALUE);
		if (logQueries) {
			System.out.println(pstmt);
		}
		return pstmt;
	}
	/**
	 * Execute the action for each resultset row returned by the query.
	 * @param sql the query
	 * @param action the action to call
	 * @param params the parameters
	 * @throws SQLException on error
	 */
	public void query(
			@NonNull CharSequence sql,
			@NonNull Action1E<? super ResultSet, ? extends SQLException> action,
			@NonNull Iterable<?> params) throws SQLException {
		query(sql, action, Iterables.toArray(params, Object.class));
	}
	/**
	 * Execute the action for each resultset row returned by the query.
	 * @param sql the query
	 * @param action the action to call
	 * @param params the parameters
	 * @throws SQLException on error
	 */
	public void query(
			@NonNull CharSequence sql,
			@NonNull Action1E<? super ResultSet, ? extends SQLException> action,
			Object... params) throws SQLException {
		try (PreparedStatement pstmt = prepare(false, sql, params)) {
			if (fetchSize != 0) {
				pstmt.setFetchSize(fetchSize);
			}
			try (ResultSet rs = pstmt.executeQuery()) {
				if (fetchSize != 0) {
					rs.setFetchSize(fetchSize);
				}
				while (rs.next()) {
					action.call(rs);
				}
			}
		}
	}
	/**
	 * Execute the indexed action for each resultset row returned by the query.
	 * @param sql the query
	 * @param action the action to call
	 * @param params the parameters
	 * @throws SQLException on error
	 */
	public void query(
			@NonNull CharSequence sql,
			@NonNull Action2E<? super ResultSet, ? super Integer, ? extends SQLException> action,
			@NonNull Iterable<?> params) throws SQLException {
		query(sql, action, Iterables.toArray(params, Object.class));
	}
	/**
	 * Execute the indexed action for each resultset row returned by the query.
	 * @param sql the query
	 * @param action the action to call
	 * @param params the parameters
	 * @throws SQLException on error
	 */
	public void query(
			@NonNull CharSequence sql,
			@NonNull Action2E<? super ResultSet, ? super Integer, ? extends SQLException> action,
			Object... params) throws SQLException {
		try (PreparedStatement pstmt = prepare(false, sql, params)) {
			if (fetchSize != 0) {
				pstmt.setFetchSize(fetchSize);
			}
			try (ResultSet rs = pstmt.executeQuery()) {
				if (fetchSize != 0) {
					rs.setFetchSize(fetchSize);
				}
				int i = 0;
				while (rs.next()) {
					action.call(rs, i++);
				}
			}
		}
	}
	/**
	 * Query the values from the database.
	 * @param <T> the element type
	 * @param sql the query
	 * @param unmarshaller the record unmarshaller
	 * @param params the sequence of query parameters
	 * @return the value list
	 * @throws SQLException on error
	 */
	@NonNull
	public <T> List<T> query(
			@NonNull CharSequence sql, 
			@NonNull Func1E<? super ResultSet, ? extends T, ? extends SQLException> unmarshaller, 
			@NonNull Iterable<?> params) throws SQLException {
		return query(sql, unmarshaller, Iterables.toArray(params, Object.class));
	}
	/**
	 * Query the values from the database.
	 * @param <T> the element type
	 * @param sql the query
	 * @param unmarshaller the record unmarshaller
	 * @param params the query parameters
	 * @return the value list or the error
	 * @throws SQLException on error
	 */
	@NonNull
	public <T> List<T> query(
			@NonNull CharSequence sql, 
			@NonNull Func1E<? super ResultSet, ? extends T, ? extends SQLException> unmarshaller, 
			Object... params) throws SQLException {
		List<T> result = Lists.newArrayList();
		try (PreparedStatement pstmt = prepare(false, sql, params)) {
			if (fetchSize != 0) {
				pstmt.setFetchSize(fetchSize);
			}
			try (ResultSet rs = pstmt.executeQuery()) {
				if (fetchSize != 0) {
					rs.setFetchSize(fetchSize);
				}
				while (rs.next()) {
					T t = unmarshaller.call(rs);
					result.add(t);
				}
			}
		}
		return result;
	}
	/**
	 * Returns a closeable iterator which returns unmarshalled data on request.
	 * @param <T> the result element type
	 * @param unmarshaller the unmarshaller
	 * @param sql the query to execute
	 * @param params the parameters to the query
	 * @return the closeable iterable
	 */
	@NonNull
	public <T> CloseableIterator<T> queryIterator(
			@NonNull CharSequence sql, 
			@NonNull final Func1E<? super ResultSet, ? extends T, ? extends SQLException> unmarshaller,
			@NonNull Iterable<?> params) {
		return queryIterator(sql, unmarshaller, Iterables.toArray(params, Object.class));
	}
	/**
	 * Returns a closeable iterator which returns unmarshalled data on request.
	 * @param <T> the result element type
	 * @param unmarshaller the unmarshaller
	 * @param sql the query to execute
	 * @param params the parameters to the query
	 * @return the closeable iterable
	 */
	@NonNull
	public <T> CloseableIterator<T> queryIterator(
			@NonNull CharSequence sql, 
			@NonNull final Func1E<? super ResultSet, ? extends T, ? extends SQLException> unmarshaller,
			Object... params) {
		try {
			final PreparedStatement pstmt = prepare(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, sql, params);
			
			pstmt.setFetchSize(Integer.MIN_VALUE);
			final ResultSet rs = pstmt.executeQuery();
			if (fetchSize != 0) {
				rs.setFetchSize(fetchSize);
			}
			
			return new CloseableIterator<T>() {
				/** The resultset has finished. */
				boolean done;
				/** The cursor has been moved. */
				boolean moved;
				final AtomicBoolean once = new AtomicBoolean();
				@Override
				public void unsubscribe() {
					if (once.compareAndSet(false, true)) {
						try {
							rs.close();
						} catch (SQLException ex) {
						}
						try {
							pstmt.close();
						} catch (SQLException ex) {
						}
					}
				}
				@Override
				public boolean isUnsubscribed() {
					return once.get();
				}
				@Override
				public boolean hasNext() {
					if (!done) {
						if (!moved) {
							try {
								if (rs.next()) {
									moved = true;
								} else {
									done = true;
								}
							} catch (SQLException ex) {
								throw new SQLRuntimeException(ex);
							}
						}
					}
					return !done;
				}
				@Override
				public T next() {
					if (hasNext()) {
						try {
							T result = unmarshaller.call(rs);
							moved = false;
							return result;
						} catch (SQLException ex) {
							throw new SQLRuntimeException(ex);
						}
					}
					throw new NoSuchElementException();
				}
				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		} catch (SQLException ex) {
			throw new SQLRuntimeException(ex);
		}
	}
	/**
	 * Execute the action for each resultset row returned by the query.
	 * @param sql the query
	 * @param action the action to call
	 * @param params the parameters
	 * @throws SQLException on error
	 */
	public void queryReadOnly(
			@NonNull CharSequence sql,
			@NonNull Action1E<? super ResultSet, ? extends SQLException> action,
			@NonNull Iterable<?> params) throws SQLException {
		queryReadOnly(sql, action, Iterables.toArray(params, Object.class));
	}
	/**
	 * Execute the action for each resultset row returned by the query.
	 * @param sql the query
	 * @param action the action to call
	 * @param params the parameters
	 * @throws SQLException on error
	 */
	public void queryReadOnly(
			@NonNull CharSequence sql,
			@NonNull Action1E<? super ResultSet, ? extends SQLException> action,
			Object... params) throws SQLException {
		try (PreparedStatement pstmt = prepareReadOnly(sql, params)) {
			if (fetchSize != 0) {
				pstmt.setFetchSize(fetchSize);
			}
			try (ResultSet rs = pstmt.executeQuery()) {
				if (fetchSize != 0) {
					rs.setFetchSize(fetchSize);
				}
				while (rs.next()) {
					action.call(rs);
				}
			}
		}
	}
	/**
	 * Query the values from the database.
	 * @param <T> the element type
	 * @param sql the query
	 * @param unmarshaller the record unmarshaller
	 * @param params the sequence of query parameters
	 * @return the value list
	 * @throws SQLException on error
	 */
	@NonNull
	public <T> List<T> queryReadOnly(
			@NonNull CharSequence sql, 
			@NonNull Func1E<? super ResultSet, ? extends T, ? extends SQLException> unmarshaller, 
			@NonNull Iterable<?> params) throws SQLException {
		return queryReadOnly(sql, unmarshaller, Iterables.toArray(params, Object.class));
	}
	/**
	 * Query the values from the database.
	 * @param <T> the element type
	 * @param sql the query
	 * @param unmarshaller the record unmarshaller
	 * @param params the query parameters
	 * @return the value list or the error
	 * @throws SQLException on error
	 */
	@NonNull
	public <T> List<T> queryReadOnly(
			@NonNull CharSequence sql, 
			@NonNull Func1E<? super ResultSet, ? extends T, ? extends SQLException> unmarshaller, 
			Object... params) throws SQLException {
		List<T> result = Lists.newArrayList();
		try (PreparedStatement pstmt = prepareReadOnly(sql, params)) {
			if (fetchSize != 0) {
				pstmt.setFetchSize(fetchSize);
			}
			try (ResultSet rs = pstmt.executeQuery()) {
				if (fetchSize != 0) {
					rs.setFetchSize(fetchSize);
				}
				while (rs.next()) {
					T t = unmarshaller.call(rs);
					result.add(t);
				}
			}
		}
		return result;
	}
	/**
	 * Query the values from the database with an indexed unmarshaller.
	 * @param <T> the element type
	 * @param sql the query
	 * @param unmarshaller the record unmarshaller, indexed
	 * @param params the sequence of query parameters
	 * @return the value list
	 * @throws SQLException on error
	 */
	@NonNull
	public <T> List<T> queryReadOnly(
			@NonNull CharSequence sql, 
			@NonNull Func2E<? super ResultSet, ? super Integer, ? extends T, ? extends SQLException> unmarshaller, 
			@NonNull Iterable<?> params) throws SQLException {
		return queryReadOnly(sql, unmarshaller, Iterables.toArray(params, Object.class));
	}
	/**
	 * Query the values from the database with an indexed unmarshaller.
	 * @param <T> the element type
	 * @param sql the query
	 * @param unmarshaller the record unmarshaller, indexed
	 * @param params the query parameters
	 * @return the value list or the error
	 * @throws SQLException on error
	 */
	@NonNull
	public <T> List<T> queryReadOnly(
			@NonNull CharSequence sql, 
			@NonNull Func2E<? super ResultSet, ? super Integer, ? extends T, ? extends SQLException> unmarshaller, 
			Object... params) throws SQLException {
		List<T> result = Lists.newArrayList();
		try (PreparedStatement pstmt = prepareReadOnly(sql, params)) {
			if (fetchSize != 0) {
				pstmt.setFetchSize(fetchSize);
			}
			try (ResultSet rs = pstmt.executeQuery()) {
				if (fetchSize != 0) {
					rs.setFetchSize(fetchSize);
				}
				int i = 0;
				while (rs.next()) {
					T t = unmarshaller.call(rs, i++);
					result.add(t);
				}
			}
		}
		return result;
	}
	/**
	 * Returns a single element from the query or null if the result set is empty.
	 * @param <T> the result type
	 * @param sql the query
	 * @param unmarshaller the record unmarshaller
	 * @param params the parameter sequence
	 * @return the unmarshalled value or null if the query result was empty
	 * @throws SQLException on error
	 */
	@Nullable
	public <T> T querySingle(
			@NonNull CharSequence sql, 
			@NonNull Func1E<? super ResultSet, ? extends T, ? extends SQLException> unmarshaller, 
			@NonNull Iterable<?> params) throws SQLException {
		return querySingle(sql, unmarshaller, Iterables.toArray(params, Object.class));
	}
	/**
	 * Returns a single element from the query or null if the result set is empty.
	 * @param <T> the result type
	 * @param sql the query
	 * @param unmarshaller the record unmarshaller
	 * @param params the optional parameter sequence
	 * @return the unmarshalled value or null if the query result was empty
	 * @throws SQLException on error
	 */
	@Nullable
	public <T> T querySingle(
			@NonNull CharSequence sql, 
			@NonNull Func1E<? super ResultSet, ? extends T, ? extends SQLException> unmarshaller, 
			Object... params) throws SQLException {
		try (PreparedStatement pstmt = prepare(false, sql, params)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return unmarshaller.call(rs);
				}
				return null;
			}
		} catch (SQLException ex) {
			System.err.println(sql);
			throw ex;
		}
	}
	/**
	 * Roll back the current transaction.
	 * Exceptions are suppressed.
	 */
	public void rollback() {
		try {
			conn.rollback();
		} catch (SQLException ex) {
			// suppressed
		}
	}
	/**
	 * Execute the given SQL with the batch of values marshalled from the sources.
	 * @param <T> the element type
	 * @param sql the query to execute
	 * @param items the items
	 * @param marshaller the marshaller that fills in the statement, should *NOT* call the addBatch
	 * @throws SQLException on error
	 */
	public <T> void save(
			@NonNull CharSequence sql, 
			@NonNull Iterable<T> items, 
			@NonNull Action2E<? super PreparedStatement, ? super T, ? extends SQLException> marshaller) throws SQLException {
		PreparedStatement pstmt = prepare(false, sql);
		try {
			for (T t : items) {
				marshaller.call(pstmt, t);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		} catch (SQLException ex) {
			System.err.println(sql);
			throw ex;
		} finally {
			pstmt.close();
		}
	}
	/**
	 * Execute the given SQL with the batch of values marshalled from the sources and update
	 * the elements with the autogenerated keys.
	 * @param <T> the element type
	 * @param sql the query to execute
	 * @param items the items
	 * @param marshaller the marshaller that fills in the statement, should *NOT* call the addBatch
	 * @param setAutoKey callback to set the automatic key
	 * @throws SQLException on error
	 */
	public <T> void save(@NonNull CharSequence sql,
			@NonNull Iterable<T> items, 
			@NonNull Action2E<? super PreparedStatement, ? super T, ? extends SQLException> marshaller,
			@NonNull Action2E<? super ResultSet, ? super T, ? extends SQLException> setAutoKey) throws SQLException {
		PreparedStatement pstmt = prepare(true, sql);
		try {
			if (fetchSize != 0) {
				pstmt.setFetchSize(fetchSize);
			}
			for (T t : items) {
				marshaller.call(pstmt, t);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (fetchSize != 0) {
				rs.setFetchSize(fetchSize);
			}
			try {
				Iterator<T> it = items.iterator();
				while (it.hasNext() && rs.next()) {
					T t = it.next();
					setAutoKey.call(rs, t);
				}
			} finally {
				rs.close();
			}
		} catch (SQLException ex) {
			System.err.println(sql);
			throw ex;
		} finally {
			pstmt.close();
		}
	}
	/**
	 * Sets the query fetch size.
	 * @param size the size
	 */
	public void setFetchSize(int size) {
		fetchSize = size;
	}
	/**
	 * Enable the logging of queries.
	 * @param value is enabled?
	 */
	public void setLogQueries(boolean value) {
		this.logQueries = value;
	}
	/**
	 * Execute a single update-like statement.
	 * @param sql the query
	 * @param params the query parameters
	 * @return the update count
	 * @throws SQLException on error
	 */
	public int update(
			@NonNull CharSequence sql, 
			@NonNull Iterable<?> params)
	throws SQLException {
		return update(sql, Iterables.toArray(params, Object.class));
	}
	/**
	 * Execute a single update-like statement.
	 * @param sql the query
	 * @param params the query parameters
	 * @return the update count
	 * @throws SQLException on error
	 */
	public int update(
			@NonNull CharSequence sql, 
			Object... params) throws SQLException {
		try {
			PreparedStatement pstmt = prepare(false, sql, params);
			try {
				return pstmt.executeUpdate();
			} finally {
				pstmt.close();
			}
		} catch (SQLException ex) {
			System.err.println(sql);
			throw ex;
		}
	}
	/**
	 * Returns a boolean field which can be null.
	 * @param rs the result set
	 * @param index the column index
	 * @return the boolean value
	 * @throws SQLException on error
	 */
	public static Boolean getBoolean(ResultSet rs, int index) throws SQLException {
		boolean b = rs.getBoolean(index);
		if (rs.wasNull()) {
			return null;
		}
		return b;
	}
	/**
	 * Check the validity of the underlying connection.
	 * @param n the timeout in seconds
	 * @return true if the connection is valid
	 * @throws SQLException on error
	 */
	public boolean isValid(int n) throws SQLException {
		return conn.isValid(n);
	}
	/**
	 * Check if the underlying connection is closed.
	 * @return true if closed
	 * @throws SQLException on error
	 */
	public boolean isClosed() throws SQLException {
		return conn.isClosed();
	}
	/**
	 * Check if the underlying connection is read-only.
	 * @return true if read only
	 * @throws SQLException on error
	 */
	public boolean isReadOnly() throws SQLException {
		return conn.isReadOnly();
	}
	/**
	 * Returns the database metadata object.
	 * @return the database metadata
	 * @throws SQLException on error
	 */
	public DatabaseMetaData getMetaData()  throws SQLException {
		return conn.getMetaData();
	}
	/**
	 * Insert a single record by using the given marshaller.
	 * @param <T> the record type
	 * @param sql the query
	 * @param marshaller the marshaller
	 * @param value the value
	 * @return the generated identifier or zero if somehow none was generated
	 * @throws SQLException on error
	 */
	public <T> long insertAuto(CharSequence sql, 
			Action2E<PreparedStatement, T, SQLException> marshaller, 
			T value) throws SQLException {
		try (PreparedStatement pstmt = prepare(true, sql)) {
			marshaller.call(pstmt, value);
			pstmt.executeUpdate();
			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					return rs.getLong(1);
				}
			}
		} catch (SQLException ex) {
			System.err.println(sql);
			throw ex;
		}
		return 0L;
	}
	/**
	 * Update a single record by using the given marshaller to set the
	 * parameters.
	 * @param <T> the record type
	 * @param sql the SQL query
	 * @param marshaller the marshaller
	 * @param value the value
	 * @return the update count
	 * @throws SQLException on error
	 */
	public <T> int update(CharSequence sql, 
			Action2E<PreparedStatement, T, SQLException> marshaller, T value) throws SQLException {
		try (PreparedStatement pstmt = prepare(sql)) {
			marshaller.call(pstmt, value);
			return pstmt.executeUpdate();
		} catch (SQLException ex) {
			System.err.println(sql);
			throw ex;
		}
	}
	/**
	 * Returns a builder for a prepared statement.
	 * @param sql the query
	 * @return the builder
	 * @throws SQLException one error
	 */
	public DBParams prepareBuild(CharSequence sql) throws SQLException {
		return new DBParams(prepare(sql));
	}
	/**
	 * Returns a builder for a prepared statement with optional
	 * automatic results.
	 * @param auto prepare for auto-generated keys?
	 * @param sql the query
	 * @return the builder
	 * @throws SQLException one error
	 */
	public DBParams prepareBuild(boolean auto, CharSequence sql) throws SQLException {
		return new DBParams(prepare(auto, sql));
	}
	/**
	 * Create a new connection with the same parameters
	 * as this connection.
	 * @return the new database connection
	 */
	public DB copy() {
		if (dbi != null) {
			return connect(dbi);
		}
		throw new IllegalStateException("DB was not created from a DBInfo object");
	}
}
