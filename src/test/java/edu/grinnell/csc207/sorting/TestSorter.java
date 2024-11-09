package edu.grinnell.csc207.sorting;

import edu.grinnell.csc207.util.ArrayUtils;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

/**
 * Tests of Sorter objects. Please do not use this class directly.
 * Rather, you should subclass it and initialize stringSorter and
 * intSorter in a static @BeforeAll method.
 *
 * @author David William Stroud
 * @author Samuel A. Rebelsky
 */
public class TestSorter {

  // +---------+-----------------------------------------------------
  // | Globals |
  // +---------+

  /**
   * The sorter we use to sort arrays of strings.
   */
  static Sorter<String> stringSorter = null;

  /**
   * The sorter we use to sort arrays of integers.
   */
  static Sorter<Integer> intSorter = null;

  // +-----------+---------------------------------------------------
  // | Utilities |
  // +-----------+

  /**
   * Given a sorted array and a permutation of the array, sort the
   * permutation and assert that it equals the original.
   *
   * @param <T>
   *   The type of values in the array.
   * @param sorted
   *   The sorted array.
   * @param perm
   *   The permuted sorted array.
   * @param sorter
   *   The thing to use to sort.
   */
  public <T> void assertSorts(T[] sorted, T[] perm, Sorter<? super T> sorter) {
    T[] tmp = perm.clone();
    sorter.sort(perm);
    assertArrayEquals(sorted, perm,
      () -> String.format("sort(%s) yields %s rather than %s",
          Arrays.toString(tmp), 
          Arrays.toString(perm), 
          Arrays.toString(sorted)));
  } // assertSorts

  // +-------+-------------------------------------------------------
  // | Tests |
  // +-------+

  /**
   * A fake test. I've forgotten why I've included this here. Probably
   * just to make sure that some test succeeds.
   */
  @Test
  public void fakeTest() {
    assertTrue(true);
  } // fakeTest()

  /**
   * Ensure that an array that is already in order gets sorted correctly.
   */
  @Test
  public void orderedStringTest() {
    if (null == stringSorter) {
      return;
    } // if
    String[] original = { "alpha", "bravo", "charlie", "delta", "foxtrot" };
    String[] expected = original.clone();
    assertSorts(expected, original, stringSorter);
  } // orderedStringTest

  /**
   * Ensure that an array that is ordered backwards gets sorted correctly.
   */
  @Test
  public void reverseOrderedStringTest() {
    if (null == stringSorter) {
      return;
    } // if
    String[] original = { "foxtrot", "delta", "charlie", "bravo", "alpha" };
    String[] expected = { "alpha", "bravo", "charlie", "delta", "foxtrot" };
    assertSorts(expected, original, stringSorter);
  } // orderedStringTest

  /**
   * Ensure that a randomly permuted version of a moderate-sized
   * array sorts correctly.
   */
  @Test 
  public void permutedIntegersTest() { 
    int SIZE = 100; 
    if (null == intSorter) { 
      return; 
    } // if
    Integer[] original = new Integer[SIZE];
    for (int i = 0; i < SIZE; i++) {
      original[i] = i;
    } // for
    Integer[] expected = original.clone();
    ArrayUtils.permute(original);
    assertSorts(expected, original, intSorter);
  } // permutedIntegers

  // +----------------------+----------------------------------------
  // | David Stroud's Tests |
  // +----------------------+

  /**
   * Ensure that the empty array is sorted without issues (i.e. not modified).
   */
  @Test
  public void testEmpty() {
    this.assertSorts(new String[] {}, new String[] {}, TestSorter.stringSorter);
    this.assertSorts(new Integer[] {}, new Integer[] {}, TestSorter.intSorter);
  } // testEmpty()

  /**
   * Ensure that an array of length one is sorted without issues (i.e. not modified).
   */
  @Test
  public void testOneLength() {
    this.assertSorts(new String[] {"hello"}, new String[] {"hello"}, stringSorter);
    this.assertSorts(new Integer[] {0}, new Integer[] {0}, intSorter);
  } // testOneLength()

  /**
   * Ensure that equal elements are bunched together in sorted arrays.
   */
  @Test
  public void testEqualBunching() {
    for (int a = -100; a < 100; a++) {
      for (int b = a + 1; b < 100; b++) {
        for (int c = b + 1; c < 100; c++) {
          Integer[] sorted = new Integer[] {a, b, b, c};

          this.assertSorts(sorted, new Integer[] {c, b, b, a}, intSorter);
          this.assertSorts(sorted, new Integer[] {a, b, b, c}, intSorter);
          this.assertSorts(sorted, new Integer[] {b, c, a, b}, intSorter);
          this.assertSorts(sorted, new Integer[] {a, b, c, b}, intSorter);
          this.assertSorts(sorted, new Integer[] {c, a, b, b}, intSorter);
        } // for
      } // for
    } // for
  } // testEqualBunching()

  /**
   * Ensure that a very large array (20k elements) is sorted correctly.
   */
  @Test
  public void testVeryLargeArray() {
    Integer[] sorted = new Integer[20000];
    for (int i = 0; i < sorted.length; i++) {
      sorted[i] = i * 3 - 200;
    } // for
    Integer[] permuted = new Integer[sorted.length];
    for (int i = 0; i < permuted.length; i++) {
      // Since 531 and 20k are coprime, this will always get a new index.
      permuted[i] = sorted[(i * 531) % sorted.length];
    } // for
    this.assertSorts(sorted, permuted, intSorter);
  } // testVeryLargeArray()

  /**
   * Ensure that an array of stringified integers is sorted correctly.
   */
  @Test
  public void testStringifiedIntegers() {
    // Maybe one less than a power of two will catch a bug.
    String[] sorted = new String[63];
    for (int i = 0; i < sorted.length; i++) {
      sorted[i] = Integer.valueOf(i / 10).toString() + Integer.valueOf(i % 10).toString();
    } // for
    String[] permuted = new String[sorted.length];
    for (int i = 0; i < permuted.length; i++) {
      // Since 15 and 31 are coprime, this will always get a new index.
      permuted[i] = sorted[(i * 15) % sorted.length];
    } // for
    this.assertSorts(sorted, permuted, stringSorter);
  }
} // class TestSorter
