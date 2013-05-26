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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.umd.cs.findbugs.annotations.NonNull;

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
		while (clazz != null) {
			result.addAll(Arrays.asList(clazz.getFields()));
			clazz = clazz.getSuperclass();
		}
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
		while (clazz != null) {
			for (Field f : clazz.getFields()) {
				if (f.isAnnotationPresent(annot)) {
					result.add(f);
				}
			}
			clazz = clazz.getSuperclass();
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
}
