package com.exercise;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Test for MergingIterator
 */
public class MergingIteratorTest {

	/**
	 * Test for unique elements in ascending order
	 */
	@Test
	public void uniqueAscendingTest() {
		String a1[] = { "A", "S", "T", "W" };
		String a2[] = { "A", "D", "F" };
		String a3[] = { "A", "D", "G" };
		String a4[] = { "J", "V", "Y" };
		String r = "ADFGJSTVWY";
		List<Iterator<String>> iterators = new ArrayList<Iterator<String>>();
		iterators.add(Arrays.asList(a1).iterator());
		iterators.add(Arrays.asList(a2).iterator());
		iterators.add(Arrays.asList(a3).iterator());
		iterators.add(Arrays.asList(a4).iterator());
		Comparator<String> comparator = new Comparator<String>() {
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		};
		MergingIterator<String> itr = new MergingIterator<String>(iterators,
				comparator, true);
		StringBuilder str = new StringBuilder();
		while (itr.hasNext()) {
			str.append(itr.next());
		}
		assertEquals(r, str.toString());
	}
	
	/**
	 * Test for unique elements in ascending order and 
	 * NoSuchElementException
	 */
	@Test(expected = NoSuchElementException.class) 
	public void uniqueAscendingExceptionTest() {
		String a1[] = { "A", "S", "T", "W" };
		String a2[] = { "A", "D", "F" };
		String a3[] = { "A", "D", "G" };
		String a4[] = { "J", "V", "Y" };
		String r = "ADFGJSTVWY";
		List<Iterator<String>> iterators = new ArrayList<Iterator<String>>();
		iterators.add(Arrays.asList(a1).iterator());
		iterators.add(Arrays.asList(a2).iterator());
		iterators.add(Arrays.asList(a3).iterator());
		iterators.add(Arrays.asList(a4).iterator());
		Comparator<String> comparator = new Comparator<String>() {
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		};
		MergingIterator<String> itr = new MergingIterator<String>(iterators,
				comparator, true);
		StringBuilder str = new StringBuilder();
		for (int i=0; i < 11; i++) {
			str.append(itr.next());
		}
	}

	/**
	 * Test for duplicate elements in ascending order
	 */
	@Test
	public void duplicateAscendingTest() {
		String a1[] = { "A", "S", "T", "W" };
		String a2[] = { "A", "D", "F" };
		String a3[] = { "A", "D", "G" };
		String a4[] = { "J", "V", "Y" };
		String r = "AAADDFGJSTVWY";
		List<Iterator<String>> iterators = new ArrayList<Iterator<String>>();
		iterators.add(Arrays.asList(a1).iterator());
		iterators.add(Arrays.asList(a2).iterator());
		iterators.add(Arrays.asList(a3).iterator());
		iterators.add(Arrays.asList(a4).iterator());
		Comparator<String> comparator = new Comparator<String>() {
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		};
		MergingIterator<String> itr = new MergingIterator<String>(iterators,
				comparator, false);
		StringBuilder str = new StringBuilder();
		while (itr.hasNext()) {
			str.append(itr.next());
		}
		assertEquals(r, str.toString());
	}

	/**
	 * Test for unique elements in descending order
	 */
	@Test
	public void uniqueDescendingTest() {
		String a1[] = { "W", "T", "S", "A" };
		String a2[] = { "F", "D", "A" };
		String a3[] = { "G", "D", "A" };
		String a4[] = { "Y", "V", "J" };
		String r = "YWVTSJGFDA";
		List<Iterator<String>> iterators = new ArrayList<Iterator<String>>();
		iterators.add(Arrays.asList(a1).iterator());
		iterators.add(Arrays.asList(a2).iterator());
		iterators.add(Arrays.asList(a3).iterator());
		iterators.add(Arrays.asList(a4).iterator());
		Comparator<String> comparator = new Comparator<String>() {
			public int compare(String s1, String s2) {
				return -1 * s1.compareTo(s2);
			}
		};
		MergingIterator<String> itr = new MergingIterator<String>(iterators,
				comparator, true);
		StringBuilder str = new StringBuilder();
		while (itr.hasNext()) {
			str.append(itr.next());
		}
		assertEquals(r, str.toString());
	}

	/**
	 * Test for duplicate elements in descending order
	 */
	@Test
	public void duplicateDescendingTest() {
		String a1[] = { "W", "T", "S", "A" };
		String a2[] = { "F", "D", "A" };
		String a3[] = { "G", "D", "A" };
		String a4[] = { "Y", "V", "J" };
		String r = "YWVTSJGFDDAAA";
		List<Iterator<String>> iterators = new ArrayList<Iterator<String>>();
		iterators.add(Arrays.asList(a1).iterator());
		iterators.add(Arrays.asList(a2).iterator());
		iterators.add(Arrays.asList(a3).iterator());
		iterators.add(Arrays.asList(a4).iterator());
		Comparator<String> comparator = new Comparator<String>() {
			public int compare(String s1, String s2) {
				return -1 * s1.compareTo(s2);
			}
		};
		MergingIterator<String> itr = new MergingIterator<String>(iterators,
				comparator, false);
		StringBuilder str = new StringBuilder();
		while (itr.hasNext()) {
			str.append(itr.next());
		}
		assertEquals(r, str.toString());
	}

	/**
	 * Test for duplicate elements in ascending order. Data
	 * loaded from a file
	 */
	@Test
	public void duplicateAscendingFileTest() {
		List<Iterator<String>> iterators = getIterators();

		Comparator<String> comparator = new Comparator<String>() {
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		};
		MergingIterator<String> itr = new MergingIterator<String>(iterators,
				comparator, false);
		StringBuilder strb = new StringBuilder();
		while (itr.hasNext()) {
			strb.append(itr.next());
		}
		assertEquals(strb.toString(), getResult("Result-Duplicate.txt"));
	}

	/**
	 * Test for unique elements in ascending order. Data loaded
	 * from a file
	 */
	@Test
	public void uniqueAscendingFileTest() {
		List<Iterator<String>> iterators = getIterators();

		Comparator<String> comparator = new Comparator<String>() {
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		};
		MergingIterator<String> itr = new MergingIterator<String>(iterators,
				comparator, true);
		StringBuilder strb = new StringBuilder();
		while (itr.hasNext()) {
			strb.append(itr.next());
		}
		assertEquals(strb.toString(), getResult("Result-Unique.txt"));
	}

	/**
	 * Loads and returns list of iterators from data file
	 * 
	 * @return list of iterators
	 */
	private List<Iterator<String>> getIterators() {
		List<Iterator<String>> iterators = new ArrayList<Iterator<String>>();

		try {
			URL url = MergingIteratorTest.class
					.getResource("/com/exercise/Input.txt");
			File file = new File(url.getFile());
			Scanner sc = new Scanner(file);

			List<String> group = new ArrayList<String>();
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (line.contains("[List]")) {
					if (group.size() == 0) {
						continue;
					} else {
						iterators.add(group.iterator());
						group = new ArrayList<String>();
					}
				} else {
					group.add(line);
				}
			}
			if (group.size() != 0) {
				iterators.add(group.iterator());
			}
			sc.close();
		} catch (FileNotFoundException exc) {

		}
		return iterators;
	}

	/**
	 * Loads and returns result string form result file
	 * 
	 * @return list of iterators
	 */
	private String getResult(String fileName) {
		StringBuilder strb = new StringBuilder();
		try {
			URL url = MergingIteratorTest.class.getResource("/com/exercise/"
					+ fileName);
			File file = new File(url.getFile());
			Scanner sc = new Scanner(file);
			List<String> group = new ArrayList<String>();
			while (sc.hasNextLine()) {
				strb.append(sc.nextLine());
			}
			sc.close();
		} catch (FileNotFoundException exc) {

		}
		return strb.toString();
	}

}
