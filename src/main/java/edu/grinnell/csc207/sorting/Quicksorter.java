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

    T pivot = values[start / 2 + end / 2];
    int startEqual = start;
    int startGt = end;
    for (int index = start; index < startGt; ) {
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
      }
    } // for

    this.sortSlice(values, start, startEqual);
    this.sortSlice(values, startGt, end);
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
   *   For all i, 0 &lt; i &lt; vals.length,
   *     order.compare(vals[i-1], vals[i]) &lt;= 0
   */
  @Override
  public void sort(T[] values) {
    this.sortSlice(values, 0, values.length);
  } // sort(T[])
} // class Quicksorter
