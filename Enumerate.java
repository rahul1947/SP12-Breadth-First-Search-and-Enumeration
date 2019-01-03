package rsn170330.sp12;

import java.util.Comparator;

/**
 * CS 5V81.001: Implementation of Data Structures and Algorithms 
 * Short Project SP12: Breadth First Search and Enumeration
 * @author Rahul Nalawade (rsn170330)
 * 
 * Date: November 25, 2018
 */

//Permutations and Combinations of distinct items
public class Enumerate<T> {
	T[] arr; // array of elements
	int k; // size of permutation
	// NOTE: permutation is in arr[0..k-1]

	long count; // how many permutations/ enumerations we're visiting
	
	Approver<T> app;

	// ----------------------- Constructors ----------------------------------
	
	// reference to the array, k, and caller's own approver
	public Enumerate(T[] arr, int k, Approver<T> app) {
		this.arr = arr;
		this.k = k;
		this.count = 0;
		this.app = app;
	}
	
	// when k = n, taking all permutations of arr
	public Enumerate(T[] arr, Approver<T> app) {
		this(arr, arr.length, app);
	}
	
	// no approver (yes to everything)
	public Enumerate(T[] arr, int k) {
		this(arr, k, new Approver<T>());
	}

	public Enumerate(T[] arr) {
		this(arr, arr.length, new Approver<T>());
	}

	// -------------- Methods of Enumerate class: To do ----------------------

	/**
	 * Chooses c more elements from arr[k-c...n-1] elements, n = arr.length
	 * 
	 * Precondition: arr[0...k-c-1] have been selected
	 * 
	 * @param c number of elements needed to be chosen
	 */
	public void permute(int c) {
		
		if (c == 0) {
			visit(arr); // visit permutation in arr[0...k-1]
		}

		else {
			int d = k - c; // key index where selected element is placed 
			
			for (int i = d; i < arr.length; ++i) {
				
				if (app.select(arr[i])) {
					
					// swap arr[d] with arr[i]
					T temp = arr[d];
					arr[d] = arr[i];
					arr[i] = temp;
	
					// Permutations having arr[i] as the next element
					permute(c - 1);
	
					// Restore elements where they were before swap
					arr[i] = arr[d];
					arr[d] = temp;
					
					app.unselect(arr[i]);
				}
			}
		}
	}

	/**
	 * Choose c more elements from arr[i...n-1] 
	 * Precondition: arr[0...k-c-1] are already chosen
	 * 
	 * @param i the left index of the right sub-array arr[i...n-1]
	 * @param c the number of elements needed to be chosen
	 */
	public void combine(int i, int c) {

		if (c == 0) {
			visit(arr); // visit combination in arr[0...k-1]
		}

		else {
			// swap arr[d] with arr[i], where d = k-c
			T temp = arr[k - c];
			arr[k - c] = arr[i];
			arr[i] = temp;

			combine(i + 1, c - 1);

			// Restore elements where they were before swapping
			arr[i] = arr[k - c];
			arr[k - c] = temp;

			// When there are enough elements remaining
			if (arr.length - i > c) {
				combine(i + 1, c); // skip arr[i]
			}
		}
	}

	/**
	 * Generate all n! permutations with just a swap from previous permutation
	 * Precondition: arr[g...n-1] are frozen/ done.
	 * 
	 * @param g number of elements to go i.e. 
	 *    elements in arr[0...g-1] can only be changed
	 */
	public void heap(int g) {

		if (g == 1) {
			visit(arr); // visit permutation in arr[0...n-1]
		} 
		else {
			for (int i = 0; i < g - 1; ++i) {
				heap(g - 1);

				if (g % 2 == 0) {
					swap(i, g - 1);
				} else {
					swap(0, g - 1);
				}
			}
			heap(g - 1);
		}
	}
	
	/**
	 * Generates all n! permutations in lexicographic order, even when
	 * elements are not distinct.
	 * 
	 * Precondition: array A is sorted in natural order, i.e.
	 *  A[0] <= A[1] <= ... <= A[n-1]
	 * 
	 * @param c comparator
	 */
	public void algorithmL(Comparator<T> c) {
		int j, k;
		
		visit(arr);
		
		j = findJ(c);
		
		// NOTE: no need to have a decreasing array checker method :)
		while (j > -1) {
			k = findK(c, j);
			swap(j, k);
			
			reverse(j+1, arr.length - 1); 
			// now A[j+1...n-1] is in ascending order
			
			visit(arr);
			j = findJ(c);
		}
	}
	
	/**
	 * algorithmL() helper method: finds max index j such that A[j] < A[j+1]
	 * @param c comparator of the generic type used 
	 * @return the j index for the existing array
	 */
	private int findJ(Comparator<T> c) {
		int j = arr.length - 2;
		
		while (j >= 0 && c.compare(arr[j], arr[j+1]) >= 0) {
			j--;
		}
		
		return j;
	}
	
	/**
	 * algorithmL() helper method: finds max index k such that A[j] < A[k]
	 * @param c comparator of the generic type used
	 * @param j existing j index
	 * @return the k index for j index for the existing array
	 */
	private int findK(Comparator<T> c, int j) {
		int k = arr.length - 1;
		
		while (j < k && c.compare(arr[j], arr[k]) >= 0) {
			k--;
		}
		
		return k;
	}
	
	/**
	 * Main visit() method, that stores count in Enumerate class itself
	 * @param array that is passed to the relevant Approver's visit()
	 */
	public void visit(T[] array) {
		// increments count of this class 
		count++; // may not need to store count for other Enumeration classes
		app.visit(array, k); // and call appropriate visit
	}

	// -------------------- Nested class: Approver ---------------------------

	// Class to decide whether to extend a permutation with a selected item
	// Extend this class in algorithms that need to enumerate permutations 
	// with precedence constraints
	public static class Approver<T> {
		
		/* Extend permutation by item? */
		public boolean select(T item) {
			return true;
		}

		/* Backtrack selected item */
		public void unselect(T item) {
		}

		/* Visit a permutation or combination */
		public void visit(T[] array, int k) {
			
			for (int i = 0; i < k; i++) {
				System.out.print(array[i] + " ");
			}
			System.out.println();
		}
	}

	// ---------------------------- UTILITIES --------------------------------
	
	/* Swaps an element at i with element at j */
	void swap(int i, int j) {
		T tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	
	/* Elements from index low to high are reversed */
	void reverse(int low, int high) {
		while (low < high) {
			swap(low, high);
			low++;
			high--;
		}
	}

	// -------------------------- STATIC METHODS -----------------------------

	// Enumerate permutations of k items out of n = arr.length
	public static <T> Enumerate<T> permute(T[] arr, int k) {
		Enumerate<T> e = new Enumerate<>(arr, k);
		e.permute(k);
		return e;
	}

	// Enumerate combinations of k items out of n = arr.length
	public static <T> Enumerate<T> combine(T[] arr, int k) {
		Enumerate<T> e = new Enumerate<>(arr, k);
		e.combine(0, k);
		return e;
	}

	// Enumerate permutations of n = arr.length item, using Heap's algorithm
	public static <T> Enumerate<T> heap(T[] arr) {
		Enumerate<T> e = new Enumerate<>(arr, arr.length);
		e.heap(arr.length);
		return e;
	}

	// Enumerate permutations of items in array, using Knuth's algorithm L
	public static <T> Enumerate<T> algorithmL(T[] arr, Comparator<T> c) {
		Enumerate<T> e = new Enumerate<>(arr, arr.length);
		e.algorithmL(c);
		return e;
	}
	
	// --------------------------- MAIN METHOD -------------------------------
	public static void main(String args[]) {
		int n = 4;
		int k = 3;
		if (args.length > 0) {
			n = Integer.parseInt(args[0]);
			k = n;
		}
		if (args.length > 1) {
			k = Integer.parseInt(args[1]);
		}
		Integer[] arr = new Integer[n];
		for (int i = 0; i < n; i++) {
			arr[i] = i + 1;
		}

		System.out.println("Permutations: " + n + " " + k);
		Enumerate<Integer> e = permute(arr, k);
		System.out.println("Count: " + e.count + "\n_________________________");

		System.out.println("Combinations: " + n + " " + k);
		e = combine(arr, k);
		System.out.println("Count: " + e.count + "\n_________________________");

		System.out.println("Heap Permutations: " + n);
		e = heap(arr);
		System.out.println("Count: " + e.count + "\n_________________________");

		Integer[] test = { 1, 2, 2, 3, 3, 4 };
		System.out.println("Algorithm L Permutations: ");
		e = algorithmL(test, (Integer a, Integer b) -> a.compareTo(b));
		System.out.println("Count: " + e.count + "\n_________________________");
	}
}
