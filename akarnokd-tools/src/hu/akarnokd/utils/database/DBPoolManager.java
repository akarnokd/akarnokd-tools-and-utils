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

import hu.akarnokd.utils.database.DB.DBInfo;
import hu.akarnokd.utils.pool.PoolManager;
import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * A database pool manager.
 * @author akarnokd, 2013.11.07.
 */
public class DBPoolManager implements PoolManager<DB> {
	/** The connection info. */
	private final DBInfo dbi;
	/**
	 * Constructor, sets the connection info.
	 * @param dbi the connection info
	 */
	public DBPoolManager(@NonNull DBInfo dbi) {
		this.dbi = new DBInfo(dbi);
	}
	@Override
	public DB create() throws Exception {
		return DB.connect(dbi);
	}

	@Override
	public boolean verify(DB obj) throws Exception {
		if (obj != null) {
			return obj.conn.isValid(5);
		}
		return false;
	}

	@Override
	public void close(DB obj) throws Exception {
		if (obj != null) {
			obj.close();
		}
	}

}
