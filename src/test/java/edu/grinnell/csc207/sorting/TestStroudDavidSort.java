package edu.grinnell.csc207.sorting;

import org.junit.jupiter.api.BeforeAll;

/**
 * Tests of our SelectionSorter.
 */
public class TestStroudDavidSort extends TestSorter {
  /**
   * Set up the sorters.
   */
  @BeforeAll
  static void setup() {
    stringSorter = new StroudDavidSort<>(String::compareTo);
    intSorter = new StroudDavidSort<>(Integer::compareTo);
  } // setup()
} // class TestSelectionSorter
