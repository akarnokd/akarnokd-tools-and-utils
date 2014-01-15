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

package hu.akarnokd.utils.math;

import edu.umd.cs.findbugs.annotations.NonNull;

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
	public static void transpose(@NonNull double[][] in, @NonNull double[][] out) {
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
	public static double[][] transpose(@NonNull double[][] in) {
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
	public static double[][] projectRows(@NonNull double[][] in, @NonNull int[] indices) {
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
	public static double[][] projectColumns(@NonNull double[][] in, @NonNull int[] indices) {
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
	public static boolean isRegular(@NonNull double[][] in) {
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
	public static double[][] copy(@NonNull double[][] in) {
		double[][] r = new double[in.length][in[0].length];
		for (int i = 0; i < in.length; i++) {
			System.arraycopy(in[i], 0, r[i], 0, r[0].length);
		}
		return r;
	}
	/**
	 * Multiplies a column-oriented matrix with a row vector.
	 * @param columnMatrix the column-oriented matrix, n col * m row
	 * @param rowVector the row vector, m row
	 * @param out the output, m row, null to create a new one
	 * @return the out parameter
	 */
	public static double[] multiplyTransposed(@NonNull double[][] columnMatrix, 
			@NonNull double[] rowVector, double[] out) {
		if (out == null) {
			out = new double[columnMatrix[0].length];
		}
		if (columnMatrix.length != rowVector.length) {
			throw new IllegalArgumentException("Matrix columns != row vector size: " + columnMatrix.length + " vs. " + rowVector);
		}
		int j = 0;
		for (double[] col : columnMatrix) {
			double v = rowVector[j];
			for (int i = 0; i < col.length; i++) {
				out[i] += col[i] * v;
			}
			j++;
		}
		return out;
	}
	/**
	 * Subtracts the values of the first vector from the second vector and puts the result into out.
	 * @param first the first vector
	 * @param second the second vector
	 * @param out the output vector, null to create a new one
	 * @return the out vector
	 */
	public static double[] subtract(double[] first, double[] second, double[] out) {
		if (out == null) {
			out = new double[first.length];
		}
		if (first.length != second.length || second.length != out.length || first.length != out.length) {
			throw new IllegalArgumentException("Same size vectors expected: " + first.length + ", " + second.length + ", " + out.length);
		}
		for (int i = 0; i < first.length; i++) {
			out[i] = first[i] - second[i];
		}
		return out;
	}
	/**
	 * Adds the values of the first vector from the second vector and puts the result into out.
	 * @param first the first vector
	 * @param second the second vector
	 * @param out the output vector, null to create a new one
	 * @return the out vector
	 */
	public static double[] add(double[] first, double[] second, double[] out) {
		if (out == null) {
			out = new double[first.length];
		}
		if (first.length != second.length || second.length != out.length || first.length != out.length) {
			throw new IllegalArgumentException("Same size vectors expected: " + first.length + ", " + second.length + ", " + out.length);
		}
		for (int i = 0; i < first.length; i++) {
			out[i] = first[i] + second[i];
		}
		return out;
	}
	/**
	 * Squares each element of the input vector and puts it into the out vector.
	 * @param in the input vector
	 * @param out the output vector, null to create a new one
	 * @return the out vector
	 */
	public static double[] square(double[] in, double[] out) {
		if (out == null) {
			out = new double[in.length];
		}
		if (in.length != out.length) {
			throw new IllegalArgumentException("in.length != out.length: " + in.length + " vs. " + out.length);
		}
		for (int i = 0; i < in.length; i++) {
			double a = in[i];
			out[i] = a * a;
		}
		return out;
	}
	/**
	 * Returns a row-vector for the given column-oriented matrix and row index.
	 * @param columnMatrix the column-oriented matrix
	 * @param row the row index
	 * @param out the out vector, null to create a new one
	 * @return the out vector
	 */
	public static double[] getRow(double[][] columnMatrix, int row, double[] out) {
		if (out == null) {
			out = new double[columnMatrix.length];
		}
		for (int i = 0; i < columnMatrix.length; i++) {
			out[i] = columnMatrix[i][row];
		}
		return out;
	}
}
