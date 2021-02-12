package codinginterview.algorithms.dynamicprogramming

import scala.collection.mutable.ArrayBuffer

object LeetCodeSolutions {

  // https://leetcode.com/problems/unique-paths
  // O(nxm) time
  // O(nxm) space
  def uniquePaths1_brute(numRows: Int, numCols: Int): Int = {

    // i,j index is number of paths to i j node
    var arr = ArrayBuffer.fill(numRows, numCols)(0)

    for (
      r <- 0 until numRows;
      c <- 0 until numCols
    ) {
      if (r == 0 && c == 0) {
        arr(r)(c) = 1
      } else if (r == 0 && c > 0) {
        // Top row: only depends on previous column
        arr(r)(c) = arr(r)(c - 1)
      } else if (c == 0) {
        // First column: only depends on previous row
        arr(r)(c) = arr(r - 1)(c)
      } else {
        // interior or last column:  Sum of left and up
        arr(r)(c) = arr(r - 1)(c) + arr(r)(c - 1)
      }
    }

    println(arr)

    return arr(numRows - 1)(numCols - 1)
  }

  // https://leetcode.com/problems/unique-paths
  // O(rxc) time
  // O(c) space
  def uniquePaths1_optimal(numRows: Int, numCols: Int): Int = {

    if (numRows == 0 || numCols == 0) return 0

    // j index is number of paths to i j node, at time i
    var arr = ArrayBuffer.fill(numCols)(1)

    for (
      r <- 1 until numRows;
      c <- 1 until numCols
    ) {
      arr(c) = arr(c) + arr(c - 1)
      println(arr)
    }

    return arr(numCols - 1)
  }
}
