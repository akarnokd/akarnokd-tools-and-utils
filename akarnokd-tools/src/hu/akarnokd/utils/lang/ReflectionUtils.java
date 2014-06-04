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

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

/**
 * Utility methods for supporting reflection access.
 * @author akarnokd, 2013.05.26.
 */
public final class ReflectionUtils {
	/** Utility class. */
	private ReflectionUtils() { }
	/**
	 * Returns a list of all fields of the class, including
	 * any superclass fields recursively.
	 * @param clazz the target class
	 * @return the list of all fields
	 */
	@NonNull
	public static List<Field> allFields(@NonNull Class<?> clazz) {
		List<Field> result = new ArrayList<>();
		result.addAll(Arrays.asList(clazz.getFields()));
		return result;
	}
	/**
	 * Returns a list of all fields having the given annotation for the class, including
	 * any superclass fields recursively.
	 * @param clazz the target class
	 * @param annot the required annotation to be present
	 * @return the list of all fields
	 */
	@NonNull
	public static List<Field> allFields(@NonNull Class<?> clazz, 
			@NonNull Class<? extends Annotation> annot) {
		List<Field> result = new ArrayList<>();
		for (Field f : clazz.getFields()) {
			if (f.isAnnotationPresent(annot)) {
				result.add(f);
			}
		}
		return result;
	}
	/**
	 * Returns a list of all declared fields of the class, including
	 * any superclass fields recursively.
	 * @param clazz the target class
	 * @return the list of all fields
	 */
	@NonNull
	public static List<Field> allDeclaredFields(Class<?> clazz) {
		List<Field> result = new ArrayList<>();
		while (clazz != null) {
			result.addAll(Arrays.asList(clazz.getDeclaredFields()));
			clazz = clazz.getSuperclass();
		}
		return result;
	}
	/**
	 * Returns a list of declared fields having the given annotation for the class, including
	 * any superclass fields recursively.
	 * @param clazz the target class
	 * @param annot the required annotation to be present
	 * @return the list of all fields
	 */
	@NonNull
	public static List<Field> allDeclaredFields(@NonNull Class<?> clazz, 
			@NonNull Class<? extends Annotation> annot) {
		List<Field> result = new ArrayList<>();
		while (clazz != null) {
			for (Field f : clazz.getDeclaredFields()) {
				if (f.isAnnotationPresent(annot)) {
					result.add(f);
				}
			}
			clazz = clazz.getSuperclass();
		}
		return result;
	}
	/**
	 * Returns the parametrized type of the given interface implemented by
	 * the class or any of its superclass.
	 * @param inClazz the class to search
	 * @param theInterface the target interface to look for
	 * @return the parametrized type or null if not found
	 */
	@Nullable
	public static ParameterizedType getGenericInterface(@NonNull Class<?> inClazz, @NonNull Class<?> theInterface) {
		while (inClazz != null) {
			for (Type c : inClazz.getGenericInterfaces()) {
				if (c instanceof ParameterizedType) {
					ParameterizedType pt = (ParameterizedType) c;
					Type rawType = pt.getRawType();
					if (theInterface.equals(rawType)) {
						return pt;
					}
				}
			}
			inClazz = inClazz.getSuperclass();
		}
		return null;
	}
	/**
	 * Recursively checks each field for the given annotation and returns the
	 * first field found.
	 * @param clazz the starting class
	 * @param annot the annotation to look for
	 * @return the field or null if not found
	 */
	@Nullable
	public static Field declaredField(@NonNull Class<?> clazz, @NonNull Class<? extends Annotation> annot) {
		while (clazz != null) {
			for (Field f : clazz.getFields()) {
				if (f.isAnnotationPresent(annot)) {
					return f;
				}
			}
			clazz = clazz.getSuperclass();
		}
		return null;
	}
	/**
	 * Returns the value of a static field,
	 * converting the IllegalAccessException
	 * to IllegalArgumentException.
	 * @param <T> the expected value type
	 * @param f the field
	 * @return the field value
	 */
	public static <T> T get(@NonNull Field f) {
		return get(f, null);
	}
	/**
	 * Returns the value of a field,
	 * converting the IllegalAccessException
	 * to IllegalArgumentException.
	 * @param <T> the expected value type
	 * @param f the field
	 * @param instance the object instance
	 * @return the field value
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(@NonNull Field f, Object instance) {
		try {
			return (T)f.get(instance);
		} catch (IllegalAccessException ex) {
			throw new IllegalArgumentException(ex);
		}
	}
	/**
	 * Returns the value of a field,
	 * converting the IllegalAccessException
	 * to IllegalArgumentException.
	 * @param <T> the expected value type
	 * @param f the field
	 * @param clazz the expected class
	 * @param instance the object instance
	 * @return the field value
	 */
	public static <T> T get(@NonNull Field f, Class<T> clazz, Object instance) {
		try {
			return clazz.cast(f.get(instance));
		} catch (IllegalAccessException ex) {
			throw new IllegalArgumentException(ex);
		}
	}
	/**
	 * Returns a Func0 function which returns a new
	 * instance of the target class by invoking the
	 * default constructor.
	 * <p>Until Java 8 and T::new</p>
	 * @param <T> the type
	 * @param clazz the target class
	 * @return the function
	 */
	public static <T> Func0<T> constructor(Class<T> clazz) {
		try {
			final Constructor<T> c = clazz.getConstructor();
			return new Func0<T>() {
				@Override
				public T call() {
					try {
						return c.newInstance();
					} catch (InstantiationException | InvocationTargetException | IllegalAccessException ex) {
						throw new IllegalArgumentException(ex);
					}
				}
			};
		} catch (NoSuchMethodException ex) {
			throw new IllegalArgumentException(ex);
		}
	}
	/**
	 * Returns a Func1 function which returns a new
	 * instance of the target class by invoking the
	 * one parameter constructor.
	 * <p>Until Java 8 and T::new</p>
	 * @param <T> the type
	 * @param <U> the first parameter type
	 * @param clazz the target class
	 * @param param1 the first parameter class
	 * @return the function
	 */
	public static <T, U> Func1<U, T> constructor(Class<T> clazz, Class<U> param1) {
		try {
			final Constructor<T> c = clazz.getConstructor(param1);
			return new Func1<U, T>() {
				@Override
				public T call(U param1) {
					try {
						return c.newInstance(param1);
					} catch (InstantiationException | InvocationTargetException | IllegalAccessException ex) {
						throw new IllegalArgumentException(ex);
					}
				}
			};
		} catch (NoSuchMethodException ex) {
			throw new IllegalArgumentException(ex);
		}
	}
	/**
	 * Returns a Func1 function which returns a new
	 * instance of the target class by invoking the
	 * one parameter constructor.
	 * <p>Until Java 8 and T::new</p>
	 * @param <T> the type
	 * @param <U> the first parameter type
	 * @param <V> the second parameter type
	 * @param clazz the target class
	 * @param param1 the first parameter class
	 * @param param2 the second parameter class
	 * @return the function
	 */
	public static <T, U, V> Func2<U, V, T> constructor(Class<T> clazz, Class<U> param1, Class<V> param2) {
		try {
			final Constructor<T> c = clazz.getConstructor(param1, param2);
			return new Func2<U, V, T>() {
				@Override
				public T call(U param1, V param2) {
					try {
						return c.newInstance(param1, param2);
					} catch (InstantiationException | InvocationTargetException | IllegalAccessException ex) {
						throw new IllegalArgumentException(ex);
					}
				}
			};
		} catch (NoSuchMethodException ex) {
			throw new IllegalArgumentException(ex);
		}
	}
	/**
	 * Sets a field, converting the potential exceptions to
	 * IllegalArgumentException.
	 * @param f the field
	 * @param instance the instance
	 * @param value the new value
	 */
	public static void set(@NonNull Field f, Object instance, Object value) {
		try {
			f.set(instance, value);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
