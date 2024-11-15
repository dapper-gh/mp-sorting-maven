package edu.grinnell.csc207.sorting;

import java.util.Comparator;

/**
 * Something that sorts using selection sort.
 *
 * @param <T>
 *   The types of values that are sorted.
 *
 * @author David William Stroud
 * @author Samuel A. Rebelsky
 */

public class SelectionSorter<T> implements Sorter<T> {
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
  public SelectionSorter(Comparator<? super T> comparator) {
    this.order = comparator;
  } // SelectionSorter(Comparator)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Selects the smallest element in a subarray. Assumes that all elements before it are sorted.
   * @param values The array in which to search.
   * @param firstIndex The first index of the sub-array.
   * @return The index of the smallest element in the sub-array.
   */
  private int select(T[] values, int firstIndex) {
    int min = firstIndex - 1;
    for (int candidate = firstIndex; candidate < values.length; candidate++) {
      if (this.order.compare(values[min], values[candidate]) > 0) {
        min = candidate;
      } // if
    } // for
    return min;
  } // select(T[], int)

  /**
   * Sort an array in place using selection sort.
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
    // We only need to go to values.length - 1,
    // since if there is only one unsorted element at the end,
    // the array is sorted.
    for (int unprocessedStart = 0; unprocessedStart < values.length - 1; unprocessedStart++) {
      int min = this.select(values, unprocessedStart + 1);
      T oldValue = values[unprocessedStart];
      values[unprocessedStart] = values[min];
      values[min] = oldValue;
    } // for
  } // sort(T[])
} // class SelectionSorter
