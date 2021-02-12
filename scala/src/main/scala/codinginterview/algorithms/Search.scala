package codinginterview.algorithms

object Search {

  /** Binary search of a list of integers for a target */
  def iterativeBinarySearch(sortedList: List[Int])(target: Int): Option[Int] = {
    if (sortedList.isEmpty) return None

    var leftIndex = 0
    var rightIndex = sortedList.length - 1
    var isStuck = false // scala loops do not support break

    while (leftIndex < rightIndex && !isStuck) {
      var midpoint = (rightIndex + leftIndex) / 2
      var valueAtMidpoint = sortedList(midpoint)

      if (valueAtMidpoint < target) {
        if (leftIndex == midpoint) {
          println("Caught in a loop.  Shortcutting.")
          isStuck = true
        } else leftIndex = midpoint
      } else if (valueAtMidpoint > target) {
        if (rightIndex == midpoint) {
          println("Caught in a loop.  Shortcutting.")
          isStuck = true
        } else rightIndex = midpoint
      } else return Some(midpoint)
    }

    println("Checking endpoints.")
    if (sortedList(rightIndex) == target) return Some(rightIndex)
    else if (sortedList(leftIndex) == target) return Some(leftIndex)
    else return None
  }

  /** Binary search of a list of integers for a target. */
  def recursiveBinarySearch(sortedList: List[Int])(target: Int): Option[Int] = {
    if (sortedList.isEmpty) return None

    var midpoint: Int = (sortedList.length - 1) / 2

    if (sortedList(midpoint) == target) return Some(midpoint)
    else if (sortedList(midpoint) < target)
      return recursiveBinarySearch(sortedList.drop(midpoint + 1))(
        target
      ) match {
        case None    => None
        case Some(x) => Some(midpoint + 1 + x)
      }
    else return recursiveBinarySearch(sortedList.take(midpoint))(target)
  }

  /** Sorts a list of integers using merge sort, an example of divide-and-conquor. */
  def mergeSort(nums: List[Int]): List[Int] = {
    val N = nums.length
    if (N <= 1) return nums

    val m: Int = N / 2

    val sortedFirstHalf = mergeSort(nums.take(m))
    val sortedSecondHalf = mergeSort(nums.drop(m))

    return merge(sortedFirstHalf, sortedSecondHalf)
  }

  private def merge(firstHalf: List[Int], secondHalf: List[Int]): List[Int] =
    ???
}
