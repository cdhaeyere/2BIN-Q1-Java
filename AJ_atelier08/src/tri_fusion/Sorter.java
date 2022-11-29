package tri_fusion;

/**
 * Sort a table of int. Mono-thread version.
 */
public class Sorter {

	// Table to sort
	private int[] table;
	// Slice of the table to sort
	private int start, end;

	private int nbrThreads;

	public Sorter(int[] table) {
		this(table, 0, table.length - 1);
	}

	public Sorter(int[] t, int start, int end) {
		this.table = t;
		this.start = start;
		this.end = end;
	}

	/**
	 * Sort a table of int in ascending order
	 *
	 */
	public void sort() {
		this.sort(this.start, this.end);
	}

	/**
	 * Sort a slice of the table
	 * 
	 * @param start start index of the slice to sort
	 * @param end end index of the slice to sort
	 */
	private void sort(int start, int end) {
		if (end - start < 2) {
			if (table[start] > table[end]) {
				swap(start, end);
			}
		} else {
			int milieu = start + (end - start) / 2;
			SorterThread t1 = new SorterThread(start, milieu);
			SorterThread t2 = new SorterThread(milieu + 1, end);
			t1.start();
			t2.start();
			try {
				t1.join();
				t2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.mergeSort(start, end);
		}
	}

	/**
	 * Swap t[i] and t[j]
	 */
	private void swap(int i, int j) {
		int value = table[i];
		table[i] = table[j];
		table[j] = value;
	}

	/**
	 * Merge 2 sorted slices of the table.
	 * First slice from start to middle, and second slice from middle + 1 to end.
	 * 
	 * @param start start index of the slice to sort
	 * @param end end index of the slice to sort
	 */
	private void mergeSort(int start, int end) {
		// Table where the merge will be stored
		int[] tMerge = new int[end - start + 1];
		int middle = (start + end) / 2;
		// Indexes of the elements to compare
		int i1 = start;
		int i2 = middle + 1;
		// Index of the next cell of the tMerge table to fill
		int iFusion = 0;
		while (i1 <= middle && i2 <= end) {
			if (table[i1] < table[i2]) {
				tMerge[iFusion++] = table[i1++];
			} else {
				tMerge[iFusion++] = table[i2++];
			}
		}
		// First slice done
		if (i1 > middle) {
			for (int i = i2; i <= end;) {
				tMerge[iFusion++] = table[i++];
			}
		}
		// Second slice done
		else {
			for (int i = i1; i <= middle;) {
				tMerge[iFusion++] = table[i++];
			}
		}
		// Copy tMerge into the table
		for (int i = 0, j = start; i <= end - start;) {
			table[j++] = tMerge[i++];
		}
	}

	private class SorterThread extends Thread {

		private int start, end;

		public SorterThread(int start, int end) {
			nbrThreads++;
			this.start = start;
			this.end = end;
		}

		@Override
		public void run() {
			sort(start, end);
		}
	}
}
