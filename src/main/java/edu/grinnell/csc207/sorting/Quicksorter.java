package edu.grinnell.csc207.sorting;

import java.util.Comparator;

/**
 * Something that sorts using Quicksort.
 *
 * @param <T>
 *   The types of values that are sorted.
 *
 * @author David William Stroud
 * @author Samuel A. Rebelsky
 */

public class Quicksorter<T> implements Sorter<T> {
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
  public Quicksorter(Comparator<? super T> comparator) {
    this.order = comparator;
  } // Quicksorter(Comparator)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Partitions a sub-array into elements less than, equal to, and greater than an arbitrary pivot.
   * @param values The array to sort.
   * @param start The first index of the sub-array, inclusive.
   * @param end The last index of the sub-array, exclusive.
   * @return
   *   An array with two elements:
   *   the start of the "equal to" section,
   *   and the start of the "greater than" section.
   */
  private int[] partition(T[] values, int start, int end) {
    T pivot = values[start / 2 + end / 2];
    int startEqual = start;
    int startGt = end;
    for (int index = start; index < startGt;) {
      int comparison = this.order.compare(values[index], pivot);
      if (comparison < 0) {
        T inEqual = values[startEqual];
        values[startEqual++] = values[index];
        values[index++] = inEqual;
      } else if (comparison > 0) {
        T inUnprocessed = values[--startGt];
        values[startGt] = values[index];
        values[index] = inUnprocessed;
      } else {
        // The pivot is equal to the value.
        index++;
      } // if-else
    } // for

    return new int[] {startEqual, startGt};
  } // partition(T[], int, int)

  /**
   * Sorts a slice of an array in place.
   * @param values The full array to sort.
   * @param start The first index to sort, inclusive.
   * @param end The last index to sort, exclusive.
   */
  private void sortSlice(T[] values, int start, int end) {
    if ((end - start) < 2) {
      // This slice is already sorted, since it either has
      // one or zero elements.
      return;
    } // if

    int[] indices = this.partition(values, start, end);

    this.sortSlice(values, start, indices[0]);
    this.sortSlice(values, indices[1], end);
  } // sortSlice(T[], int, int)

  /**
   * Sort an array in place using Quicksort.
   *
   * @param values
   *   an array to sort.
   *
   * @post
   *   The array has been sorted according to some order (often
   *   one given to the constructor).
   * @post
   *   For all i, 0 &lt; i &lt; values.length,
   *     order.compare(values[i-1], values[i]) &lt;= 0
   */
  @Override
  public void sort(T[] values) {
    this.sortSlice(values, 0, values.length);
  } // sort(T[])
} // class Quicksorter
