package edu.grinnell.csc207.sorting;

import java.util.Arrays;
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
   * Merges two sorted sub-arrays into one.
   * @param values The input array to use.
   * @param output The output array to use.
   *               The sorted values will be placed at the indices [start, end).
   * @param start The first index of the first sub-array, inclusive.
   * @param middle The end of the first sub-array, exclusive, and the
   *               start of the second, inclusive.
   * @param end The end of the second sub-array, exclusive.
   */
  private void merge(T[] values, T[] output, int start, int middle, int end) {
    int atFirst = start;
    int atSecond = middle;
    for (int index = start; index < end; index++) {
      if (atFirst == middle) {
        output[index] = values[atSecond++];
      } else if (atSecond == end) {
        output[index] = values[atFirst++];
      } else {
        if (this.order.compare(values[atFirst], values[atSecond]) <= 0) {
          output[index] = values[atFirst++];
        } else {
          output[index] = values[atSecond++];
        } // if-else
      } // if-else
    } // for
  } // merge(T[], T[])

  /**
   * Sorts a slice of an array out of place.
   * @param values The full array to sort.
   * @param start The first index to sort, inclusive.
   * @param end The last index to sort, exclusive.
   * @param output The array to be used for output.
   */
  private void sortSlice(T[] values, int start, int end, T[] output) {
    if ((end - start) < 2) {
      // The slice has fewer than two elements, so it is sorted.
      System.arraycopy(
              values,
              start,
              output,
              start,
              end - start
      );
      return;
    } // if

    int middle = start / 2 + end / 2;
    if ((start % 2) == 1 && (end % 2) == 1) {
      // We need to make an adjustment if they are both odd.
      middle++;
    } // if
    this.sortSlice(output, start, middle, values);
    this.sortSlice(output, middle, end, values);

    this.merge(values, output, start, middle, end);
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
   *   For all i, 0 &lt; i &lt; values.length,
   *     order.compare(values[i-1], values[i]) &lt;= 0
   */
  @Override
  public void sort(T[] values) {
    T[] valuesCopy = Arrays.copyOf(values, values.length);
    this.sortSlice(valuesCopy, 0, values.length, values);
  } // sort(T[])
} // class MergeSorter
