package codinginterview.algorithms.arrays

import scala.collection.mutable.HashMap
import scala.math.max

object LongestSubstring {

  /** A very similar problem common to ask is: What is the length of the longest
    * substring of a string with no repeated characters.  I model the solution
    * after the above optimal solution.  The analogy is that strings are simply
    * arrays, and we are merely finding the longest contiguous subarray subject
    * to a condition.
    */
  def longestNonrepeatingSubstring(str: String): String = {
    // Starting from 0 now because we're looking to maximize.
    var bestLen = 0
    var bestLeftEnpoint = 0
    var bestRightEndpoint = 0

    if (str.length() <= 1) return str

    var charCounts: HashMap[Char, Int] = new HashMap()
    var conditionMet = false

    // Using a similar one-pass search to shortest subarray.  Most of the
    // difference is due to that we're looking for a longest subarray this
    // time as opposed to shortest.
    var leftEndpoint = 0
    for (rightEndpoint <- 0 until str.length()) {
      // Update hashmap
      if (
        !charCounts
          .contains(str(rightEndpoint)) || charCounts(str(rightEndpoint)) == 0
      ) {
        charCounts(str(rightEndpoint)) = 1
        if (rightEndpoint - leftEndpoint + 1 > bestLen) {
          bestLen = rightEndpoint - leftEndpoint + 1
          bestLeftEnpoint = leftEndpoint
          bestRightEndpoint = rightEndpoint
        } else {
          charCounts(str(rightEndpoint)) += 1
          conditionMet = false

          while (!conditionMet) {
            // TODO: This could be shortcut if I kept track of indices of
            // characters.
            charCounts(str(leftEndpoint)) -= 1
            leftEndpoint += 1

            // This may seem bad, but only has O(num characters) runtime...
            if (charCounts.values.map(_ <= 1).foldLeft(true)(_ && _)) {
              conditionMet = true

              if (rightEndpoint - leftEndpoint + 1 > bestLen) {
                bestLen = rightEndpoint - leftEndpoint + 1
                bestLeftEnpoint = leftEndpoint
                bestRightEndpoint = rightEndpoint
              }
            }
          }
        }
      }
    }

    return str.substring(bestLeftEnpoint, bestRightEndpoint + 1)
  }

  def longestNonrepeatingSubstring2(str: String): String = {

    // Maybe we don't need it all
    var bestLen = 0
    var ind1 = 0
    var ind2 = 0

    // How many times character has been viewed
    // int should always be 0 or 1
    var visitedHash: HashMap[Char, Int] = new HashMap()

    var conditionMet = true
    var ptr1 = 0
    // Grow
    for (ptr2 <- 0 until str.length) {
      val c = str(ptr2)

      if (visitedHash.keySet.contains(c)) visitedHash(c) += 1
      else visitedHash(c) = 1

      if (visitedHash(c) <= 1) {
        // Condition is met, and maybe we have a new max
        val maybeBestLen = ptr2 - ptr1 + 1
        if (maybeBestLen > bestLen) {
          bestLen = maybeBestLen
          ind1 = ptr1
          ind2 = ptr2
        }
      } else conditionMet = false

      // Shrink
      while (!conditionMet) {
        // Note, need to update hash before increasing
        visitedHash(str(ptr1)) -= 1
        ptr1 += 1

        // Check all are leq 1
        conditionMet = visitedHash.values.map(_ <= 1).foldLeft(true)(_ && _)
      }
    }

    return str.substring(ind1, ind2 + 1)
  }

  // https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/
  // (Problem hidden because I don't pay for the premium leetcode anymore)
  //
  // Find the longest substring of a string with at most numRepeat repeating
  // characters
  def longestSubstringWithRepeat(str: String, numRepeat: Int): String = {
    var leftIndex = 0
    var rightIndex = 0
    var repeatHashMap: HashMap[Char, Int] = new HashMap()
    repeatHashMap.put(str(0), 1)
    var conditionIsTrue = true
    var bestLeftIndex = 0
    var bestRightIndex = 0

    while (conditionIsTrue && rightIndex < str.length() - 1) {
      // Update right endpoint
      rightIndex += 1
      repeatHashMap.get(str(rightIndex)) match {
        case None        => repeatHashMap.put(str(rightIndex), 1)
        case Some(value) => repeatHashMap(str(rightIndex)) += 1
      }

      // Update condition truth value
      conditionIsTrue = repeatHashMap(str(rightIndex)) <= numRepeat

      // Update best indexes if need be
      if (
        conditionIsTrue && rightIndex - leftIndex > bestRightIndex - bestLeftIndex
      ) {
        bestLeftIndex = leftIndex
        bestRightIndex = rightIndex
      }

      while (!conditionIsTrue) {
        // Update left endpoint
        repeatHashMap(str(leftIndex)) -= 1
        leftIndex += 1

        // update condition
        // This is linear in number of characters.  Since there are a fixed
        // number of characters, this should be considered O(1)
        conditionIsTrue = repeatHashMap.values.forall(_ <= numRepeat)
      }
    }

    return str.substring(bestLeftIndex, bestRightIndex + 1)
  }

  // https://leetcode.com/problems/combination-sum/
  // Given an array of distinct integers candidates and a target integer
  // target, return a list of all unique combinations of candidates where the
  // chosen numbers sum to target. You may return the combinations in any
  // order.
  //
  // The same number may be chosen from candidates an unlimited number
  // of times. Two combinations are unique if the frequency of at least one of
  // the chosen numbers is different.
  //
  // This stumped me.  I actually had a clever idea of using a flat map and
  // subtracting the number from the target, but somehow that didn't implement
  // backtracking correctly.  The better solution would be to hash the partial
  // lists and properly implement backtracking.
  def combinationSum(candidates: Array[Int], target: Int): List[List[Int]] = {
    def buildSum(
        candidates2: Array[Int],
        partialSolution: List[Int],
        target2: Int
    ): List[List[Int]] = {
      if (target2 == 0) return List(partialSolution)
      else if (target2 < 0) return Nil
      else
        return combinationSum(candidates2, target2).map(list =>
          partialSolution ::: list
        )
    }

    var solutions: List[List[Int]] = Nil
    for (i <- 0 until candidates.length) {
      val num = candidates(i)
      var partialSolution = List(num)
      solutions = solutions ::: buildSum(
        candidates.drop(i),
        partialSolution,
        target - num
      )
    }

    return solutions
  }
}
