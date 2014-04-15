package com.exercise;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * <p>
 * Implementation of an {@link Iterator} that returns a SORTED stream of results
 * from a collection of input {@link Iterator}s, where the values in the input
 * iterators are also sorted.
 * </p>
 * 
 * <p>
 * The values returned by the input iterators WILL ALWAYS BE SORTED according to
 * the same {@link Comparator} that is specified in {@link MergingIterator}'s
 * constructor. The input iterators will never contain <tt>null</tt>
 * </p>
 * 
 * <p>
 * The input iterators are expected to have a very large (or possibly infinite)
 * set of results, so this implementation returns results AS SOON AS THEY ARE
 * AVAILABLE, rather than waiting until the input iterators are drained before
 * returning results.
 * </p>
 * 
 * <p>
 * This class can also eliminate duplicate values from the input iterators
 * (making all output values distinct), if the <tt>eliminateDuplicates</tt> flag
 * is set in the constructor. Note that the specified {@link Comparator} is used
 * to determine if two objects are identical, rather than the objects'
 * {@link #equals(Object)} methods.
 * </p>
 * 
 * <p>
 * Note: this class is <em>not</em> thread-safe, and cannot be called
 * concurrently from multiple threads
 * </p>
 * 
 */
public class MergingIterator<T> implements Iterator<T> {

	/**
	 * Collection of input iterators
	 */
	private Collection<? extends Iterator<T>> iterators;

	/**
	 * Queue of iterator wrappers created from input iterators
	 */
	private Queue<IteratorWrapper<T>> queue;

	/**
	 * Last value returned
	 */
	private T lastVal;

	/**
	 * Comparator to compare items
	 */
	private Comparator<T> comparator;

	/**
	 * Flag deciding duplicates elimination
	 */
	private boolean eliminateDuplicates;

	/**
	 * <p>
	 * Creates a {@link MergingIterator} with the specified collection of input
	 * {@link Iterator}s and {@link Comparator}, eliminating duplicates if
	 * required.
	 * 
	 * <p>
	 * This constructor must NOT call the {@link Iterator#next()} or
	 * {@link #hasNext()} methods of the input iterators, as these methods may
	 * block, and the constructor needs to return as soon as possible.
	 * 
	 * @param iterators
	 *            The collection of input iterators, over which the results will
	 *            be returned. The values in these iterators WILL ALWAYS BE
	 *            SORTED by the specified {@link Comparator}.
	 * 
	 * @param comparator
	 *            The {@link Comparator} that is to be used to compare items.
	 *            The output values returned from {@link #next()} will be
	 *            ordered by this comparator, and the values in the input
	 *            iterators should also be ordered by it.
	 * 
	 * @param eliminateDuplicates
	 *            If this is set to <tt>true</tt> then the output values will
	 *            contain no duplicate values, according to the specified
	 *            {@link Comparator}.
	 */
	public MergingIterator(final Collection<? extends Iterator<T>> iterators,
			final Comparator<T> comparator, final boolean eliminateDuplicates) {
		this.comparator = comparator;
		this.eliminateDuplicates = eliminateDuplicates;
		this.iterators = iterators;
	}

	/**
	 * <p>
	 * This sets data. It includes following: <br>
	 * Create a  {@link PriorityQueue} with initial capacity and 
	 * {@link Comparator}. <br>
	 * For each input {@link Iterator} create {@link IteratorWrapper} and add to
	 * {@link Queue}.
	 */
	private void setData() {
		// New comparator for PriorityQueue which uses the input comparator:
		Comparator<IteratorWrapper<T>> itrComprtr = new Comparator<IteratorWrapper<T>>() {
			public int compare(IteratorWrapper<T> itrWrpr1,
					IteratorWrapper<T> itrWrpr2) {
				return comparator.compare(itrWrpr1.getCurVal(),
						itrWrpr2.getCurVal());
			}
		};
		// New PriorityQueue:
		queue = new PriorityQueue<IteratorWrapper<T>>(5, itrComprtr);
		// Create IteratorWrapper for each Iterator and add to Queue:
		for (Iterator<? extends T> itr : iterators) {
			if (itr.hasNext()) {
				queue.add(new IteratorWrapper<T>(itr, itr.next()));
			}
		}
	}

	/**
	 * @see Iterator#hasNext()
	 */
	public boolean hasNext() {
		// Set data in case queue is null:
		if (null == queue) {
			setData();
		}
		// Return true if queue is not empty otherwise false:
		return !queue.isEmpty();
	}

	/**
	 * Returns the next value from the input iterators, where values are
	 * returned <em>in order</em>, according to the {@link Comparator} supplied
	 * in the {@link #MergingIterator(Collection, Comparator, boolean)}
	 * constructor.
	 * 
	 * @return The next element in the iteration 
	 * @throws NoSuchElementException
	 *             if there are no more values to return
	 */
	public T next() {
		// Set data in case queue is null:
		if (null == queue) {
			setData();
		}
		// Remove the head of the queue and set iterator wrapper:
		IteratorWrapper<T> itrWrapper = queue.remove();
		// Above will throw NoSuchElementException if queue has no more items
		// Get current value of wrapper:
		T curVal = itrWrapper.getCurVal();
		// Set last value as current value:
		lastVal = curVal;
		// Update wrapper:
		if (itrWrapper.move()) {
			// Put wrapper back to queue as there are more items:
			queue.add(itrWrapper);
		}
		// Eliminate subsequent duplicates if expected:
		if (eliminateDuplicates) {
			eliminateDuplicates();
		}
		// Return current value which is next value from the input iterators:
		return curVal;
	}

	/**
	 * Eliminates duplicate values 
	 */
	private void eliminateDuplicates() {
		// Proceed only if last value is set:
		if (null != lastVal) {
			// Repeat until curVal and lastVal are same and queue is not empty:
			while (!queue.isEmpty()
					&& 0 == comparator.compare(lastVal, queue.element() 
							.getCurVal())) {
				// Remove the head of the queue and set iterator wrapper:
				IteratorWrapper<T> itrWrapper = queue.remove();
				// Update wrapper:
				if (itrWrapper.move()) {
					// Put wrapper back to queue as there are more items:
					queue.add(itrWrapper);
				}
			}
		}
	}

	
	/**
	 * Calling this method is not supported, and throws a
	 * {@link UnsupportedOperationException}
	 */
	public void remove() {
		throw new UnsupportedOperationException(
				"Calling remove() is not supported");
	}

	/**
	 * A wrapper class for iterator
	 */
	private static class IteratorWrapper<T> {

		/**
		 * Input iterator
		 */
		private Iterator<? extends T> itr;

		/**
		 * Current value
		 */
		private T curVal;

		/**
		 * Constructor
		 * 
		 * @param itr Input iterator
		 * @param curVal Current value of iterator
		 */
		public IteratorWrapper(Iterator<? extends T> itr, T curVal) {
			this.itr = itr;
			this.curVal = curVal;
		}

		/**
		 * Returns current value
		 * 
		 * @return Current value
		 */
		public T getCurVal() {
			return curVal;
		}

		/**
		 * Updates wrapper by assigning iterator's next value to current value
		 * and returns TRUE. If there are no more elements in the iterator then 
		 * returns FALSE.
		 * 
		 * @return boolean
		 */
		public boolean move() {
			if (itr.hasNext()) {
				curVal = itr.next();
				return true;
			} else {
				return false;
			}
		}

	}

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String a1[] = { "W", "T", "S", "A" };
		String a2[] = { "F", "D", "A" };
		String a3[] = { "G", "D", "A" };
		String a4[] = { "Y", "V", "J" };
		java.util.List<Iterator<String>> iterators = new java.util.ArrayList<Iterator<String>>();
		iterators.add(java.util.Arrays.asList(a1).iterator());
		iterators.add(java.util.Arrays.asList(a2).iterator());
		iterators.add(java.util.Arrays.asList(a3).iterator());
		iterators.add(java.util.Arrays.asList(a4).iterator());
		Comparator<String> comparator = new Comparator<String>() {
			public int compare(String s1, String s2) {
				return -1 * s1.compareTo(s2);
			}
		};
		MergingIterator<String> itr = new MergingIterator<String>(iterators,
				comparator, true);
		while (itr.hasNext()) {
			System.out.println(itr.next());
		}
	}

}