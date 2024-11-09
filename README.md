# mp-sorting-maven

An exploration of sorting in Java.

Authors:

* David William Stroud
* Samuel A. Rebelsky (starter code)

Acknowledgements: None

This code may be found at <https://github.com/dapper-gh/mp-sorting-maven>. The original code may be found at <https://github.com/Grinnell-CSC207/mp-sorting-maven>.

Description of custom sorting algorithm
---------------------------------------

The custom sorting algorithm in this repository is mostly based on Quicksort.

The changes to Quicksort are as follows:

* Optimizations
  * When the sub-array size is two, hard-code an algorihm that makes only one comparison.
  * When the sub-array size is below two, do nothing.

* Heuristics (only when the sub-array is not very small)
  * If the sub-array appears mostly sorted and is small, use insertion sort.
  * Compare multiple pivots to find one that is most likely to evenly divide the subarray.

This algorithm isn't very good, for reasons I don't yet understand.