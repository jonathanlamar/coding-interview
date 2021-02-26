package codinginterview.algorithms.arrays

import scala.collection.mutable.HashMap
import scala.collection.mutable.Stack
import scala.math._

object LeetCodeSolutions {
  def validParens(parenString: String): Boolean = {

    // Just for convenience (could use if-else)
    var matchMap: HashMap[Char, Char] =
      HashMap.apply('(' -> ')', '[' -> ']', '{' -> '}')

    // Critical
    var openParenStack: Stack[Char] = new Stack()

    for (char <- parenString.toCharArray) {
      if (char == '(' || char == '[' || char == '{') {
        openParenStack.push(char)
      } else if (char == ')' || char == ']' || char == '}') {
        if (openParenStack.isEmpty) return false
        else {
          val openChar = openParenStack.pop()
          if (matchMap(openChar) != char) return false
        }
      }
    }

    return openParenStack.isEmpty
  }

  /** https://leetcode.com/problems/longest-palindromic-substring/
    * Given a string s, return the longest palindromic substring in s.
    */
  def longestPalindrome(str: String): String = {
    val n = str.length()

    // Considering the letters and spaces between them as an array
    // of length 2n-1, this finds the maximal palindrome centered at node i
    def expandAroundCenter(i: Int): (String, Int) = {
      var currentLen = i % 2 // 1 if contains letter, else 0
      var centerPoint = n.toDouble / 2
      var le = centerPoint.toInt
      var re = (centerPoint + 0.5).toInt
      var pastLe = 0
      var pastRe = 0

      while (str(le) == str(re) && le >= 0 && re < n) {
        currentLen += 2
        pastLe = le
        pastRe = re
        le -= 1
        re += 1
      }

      return (str.substring(pastLe, pastRe + 1), currentLen)
    }

    var bestLen = 0
    var bestPalindrome = ""

    for (i <- 0 until 2 * n - 1) {
      val (palindrome, newLen) = expandAroundCenter(i)
      if (newLen > bestLen) {
        bestLen = newLen
        bestPalindrome = palindrome
      }
    }

    return bestPalindrome
  }

  /** https://leetcode.com/problems/container-with-most-water/
    * Given n non-negative integers a1, a2, ..., an , where each represents a
    * point at coordinate (i, ai). n vertical lines are drawn such that the two
    * endpoints of the line i is at (i, ai) and (i, 0). Find two lines, which,
    * together with the x-axis forms a container, such that the container
    * contains the most water.  Notice that you may not slant the container.
    */
  def maxArea(arr: List[Int]): (Int, Int, Int) = {
    var bestArea = 0
    var bestLe = 0
    var bestRe = arr.length - 1
    var le = 0
    var re = arr.length - 1

    while (le < re) {

      val ly = arr(le)
      val ry = arr(re)
      if (ly <= ry) {
        val area = ly * (re - le)
        if (area > bestArea) {
          bestArea = area
          bestLe = le
          bestRe = re
        }

        le += 1
      } else {
        val area = ry * (re - le)
        if (area > bestArea) {
          bestArea = area
          bestLe = le
          bestRe = re
        }

        re -= 1
      }
    }

    return (bestArea, bestLe, bestRe)
  }

  /** https://leetcode.com/problems/3sum/
    * Given an array nums of n integers, are there elements a, b, c in nums
    * such that a + b + c = 0? Find all unique triplets in the array which
    * gives the sum of zero.  Notice that the solution set must not contain
    * duplicate triplets.
    */
  // TODO: This solution returns a bunch of dupes.  This needs to be tweaked.
  def threeSum_nested(nums: List[Int]): List[List[Int]] = {
    def twoSum(
        arr: List[Int],
        idxToSkip: Int,
        sumVal: Int
    ): List[List[Int]] = {
      var valMap: HashMap[Int, Int] = new HashMap()
      var solutionList: List[List[Int]] = List()

      // Linear time and space
      for ((x, i) <- arr.zipWithIndex if i != idxToSkip) {
        if (valMap.keySet.contains(x)) {
          solutionList =
            solutionList :+ List(-sumVal, x, nums(valMap(x))).sorted
        } else {
          valMap(sumVal - x) = i
        }
      }

      return solutionList
    }

    // Linear time
    nums.sorted.zipWithIndex
      .flatMap({ case (x: Int, i: Int) => twoSum(nums, i, -x) })
      // O(N*log(N)) step after quadratic, so no asymptotic loss.
      // BTW, this ruins the elegance of the solution.  It would have been clever, but this kinda
      // defeats that for me.
      .sortWith((l1, l2) => l1(0) < l2(0) || (l1(0) == l2(0) && l1(1) < l2(1)))
      .distinct
      .toList
  }

  // Long story short, it has to be N^2 time because there are two degrees of
  // freedom in the problem.  Thus, we can sort the array first and loop
  // cleverly to avoid dupes.
  def threeSum_sortFirst(nums: Array[Int]): List[List[Int]] = {
    val N = nums.length
    if (N < 3) return Nil

    var sols: List[List[Int]] = Nil
    val sortedNums = nums.sorted // O(Nlog(N))

    for (i <- 0 until N - 2) {

      // Crucial step here (1 of 3) to avoid dups - we skip over repeated values in the array with
      // this condition.
      if (i == 0 || sortedNums(i) > sortedNums(i - 1)) {
        // Candidate indexes for other values in solution
        var secondIdx = i + 1
        var thirdIdx = N - 1

        // O(N) loop
        while (secondIdx < thirdIdx) {
          // register solution found.
          if (
            sortedNums(i) + sortedNums(secondIdx) + sortedNums(thirdIdx) == 0
          ) {
            sols = sols :+ List(
              sortedNums(i),
              sortedNums(secondIdx),
              sortedNums(thirdIdx)
            )
          }

          if (
            sortedNums(i) + sortedNums(secondIdx) + sortedNums(thirdIdx) < 0
          ) {

            // Crucial step 2 to avoid dups - increment *until new value found*
            val oldStart = secondIdx
            while (
              sortedNums(oldStart) == sortedNums(
                secondIdx
              ) && secondIdx < thirdIdx
            ) secondIdx += 1
          } else {

            // Crucial step 3 - decrement *until new value found*
            val oldEnd = thirdIdx
            while (
              sortedNums(oldEnd) == sortedNums(thirdIdx) && secondIdx < thirdIdx
            ) thirdIdx -= 1
          }
        }
      }
    }

    return sols
  }

  // https://leetcode.com/problems/zigzag-conversion/
  // The string "PAYPALISHIRING" is written in a zigzag pattern on a given
  // number of rows like this: (you may want to display this pattern in a fixed
  // font for better legibility)
  // P   A   H   N
  // A P L S I I G
  // Y   I   R
  // And then read line by line: "PAHNAPLSIIGYIR"
  // Write the code that will take a string and make this conversion given a
  // number of rows:
  // string convert(string s, int numRows);
  def zigZagString(s: String, numRows: Int): String = {
    if (numRows == 1) return s

    var direction = 1 // 1 means down
    var currentRow = 0

    // Hashmap rowNum -> queue of chars
    // This should be optimized, since building strings is costly in both time and space
    var map: HashMap[Int, String] = HashMap()

    for (c <- s) {
      // Update map
      map.get(currentRow) match {
        case None      => map.put(currentRow, c.toString)
        case Some(str) => map.put(currentRow, str + c.toString)
      }

      // Update direction and level
      if (
        currentRow == 0 && direction == -1 || currentRow == numRows - 1 && direction == 1
      ) {
        // Turn around and advance
        direction = -1 * direction
        currentRow += direction
      } else {
        currentRow += direction
      }
    }

    println(map)

    // This is O(N) time
    var outputString = ""
    for (i <- 0 until numRows) {
      map.get(i) match {
        case Some(str) => outputString = outputString + str
        case None      =>
      }
    }

    return outputString
  }

  // https://leetcode.com/problems/palindrome-number/
  // Given an integer x, return true if x is palindrome integer.
  // An integer is a palindrome when it reads the same backward as forward. For
  // example, 121 is palindrome while 123 is not.
  //
  // Example 1:
  // Input: x = 121
  // Output: true
  //
  // Example 2:
  // Input: x = -121
  // Output: false
  // Explanation: From left to right, it reads -121. From right to left, it
  // becomes 121-. Therefore it is not a palindrome.
  //
  // Example 3:
  // Input: x = 10
  // Output: false
  // Explanation: Reads 01 from right to left. Therefore it is not a
  // palindrome.
  def isPalindrome(x: Int): Boolean = {

    // negative should always return false
    if (x < 0) return false
    else if (x > 0 && x % 10 == 0) return false

    var xRev1 = x
    var xRev2 = 0

    while (xRev1 > xRev2) {
      xRev2 = xRev2 * 10 + xRev1 % 10
      xRev1 = (xRev1 / 10).toInt
    }

    println(x, xRev1, xRev2)

    return xRev1 == xRev2 || xRev1 == (xRev2 / 10).toInt
  }

  // https://leetcode.com/problems/maximum-subarray/
  // Given an integer array nums, find the contiguous subarray (containing at
  // least one number) which has the largest sum and return its sum.
  //
  // I thought this was pretty hard.  I got boxed into a two pointer solution,
  // since I thought it was like the max area problem above.  Turns out, it's
  // actually an easy dynamic programming problem.  I guess the lesson there is
  // in recognition of the problem type.
  def maxSubArray(nums: Array[Int]): Int = {
    var maxSoFar = nums(0)
    var maxEndingHere = nums(0)

    for (i <- 1 until nums.length) {
      maxEndingHere = max(nums(i), maxEndingHere + nums(i))
      maxSoFar = max(maxSoFar, maxEndingHere)
    }

    return maxSoFar
  }

  // https://leetcode.com/problems/add-binary/
  // Given two binary strings a and b, return their sum as a binary string.
  //
  // Example 1:
  // Input: a = "11", b = "1"
  // Output: "100"
}
