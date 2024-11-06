package edu.grinnell.csc207.util;

/**
 * Things that know how to sort arrays of values.
 *
 * @author Samuel A. Rebelsky
 *
 * @param <T>
 *   The type of value in the array.
 */
public interface Sorter<T> {
  /**
   * Sort an array in place.
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
  public void sort(T[] values);
} // interface Sorter<T>
