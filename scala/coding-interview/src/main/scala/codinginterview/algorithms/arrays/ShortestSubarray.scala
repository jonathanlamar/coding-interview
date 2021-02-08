package codinginterview.algorithms.arrays

/** Given an array, arr, of positive numbers, find the length of the shortest
  * contiguous subarray whose sum is greater than or equal to n.  If no such
  * subarray exists, return 0.
  * For example, if arr = [1, 2, 3, 4, 1, 3] and S = 5, then f(arr, n) = 2.
  *
  * The easy solution is to loop over beginning and endpoints independently and
  * test for the conditions.  Meanwhile we update a tracker variable only when
  * we find a better solution.
  */
object ShortestSubarray {

  /** My brute force solution.  This is pretty bad, and has time complexity
    * O(n^2), where n is the length of arr.
    */
  def bruteForce(arr: List[Int], sum: Int): Int = {
    var hasSolution = false
    var bestLen = arr.length

    for (
      leftEndpoint <- 0 until arr.length;
      rightEndpoint <- leftEndpoint until arr.length
    ) {
      val tmpSum = arr.slice(leftEndpoint, rightEndpoint + 1).sum

      if (tmpSum > sum) {
        hasSolution = true
        if (rightEndpoint - leftEndpoint + 1 < bestLen) {
          bestLen = rightEndpoint - leftEndpoint + 1
        }
      }
    }

    if (hasSolution) return bestLen
    else return 0
  }

  /** The solution can be improved by noting that in the brute force solution, we
    * start with short subarrays and increase length.  Also, if we are looking at a
    * subarray whose sum is already greater than or equal to sum, then there is no
    * need to increase the right endpoint.  This hints at a better solution: that
    * we can iterate the right endpoint until we find a subarray whose sum is >= S,
    * then iterate the left endpoint from 0 until we fail to satisfy the sum
    * property.  Keeping trak of the minimum length achieved, we then proceed in
    * alternating steps, increasing each enpoint.  This will involve at worst 2*n
    * steps, hence has runtim O(n).
    */
  def optimalSolution(arr: List[Int], sum: Int): Int = {
    var hasSolution = false
    var bestLen = arr.length

    arr match {
      case Nil => return 0
      case head :: Nil => {
        if (head >= sum) return 1
        else return 0
      }
      case head :: next => {
        // This is a one-pass search, and we will keep a moving sum to cut down on
        // runtime.  Initially, leftEndpoint = 0 and rightEndpoint = 0
        var movingSum = 0
        var leftEndpoint = 0
        for (rightEndpoint <- 0 until arr.length) {
          movingSum += arr(rightEndpoint)
          if (movingSum >= sum) {
            hasSolution = true // Trigger that a solution exists
            var conditionMet = true // once condition is met, update length
            while (conditionMet) {
              var tmpLen = rightEndpoint - leftEndpoint + 1

              if (tmpLen < bestLen) {
                bestLen = tmpLen
              }

              // Update length moving sum and conditionMet
              movingSum -= arr(leftEndpoint)
              leftEndpoint += 1
              if (movingSum < sum) conditionMet = false
            }
          }
        }

        if (hasSolution) return bestLen
        else return 0
      }
    }
  }
}
