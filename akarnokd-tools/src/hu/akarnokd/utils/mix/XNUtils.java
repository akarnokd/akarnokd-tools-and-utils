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

package hu.akarnokd.utils.mix;

import hu.akarnokd.utils.database.SQLResult;
import hu.akarnokd.utils.xml.XNElement;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.stream.XMLStreamException;

import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Utilities for the XNElement class.
 * @author akarnokd, 2013.08.08.
 */
public final class XNUtils {
	/** Utility class. */
	private XNUtils() { }
	/**
	 * Returns a SQL result function which reads an XML from the
	 * specified column.
	 * @param column the column name
	 * @return the SQL result function 
	 */
	@NonNull
	public static SQLResult<XNElement> fromResultSet(@NonNull final String column) {
		return new SQLResult<XNElement>() {
			@Override
			public XNElement invoke(ResultSet rs) throws SQLException {
				try {
					return XNElement.parseXML(rs, column);
				} catch (IOException | XMLStreamException ex) {
					throw new SQLException(ex);
				}
			}
		};
	}
	/**
	 * Returns a SQL result function which reads an XML from the
	 * specified column, or returns the default value in case of null
	 * column or parsing errors.
	 * @param column the column name
	 * @param defaultValue the default value
	 * @return the SQL result function 
	 */
	@NonNull
	public static SQLResult<XNElement> fromResultSet(@NonNull final String column, 
			final XNElement defaultValue) {
		return new SQLResult<XNElement>() {
			@Override
			public XNElement invoke(ResultSet rs) throws SQLException {
				try {
					XNElement e = XNElement.parseXML(rs, column);
					return e != null ? e : defaultValue;
				} catch (IOException | XMLStreamException ex) {
					return defaultValue;
				}
			}
		};
	}
	/**
	 * Returns a SQL result function which reads an XML from the
	 * specified column.
	 * @param index the column index
	 * @return the SQL result function 
	 */
	@NonNull
	public static SQLResult<XNElement> fromResultSet(@NonNull final int index) {
		return new SQLResult<XNElement>() {
			@Override
			public XNElement invoke(ResultSet rs) throws SQLException {
				try {
					return XNElement.parseXML(rs, index);
				} catch (IOException | XMLStreamException ex) {
					throw new SQLException(ex);
				}
			}
		};
	}
	/**
	 * Returns a SQL result function which reads an XML from the
	 * specified column, or returns the default value in case of null
	 * column or parsing errors.
	 * @param index the column index
	 * @param defaultValue the default value
	 * @return the SQL result function 
	 */
	@NonNull
	public static SQLResult<XNElement> fromResultSet(@NonNull final int index, 
			final XNElement defaultValue) {
		return new SQLResult<XNElement>() {
			@Override
			public XNElement invoke(ResultSet rs) throws SQLException {
				try {
					XNElement e = XNElement.parseXML(rs, index);
					return e != null ? e : defaultValue;
				} catch (IOException | XMLStreamException ex) {
					return defaultValue;
				}
			}
		};
	}
}
