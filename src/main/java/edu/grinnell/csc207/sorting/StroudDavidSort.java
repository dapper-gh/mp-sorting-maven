package edu.grinnell.csc207.sorting;

import java.util.Comparator;

/**
 * This class implements a sorting algorithm mostly based on quicksort,
 * with a few heuristics and optimizations intended to speed up computation.
 * For more information on the implementation, check the README.md in this project.
 *
 * @param <T> The type one wants to sort.
 *
 * @author David William Stroud
 */
public class StroudDavidSort<T> implements Sorter<T> {
  /**
   * The Comparator used to determine the proper order of two elements.
   */
  private final Comparator<? super T> order;

  /**
   * This integer represents the minimum length of an array required to apply any heuristics.
   */
  private static final int MIN_FOR_HEURISTICS = 15;

  /**
   * This integer represents the maximum length of an array before falling back on insertion sort
   * becomes impractical.
   */
  private static final int MAX_FOR_INSERTION_SORT = 20;

  /**
   * Creates a Sorter that uses StroudDavid sort and the given
   * comparator to compare elements.
   *
   * @param comparator The comparator to use in this Sorter.
   */
  public StroudDavidSort(Comparator<? super T> comparator) {
    this.order = comparator;
  } // StroudDavidSort(Comparator<? super T>)

  /**
   * Sorts a slice of an array in place using insertion sort.
   *
   * @param values The full array to sort.
   * @param start  The first index to sort, inclusive.
   * @param end    The last index to sort, exclusive.
   */
  private void insertionSortSlice(T[] values, int start, int end) {
    // We can start at index start + 1, since the first iteration of insertion sort will do nothing.
    for (int unprocessedStart = start + 1; unprocessedStart < end; unprocessedStart++) {
      T value = values[unprocessedStart];
      for (int index = unprocessedStart - 1; index >= 0; index--) {
        if (this.order.compare(value, values[index]) >= 0) {
          break;
        } // if
        values[index + 1] = values[index];
        values[index] = value;
      } // for
    } // for
  } // insertionSortSlice(T[], int, int)

  /**
   * Sorts a slice of an array in place using StroudDavid sort.
   *
   * @param values The full array to sort.
   * @param start  The first index to sort, inclusive.
   * @param end    The last index to sort, exclusive.
   */
  private void sortSlice(T[] values, int start, int end) {
    Comparator<? super T> thisOrder = this.order;

    int size = end - start;
    if (size == 2) {
      // Optimization: this sort can be done with a single call to .compare.
      if (thisOrder.compare(values[start], values[--end]) <= 0) {
        return;
      } else {
        T value = values[end];
        values[end] = values[start];
        values[start] = value;
        return;
      } // if
    } // if
    if (size < 2) {
      // Optimization: this slice is already sorted, since it
      // is either one element or two.
      return;
    } // if

    T lowPivot = values[start];
    // This sort will break if you are handling an array with more than 2^30 elements.
    T midPivot = values[(start + end) / 2];
    T highPivot = values[end - 1];

    T pivot;
    if (size < StroudDavidSort.MIN_FOR_HEURISTICS) {
      // Optimization: it's probably not worth comparing these
      // pivots to find the "best" one when the array is so small.
      pivot = midPivot;
    } else {
      // Heuristic: quicksort works best when the pivot is the median,
      // so we will use something that's close to the median.
      if (thisOrder.compare(lowPivot, midPivot) <= 0) {
        if (thisOrder.compare(midPivot, highPivot) <= 0) {
          // Heuristic: it is suspicious that these pivots are already sorted. Let's investigate.
          // This will break if handling an array with more than 2^29 elements.
          T candidate = values[(3 * start + end) / 4];

          if (size < StroudDavidSort.MAX_FOR_INSERTION_SORT
                  && (thisOrder.compare(lowPivot, candidate) <= 0)
                  && (thisOrder.compare(candidate, midPivot) <= 0)) {
            // Heuristic: the array is very probably sorted. Fall back to insertion sort, but only
            // if the array is small enough.
            this.insertionSortSlice(values, start, end);
            return;
          } // if
        } // if
        pivot = midPivot;
      } else {
        pivot = lowPivot;
      } // if-else
    } // if-else

    int startEqual = start;
    int startGt = end;
    for (int index = start; index < startGt;) {
      int comparison = thisOrder.compare(values[index], pivot);
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

    this.sortSlice(values, start, startEqual);
    this.sortSlice(values, startGt, end);
  } // sortSlice(T[], int, int)

  /**
   * Sort an array in place using StroudDavid sort.
   *
   * @param values an array to sort.
   * @post The array has been sorted according to some order (often
   * one given to the constructor).
   * @post For all i, 0 &lt; i &lt; values.length,
   * order.compare(values[i-1], values[i]) &lt;= 0
   */
  @Override
  public void sort(T[] values) {
    this.sortSlice(values, 0, values.length);
  } // sort(T[])
} // class StroudDavidSort<T>
