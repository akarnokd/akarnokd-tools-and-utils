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

package hu.akarnokd.games.midnightmysteries;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.Lists;

/**
 * Solve the battleship-like puzzle in
 * Midnight Mysteries 3.
 * @author akarnokd, 2012.07.28.
 */
public final class MidnightMysteries3BattleshipSolver {
	/** Utility class. */
	private MidnightMysteries3BattleshipSolver() { }
	/**
	 * @param args no arguments
	 */
	public static void main(String[] args) {
		final int[][] rows = {
				{1, 2},
				{1, 1, 1},
				{2, 1, 1},
				{1, 1},
				{1},
				{3, 3},
				{1},
				{1},
				{1, 1},
				{1, 1}
		};
		final int[][] cols = {
				{2, 1, 2},
				{1},
				{1, 1},
				{1, 1},
				{1, 1},
				{1},
				{3, 1},
				{},
				{4, 4},
				{}
		};
		
		// number of indexth length ships
		int[] ships = {0, 0, 4, 3, 2};
		
		verify(rows, cols, ships);
		
		// verification succeded
		// numbers in the secondary arrays define the order from left to right and top to bottom.

		// create ships
		List<Ship> shipList = Lists.newArrayList();
		int j3 = 0;
		for (int s : ships) {
			for (int k = 0; k < s; k++) {
				Ship sp = new Ship();
				sp.length = j3;
				shipList.add(sp);
			}
			j3++;
		}

		List<Ship> shipList2 = Lists.newArrayList(shipList);
		
		// place ships into their respective columsn or rows
		int r0 = 0;
		for (int[] r : rows) {
			for (int q : r) {
				if (q > 1) {
					Ship s = takeOfLength(shipList2, q);
					s.horizontal = true;
					s.row = r0;
				}
			}
			r0++;
		}
		r0 = 0;
		for (int[] c : cols) {
			for (int q : c) {
				if (q > 1) {
					Ship s = takeOfLength(shipList2, q);
					s.col = r0;
				}
			}
			r0++;
		}
		
		long max = 1;
		for (int i = 0; i < shipList.size(); i++) {
			max *= 10;
		}
		
		int n = Runtime.getRuntime().availableProcessors();
		ExecutorService exec = Executors.newFixedThreadPool(n);
		
		for (int core = 0; core < n; core++) {
			int start = (int)(core * max / n);
			int end = (int)((core + 1) * max / n);
			if (core == n - 1) {
				end = (int)max;
			}
			
			final int fstart = start;
			final int fend = end;
			final Object lock = new Object();
			final List<Ship> fshipList = copy(shipList);
			
			exec.execute(new Runnable() {
				@Override
				public void run() {
					
					List<Ship> vs = Lists.newArrayList();
					List<Ship> hs = Lists.newArrayList();
					for (Ship s : fshipList) {
						if (s.horizontal) {
							hs.add(s);
						} else {
							vs.add(s);
						}
					}
					
					outer:
					for (int lc = fstart; lc < fend; lc++) {
							if (lc % 1000000 == 0) {
								System.out.printf("%s | %9d: %.3f%%%n", 
										Thread.currentThread().getId(), 
										lc, (lc - fstart) * 100d / (fend - fstart));
							}
						int lc2 = lc;
						for (int i = 0; i < fshipList.size(); i++) {
							Ship s = fshipList.get(i);
							int p = lc2 % 10;
							
							if (p + s.length > 10) {
								continue outer;
							}
							if (s.horizontal) {
								s.col = p;
							} else {
								s.row = p;
							}
							
							lc2 /= 10;
						}
						if (checkOverlaps(hs) || checkOverlaps(vs)) {
							continue;
						}
						
						if (checkLayout(fshipList, rows, cols)) {
							// the play table
							int[][] table = new int[rows.length][cols.length];
							
							shipToTable(fshipList, table);
							// print the play table
							synchronized (lock) {
								printTable(table);
							}
						}
					}
				}
			});
		}

		
		// solution = 161408023
	}
	/**
	 * Create a deep copy of the list.
	 * @param ss the ships
	 * @return the copy
	 */
	static List<Ship> copy(List<Ship> ss) {
		List<Ship> result = Lists.newArrayList();
		for (Ship s : ss) {
			Ship s2 = new Ship();
			s2.horizontal = s.horizontal;
			s2.row = s.row;
			s2.col = s.col;
			s2.length = s.length;
			result.add(s2);
		}
		return result;
	}
	/**
	 * Check if two ships overlap.
	 * @param ss the ship list.
	 * @return true if overlapping detected
	 */
	static boolean checkOverlaps(List<Ship> ss) {
		for (int i = 0; i < ss.size(); i++) {
			Ship s1 = ss.get(i);
			for (int j = i + 1; j < ss.size(); j++) {
				Ship s2 = ss.get(j);
				
				if (s1.horizontal == s2.horizontal) {
					if (s1.horizontal) {
						if (s1.row != s2.row) {
							continue;
						}
						if (!(s1.col + s1.length <= s2.col || s2.col + s2.length <= s1.col)) {
							return true;
						}
					} else {
						if (s1.col != s2.col) {
							continue;
						}
						if (!(s1.row + s1.length <= s2.row || s2.row + s2.length <= s1.row)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	/**
	 * Check if the current layout produces the given row and column values.
	 * @param shipList the list of ships
	 * @param rows the row numbers
	 * @param cols the colum numbers
	 * @return true if the layout matches the row and column numbers
	 */
	private static boolean checkLayout(List<Ship> shipList, int[][] rows,
			int[][] cols) {

		for (int i = 0; i < rows.length; i++) {
			List<Ship> inRow = Lists.newArrayList();
			for (Ship s : shipList) {
				if (s.horizontal && s.row == i) {
					inRow.add(s);
				} else
				if (!s.horizontal && s.row <= i && i < s.row + s.length) {
					inRow.add(s);
				}
			}
			if (inRow.size() != rows[i].length) {
				return false;
			}
			Collections.sort(inRow, new Comparator<Ship>() {
				@Override
				public int compare(Ship o1, Ship o2) {
					return Integer.compare(o1.col, o2.col);
				}
			});
			int j = 0;
			for (Ship s : inRow) {
				if ((rows[i][j] != 1 && !s.horizontal)
						|| (s.horizontal && rows[i][j] != s.length)) {
					return false;
				}
				j++;
			}
		}
		
		for (int i = 0; i < cols.length; i++) {
			List<Ship> inRow = Lists.newArrayList();
			for (Ship s : shipList) {
				if (!s.horizontal && s.col == i) {
					inRow.add(s);
				} else
				if (s.horizontal && s.col <= i && i < s.col + s.length) {
					inRow.add(s);
				}
			}
			if (inRow.size() != cols[i].length) {
				return false;
			}
			Collections.sort(inRow, new Comparator<Ship>() {
				@Override
				public int compare(Ship o1, Ship o2) {
					return Integer.compare(o1.row, o2.row);
				}
			});
			int j = 0;
			for (Ship s : inRow) {
				if ((cols[i][j] != 1 && s.horizontal)
						|| (!s.horizontal && cols[i][j] != s.length)) {
					return false;
				}
				j++;
			}
		}

		return true;
	}
	/**
	 * Verify the row and column numbers and available ship counts.
	 * @param rows the row numbers
	 * @param cols the column numbers
	 * @param ships the ship numbers indexed by to length
	 */
	static void verify(int[][] rows, int[][] cols, int[] ships) {
		// verify data
		if (rows.length != 10) {
			throw new IllegalArgumentException("Not enough rows");
		}
		if (cols.length != 10) {
			throw new IllegalArgumentException("Not enough columns");
		}
		
		int[] ship2 = ships.clone();
		for (int[] r : rows) {
			for (int q : r) {
				if (q > 1) {
					ship2[q]--;
				}
			}
		}
		for (int[] c : cols) {
			for (int q : c) {
				if (q > 1) {
					ship2[q]--;
				}
			}
		}
		int j2 = 0;
		for (int q : ship2) {
			if (q != 0) {
				throw new IllegalArgumentException("Ship " + j2 + " is not covered in rows or columns!");
			}
			j2++;
		}
	}
	/**
	 * Convert the ship list and its coordinates into the table.
	 * @param ss the ships
	 * @param table the table
	 */
	static void shipToTable(List<Ship> ss, int[][] table) {
		// clear table
		for (int[] r : table) {
			Arrays.fill(r, 0);
		}
		// place ships
		for (Ship s : ss) {
			if (s.horizontal && s.col >= 0) {
				for (int k = s.col; k < s.col + s.length; k++) {
					table[s.row][k] = s.length;
				}
			} else 
			if (s.row >= 0) {
				for (int k = s.row; k < s.row + s.length; k++) {
					table[k][s.col] = s.length;
				}
			}
		}
	}
	/**
	 * Take one ship from the list which has a length provided.
	 * @param ss the ships
	 * @param len the length
	 * @return a ship
	 */
	static Ship takeOfLength(List<Ship> ss, int len) {
		Iterator<Ship> it = ss.iterator();
		while (it.hasNext()) {
			Ship s = it.next();
			if (s.length == len) {
				it.remove();
				return s;
			}
		}
		throw new NoSuchElementException("No ship with length " + len);
	}
	/** The ship object. */
	static class Ship {
		/** The length of the ship. */
		int length;
		/** Is it facing horizontally? */
		boolean horizontal;
		/** The start row. */
		int row;
		/** The start column. */
		int col;
		@Override
		public String toString() {
			if (horizontal) {
				return "H" + length + ": " + row + ", " + col;
			}
			return "V" + length + ": " + row + ", " + col;
		}
	}
	/**
	 * Print the contents of the table.
	 * @param table the table to print
	 */
	private static void printTable(int[][] table) {
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				if (table[i][j] > 0) {
					System.out.print(table[i][j]);
					System.out.print(' ');
				} else {
					System.out.print("  ");
				}
			}
			System.out.println("|");
		}
		for (int i = 0; i < table.length; i++) {
			System.out.print("--");
		}
		System.out.println();
	}

}
