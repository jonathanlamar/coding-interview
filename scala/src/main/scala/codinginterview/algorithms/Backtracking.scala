package codinginterview.algorithms

object Backtracking {

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

  // https://leetcode.com/problems/permutations/
  // Given an array nums of distinct integers, return all the possible
  // permutations. You can return the answer in any order.
  def permute(nums: Array[Int]): List[List[Int]] = {
    def permuteWithPrefix(
        prefix: List[Int],
        nums: Array[Int]
    ): List[List[Int]] = {
      if (nums.length == 1) return List(prefix.:+(nums(0)))

      var solutions: List[List[Int]] = Nil

      for (i <- 0 until nums.length) {
        solutions = solutions ::: permuteWithPrefix(
          prefix.:+(nums(i)),
          nums.take(i) ++ nums.drop(i + 1)
        )
      }

      return solutions
    }

    return permuteWithPrefix(Nil, nums)
  }
}
