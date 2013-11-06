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

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import com.google.common.collect.Iterables;

import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Class setting the values in a prepared statement by using the
 * builder pattern.
 * @author akarnokd, 2013.11.01.
 */
public class DBParams {
	/** The statement to fill in. */
	protected final PreparedStatement pstmt;
	/** The parameter counter. */
	protected int p;
	/**
	 * Constructor, sets the prepared statement.
	 * @param pstmt the prepared statement to use
	 */
	public DBParams(@NonNull PreparedStatement pstmt) {
		this.pstmt = pstmt;
		p = 1;
	}
	/**
	 * Reset the parameters.
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams reset() throws SQLException {
		p = 1;
		pstmt.clearParameters();
		return this;
	}
	/**
	 * Fix the parameter set as a batch.
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams addBatch() throws SQLException {
		pstmt.addBatch();
		p = 1;
		return this;
	}
	/**
	 * Execute the batch.
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams executeBatch() throws SQLException {
		pstmt.executeBatch();
		p = 1;
		return this;
	}
	/**
	 * Execute an update.
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams executeUpdate() throws SQLException {
		pstmt.executeUpdate();
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(boolean value) throws SQLException {
		pstmt.setBoolean(p++, value);
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(byte value) throws SQLException {
		pstmt.setByte(p++, value);
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(short value) throws SQLException {
		pstmt.setShort(p++, value);
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(int value) throws SQLException {
		pstmt.setInt(p++, value);
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(long value) throws SQLException {
		pstmt.setLong(p++, value);
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(float value) throws SQLException {
		pstmt.setFloat(p++, value);
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(double value) throws SQLException {
		pstmt.setDouble(p++, value);
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(Boolean value) throws SQLException {
		if (value != null) {
			pstmt.setBoolean(p++, value);
		} else {
			pstmt.setNull(p++, Types.BOOLEAN);
		}
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(Byte value) throws SQLException {
		if (value != null) {
			pstmt.setByte(p++, value);
		} else {
			pstmt.setNull(p++, Types.TINYINT);
		}
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(Short value) throws SQLException {
		if (value != null) {
			pstmt.setShort(p++, value);
		} else {
			pstmt.setNull(p++, Types.SMALLINT);
		}
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(Integer value) throws SQLException {
		if (value != null) {
			pstmt.setInt(p++, value);
		} else {
			pstmt.setNull(p++, Types.INTEGER);
		}
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(Long value) throws SQLException {
		if (value != null) {
			pstmt.setLong(p++, value);
		} else {
			pstmt.setNull(p++, Types.BIGINT);
		}
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(Float value) throws SQLException {
		if (value != null) {
			pstmt.setFloat(p++, value);
		} else {
			pstmt.setNull(p++, Types.FLOAT);
		}
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(Double value) throws SQLException {
		if (value != null) {
			pstmt.setDouble(p++, value);
		} else {
			pstmt.setNull(p++, Types.DOUBLE);
		}
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(String value) throws SQLException {
		pstmt.setString(p++, value);
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(LocalTime value) throws SQLException {
		if (value != null) {
			pstmt.setTime(p++, DB.toSQLTime(value));
		} else {
			pstmt.setNull(p++, Types.TIME);
		}
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(DateMidnight value) throws SQLException {
		if (value != null) {
			pstmt.setDate(p++, new Date(value.getMillis()));
		} else {
			pstmt.setNull(p++, Types.DATE);
		}
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(DateTime value) throws SQLException {
		if (value != null) {
			pstmt.setTimestamp(p++, new Timestamp(value.getMillis()));
		} else {
			pstmt.setNull(p++, Types.TIMESTAMP);
		}
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(Date value) throws SQLException {
		pstmt.setDate(p++, value);
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(Time value) throws SQLException {
		pstmt.setTime(p++, value);
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(Timestamp value) throws SQLException {
		pstmt.setTimestamp(p++, value);
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(BigDecimal value) throws SQLException {
		pstmt.setBigDecimal(p++, value);
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(BigInteger value) throws SQLException {
		if (value != null) {
			pstmt.setBigDecimal(p++, new BigDecimal(value));
		} else {
			pstmt.setNull(p++, Types.DECIMAL);
		}
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(byte[] value) throws SQLException {
		if (value != null) {
			pstmt.setBytes(p++, value);
		} else {
			pstmt.setNull(p++, Types.BLOB);
		}
		return this;
	}
	/**
	 * Add a new parameter value.
	 * @param value the value
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(InputStream value) throws SQLException {
		if (value != null) {
			pstmt.setBinaryStream(p++, value);
		} else {
			pstmt.setNull(p++, Types.BLOB);
		}
		return this;
	}
	/**
	 * Add multiple values as parameters.
	 * @param os the rest parameters
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(Object... os) throws SQLException {
		DB.setParams(p, pstmt, os);
		p += os.length;
		return this;
	}
	/**
	 * Add multiple values as parameters.
	 * @param os the rest parameters
	 * @return this
	 * @throws SQLException on error
	 */
	public DBParams add(Iterable<?> os) throws SQLException {
		return add(Iterables.toArray(os, Object.class));
	}
}
