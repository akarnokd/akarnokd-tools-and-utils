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
package hu.akarnokd.utils.lang;

/**
 * A three parameter action callback.
 * @author akarnokd, 2013.10.18.
 * @param <T1> the first parameter type
 * @param <T2> the second parameter type
 * @param <T3> the third parameter type
 */
public interface Action3<T1, T2, T3> {
	/**
	 * Invokes the action.
	 * @param t1 the first parameter
	 * @param t2 the second parameter
	 * @param t3 the third parameter
	 */
	void invoke(T1 t1, T2 t2, T3 t3);
}
