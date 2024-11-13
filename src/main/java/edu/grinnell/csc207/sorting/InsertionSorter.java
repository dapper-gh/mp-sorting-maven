package edu.grinnell.csc207.sorting;

import java.util.Comparator;

/**
 * Something that sorts using insertion sort.
 *
 * @param <T>
 *   The types of values that are sorted.
 *
 * @author David William Stroud
 * @author Samuel A. Rebelsky
 */

public class InsertionSorter<T> implements Sorter<T> {
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
  public InsertionSorter(Comparator<? super T> comparator) {
    this.order = comparator;
  } // InsertionSorter(Comparator)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Inserts a value into its proper place in the section before it.
   * @param values The array to modify.
   * @param toInsert The index of the value to insert.
   */
  private void insert(T[] values, int toInsert) {
    T value = values[toInsert];
    for (int index = toInsert - 1; index >= 0; index--) {
      if (this.order.compare(value, values[index]) >= 0) {
        break;
      } // if
      values[index + 1] = values[index];
      values[index] = value;
    } // for
  } // insert(T[], int)

  /**
   * Sort an array in place using insertion sort.
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
    // We can start at index 1, since the first iteration of insertion sort will do nothing.
    for (int unprocessedStart = 1; unprocessedStart < values.length; unprocessedStart++) {
      this.insert(values, unprocessedStart);
    } // for
  } // sort(T[])
} // class InsertionSorter
