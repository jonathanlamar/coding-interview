package codinginterview.algorithms.arrays

import scala.collection.mutable.HashMap
import scala.collection.mutable.Stack
import scala.math.min

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
}
