package codinginterview.algorithms

import scala.collection.mutable.HashMap

object Cetera {

  /** Given a array nums and a target value target, return the indices of two
    * elements of nums which sum to target.
    */
  def twoSum(nums: Array[Int])(target: Int): Array[(Int, Int)] = {
    // TODO: Super ugly syntax for appending to an array.

    // Map values -> index of complement
    var complementMap: HashMap[Int, Option[Int]] = new HashMap()
    complementMap.addAll(nums.map(x => x -> None))
    var solutionArray: Array[(Int, Int)] = Array()

    for (i <- 0 until nums.length) {
      val value = nums(i)
      complementMap(value) match {
        case None => {
          // println("Updating dict")
          // println(s"complementMap = ${complementMap}")
          // println(s"key = ${target - value}, val = ${i}")

          complementMap(target - value) = Some(i)
        }
        case Some(j) => {
          // println(
          //   s"nums(${i}) = ${value}, nums(${j}) = ${nums(j)}.  Adding (${i}, ${j})."
          // )

          solutionArray :+= (i, j)
        }
      }
    }

    return solutionArray
  }

  /** Reverse the elements of a array in place. */
  def reverseArray[A](nums: Array[A]): Array[A] = {
    var x: Int = 0
    var y: Int = nums.length - 1

    var reversedNums = nums.clone()
    while (x < y) {
      var z = reversedNums(x)
      reversedNums(x) = reversedNums(y)
      reversedNums(y) = z
      x += 1
      y -= 1
    }

    return reversedNums
  }
}
