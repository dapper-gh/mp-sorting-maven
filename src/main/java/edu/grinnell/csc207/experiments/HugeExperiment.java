package edu.grinnell.csc207.experiments;

import edu.grinnell.csc207.sorting.Quicksorter;
import edu.grinnell.csc207.sorting.Sorter;
import edu.grinnell.csc207.sorting.StroudDavidSort;
import edu.grinnell.csc207.util.SimpleTimer;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

/**
 * An experiment to benchmark the performance of StroudDavid sort.
 *
 * @author David William Stroud
 */
public class HugeExperiment {
  /**
   * This is the seed used for the random number generator
   * that fills the array.
   */
  private static final long SEED = 1234567;
  /**
   * This is the length of the array.
   */
  private static final int HUGE_SIZE = 2_000_000;

  /**
   * This is the first sorter that needs to be benchmarked.
   */
  private static final Sorter<Integer> SORTER1 = new StroudDavidSort<>(Integer::compareTo);
  /**
   * This is the second sorter that needs to be benchmarked.
   */
  private static final Sorter<Integer> SORTER2 = new Quicksorter<>(Integer::compareTo);

  /**
   * Times a Sorter on an array.
   * @param sorter The sorter to time.
   * @param original The array that the sorter should sort.
   * @return The number of milliseconds it took for the sorter to sort the array.
   */
  private static long time(Sorter<Integer> sorter, Integer[] original) {
    Integer[] copy = Arrays.copyOf(original, original.length);

    SimpleTimer timer = new SimpleTimer();
    sorter.sort(copy);
    return timer.stop();
  } // time(Sorter<Integer>, Integer[])

  /**
   * Times SORTER1 and SORTER2 on an array.
   * @param pen The PrintWriter to use when outputting information.
   * @param huge The array that the sorter should sort.
   */
  private static void timeSorters(PrintWriter pen, Integer[] huge) {
    // We warm up the CPU here.
    HugeExperiment.time(HugeExperiment.SORTER1, huge);
    HugeExperiment.time(HugeExperiment.SORTER1, huge);

    pen.printf("First sorter time: %dms\n", HugeExperiment.time(HugeExperiment.SORTER1, huge));

    // And here.
    HugeExperiment.time(HugeExperiment.SORTER2, huge);
    HugeExperiment.time(HugeExperiment.SORTER2, huge);
    pen.printf("Second sorter time: %dms\n\n\n", HugeExperiment.time(HugeExperiment.SORTER2, huge));
  } // timeSorter(Integer[])

  /**
   * Times the sorting algorithms on a random array.
   * @param pen The PrintWriter to use when printing output.
   */
  public static void experimentRandom(PrintWriter pen) {
    pen.print("-- Experiment 1: Random Array --\n\n");

    Integer[] huge = new Integer[HugeExperiment.HUGE_SIZE];
    Random rng = new Random(HugeExperiment.SEED);
    for (int i = 0; i < huge.length; i++) {
      huge[i] = rng.nextInt();
    } // for

    HugeExperiment.timeSorters(pen, huge);
  } // experimentRandom(PrintWriter)

  /**
   * Times the sorting algorithms on a fully sorted array.
   * @param pen The PrintWriter to use when printing output.
   */
  public static void experimentSorted(PrintWriter pen) {
    pen.print("-- Experiment 2: Sorted Array --\n\n");

    Integer[] huge = new Integer[HugeExperiment.HUGE_SIZE];
    for (int i = 0; i < huge.length; i++) {
      huge[i] = (i / 2) - (huge.length / 4);
    } // for

    HugeExperiment.timeSorters(pen, huge);
  } // experimentSorted(PrintWriter)

  /**
   * Times the sorting algorithms on a nearly fully sorted array.
   * @param pen The PrintWriter to use when printing output.
   */
  public static void experimentNearlySorted(PrintWriter pen) {
    pen.print("-- Experiment 3: Nearly Sorted Array --\n\n");

    Integer[] huge = new Integer[HugeExperiment.HUGE_SIZE];
    for (int i = 0; i < huge.length; i++) {
      huge[i] = (i / 2) - (huge.length / 4);
    } // for

    Random rng = new Random(HugeExperiment.SEED);
    for (int i = 0; i < huge.length / 1_000; i++) {
      huge[rng.nextInt(0, huge.length)] = rng.nextInt();
    } // for

    HugeExperiment.timeSorters(pen, huge);
  } // experimentNearlySorted(PrintWriter)

  /**
   * Executes the experiments in this bundle.
   * @param args Command-line arguments, which are ignored.
   */
  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out, true);

    HugeExperiment.experimentRandom(pen);
    HugeExperiment.experimentSorted(pen);
    HugeExperiment.experimentNearlySorted(pen);

    pen.close();
  } // main(String[])
} // class HugeExperiment
