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
package hu.akarnokd.utils.lang;

/**
 * Functional interface taking one parameter and throwing an exception.
 * @author karnokd, 2013.11.06.
 * @param <T1> the first parameter type
 * @param <R> the result type
 * @param <E> the exception type
 */
public interface Func1E<T1, R, E extends Exception> {
	/**
	 * Invokes the function.
	 * @param t the first parameter
	 * @return the result
	 * @throws E the exception
	 */
	R call(T1 t) throws E;
}
