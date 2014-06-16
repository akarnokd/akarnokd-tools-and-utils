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

import java.sql.Time;

import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test the DB methods.
 * @author akarnokd, 2013.05.28.
 */
public class DBTest {
	/**
	 * Test SQL and LocalTime conversions.
	 */
	@Test
	public void testSQLAndLocalTime() {
		LocalTime t1 = new LocalTime(10, 0, 0, 0);
		Time time = DB.toSQLTime(t1);
		LocalTime t2 = DB.toLocalTime(time);
		Assert.assertEquals(t1, t2);
	}

}
