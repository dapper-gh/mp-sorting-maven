package edu.grinnell.csc207.sorting;

import java.util.Comparator;

/**
 * Something that sorts using merge sort.
 *
 * @param <T>
 *   The types of values that are sorted.
 *
 * @author David William Stroud
 * @author Samuel A. Rebelsky
 */

public class MergeSorter<T> implements Sorter<T> {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The way in which elements are ordered.
   */
  Comparator<? super T> order;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a sorter using a particular comparator.
   *
   * @param comparator
   *   The order in which elements in the array should be ordered
   *   after sorting.
   */
  public MergeSorter(Comparator<? super T> comparator) {
    this.order = comparator;
  } // MergeSorter(Comparator)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Sorts a slice of an array out of place.
   * @param values The full array to sort.
   * @param start The first index to sort, inclusive.
   * @param end The last index to sort, exclusive.
   *
   * @return A new, sorted array.
   */
  @SuppressWarnings({ "unchecked" })
  private T[] sortSlice(T[] values, int start, int end) {
    if ((end - start) == 1) {
      // The slice is just one element, so it is sorted.
      return (T[]) new Object[] {values[start]};
    } else if ((end - start) == 0) {
      // The slice is empty, so it is sorted.
      return (T[]) new Object[0];
    } // if

    int middle = start / 2 + end / 2;
    if ((start % 2) == 1 && (end % 2) == 1) {
      // We need to make an adjustment if they are both odd.
      middle++;
    } // if
    T[] firstSlice = this.sortSlice(values, start, middle);
    T[] secondSlice = this.sortSlice(values, middle, end);

    T[] sorted = (T[]) new Object[end - start];
    int usedFirst = 0;
    int usedSecond = 0;
    for (int index = 0; index < sorted.length; index++) {
      if (usedFirst == firstSlice.length) {
        sorted[index] = secondSlice[usedSecond++];
      } else if (usedSecond == secondSlice.length) {
        sorted[index] = firstSlice[usedFirst++];
      } else {
        if (this.order.compare(firstSlice[usedFirst], secondSlice[usedSecond]) <= 0) {
          sorted[index] = firstSlice[usedFirst++];
        } else {
          sorted[index] = secondSlice[usedSecond++];
        } // if-else
      } // if-else
    } // for
    return sorted;
  } // sortSlice(T[], int, int)

  /**
   * Sort an array in place using merge sort.
   *
   * @param values
   *   an array to sort.
   *
   * @post
   *   The array has been sorted according to some order (often
   *   one given to the constructor).
   * @post
   *   For all i, 0 &lt; i &lt; vals.length,
   *     order.compare(vals[i-1], vals[i]) &lt;= 0
   */
  @Override
  public void sort(T[] values) {
    T[] sorted = this.sortSlice(values, 0, values.length);
    System.arraycopy(
            sorted,
            0,
            values,
            0,
            sorted.length
    );
  } // sort(T[])
} // class MergeSorter
