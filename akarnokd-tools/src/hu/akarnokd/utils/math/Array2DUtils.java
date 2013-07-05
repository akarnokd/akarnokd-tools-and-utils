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

package hu.akarnokd.utils.math;

/**
 * Two dimensional array (matrix) support utilities.
 * @author akarnokd, 2013.07.05.
 */
public final class Array2DUtils {
	/** Utility class. */
	private Array2DUtils() { }
	/**
	 * Transposes the given input array, i.e., turning
	 * between row-oriented and column oriented representations.
	 * @param in the input array, n * m
	 * @param out the output array, m * n
	 */
	public static void transpose(double[][] in, double[][] out) {
		for (int i = 0; i < in.length; i++) {
			for (int j = 0; j < in[0].length; j++) {
				out[j][i] = in[i][j];
			}
		}
	}
	/**
	 * Returns a new array with the transpose of the input
	 * array, i.e., turning
	 * between row-oriented and column oriented representations.
	 * @param in the input array, n * m
	 * @return the output array, m * n
	 */
	public static double[][] transpose(double[][] in) {
		double[][] r = new double[in[0].length][in.length];
		transpose(in, r);
		return r;
	}
	/**
	 * Returns a sub-matrix of the given input matrix by
	 * keeping the rows (first dimension) specified by
	 * the indices. The original rows are referenced
	 * in the result.
	 * @param in the input matrix, n * m
	 * @param indices the indices to select the second dimension elements (k)
	 * @return the projected matrix, k * m
	 */
	public static double[][] projectRows(double[][] in, int[] indices) {
		double[][] r = new double[indices.length][];
		for (int i = 0; i < indices.length; i++) {
			r[i] = in[indices[i]];
		}
		return r;
	}
	/**
	 * Returns a new matrix with only the columns at the specified
	 * indices and all rows.
	 * @param in the input matrix n * m
	 * @param indices the column indices to keep
	 * @return the projected matrix, n * k
	 */
	public static double[][] projectColumns(double[][] in, int[] indices) {
		double[][] r = new double[in.length][indices.length];
		for (int i = 0; i < indices.length; i++) {
			int j2 = indices[i];
			for (int j = 0; j < in.length; j++) {
				r[j][i] = in[j][j2];
			}
		}
		return r;
	}
	/**
	 * Checks if all row dimension is the same, i.e.,
	 * the input represents a proper n * m matrix.
	 * @param in the input matrix
	 * @return true if regular
	 */
	public static boolean isRegular(double[][] in) {
		for (int i = 1; i < in.length; i++) {
			if (in[i - 1].length != in[i].length) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Creates a deep copy of the given matrix.
	 * @param in the input matrix, n * m
	 * @return the copy matrix, n * m
	 */
	public static double[][] copy(double[][] in) {
		double[][] r = new double[in.length][in[0].length];
		for (int i = 0; i < in.length; i++) {
			System.arraycopy(in[i], 0, r[i], 0, r[0].length);
		}
		return r;
	}
}
