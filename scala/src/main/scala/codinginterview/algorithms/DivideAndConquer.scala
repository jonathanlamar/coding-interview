package codinginterview.algorithms

import codinginterview.datastructures.BinaryTree2.BinaryTreeNode

object DivideAndConquer {

  /** Sorts a list of integers using merge sort, an example of divide-and-conquor. */
  def mergeSort(nums: List[Int]): List[Int] = {
    def merge(sortedNums1: List[Int], sortedNums2: List[Int]): List[Int] = {
      sortedNums1 match {
        case Nil => sortedNums2
        case head1 :: tail1 =>
          sortedNums2 match {
            case Nil => sortedNums1
            case head2 :: tail2 => {
              if (head1 <= head2) {
                List(head1, head2) ::: merge(tail1, tail2)
              } else {
                List(head2, head1) ::: merge(tail1, tail2)
              }
            }
          }
      }
    }
    if (nums.length <= 1) return nums
    val m: Int = nums.length / 2
    return merge(mergeSort(nums.take(m)), mergeSort(nums.drop(m)))
  }

  def validateBinaryTree(tree: BinaryTreeNode): Boolean = {
    if (tree == null) return true
    else {
      if (tree.left != null && tree.left.value > tree.value) return false

      if (tree.right != null && tree.right.value < tree.value) return false

      return validateBinaryTree(tree.left) && validateBinaryTree(tree.right)
    }
  }

  // https://leetcode.com/problems/combinations/
  // Given two integers n and k, return all possible combinations of k numbers
  // out of 1 ... n.
  //
  // You may return the answer in any order.
  def combine(n: Int, k: Int): List[List[Int]] = {
    if (k > n) return Nil
    else if (k < 0) return Nil
    else if (k == 0) return List(Nil)

    val foo = combine(n - 1, k - 1).map(combo => 1 :: combo.map(x => x + 1))
    val bar = combine(n - 1, k).map(combo => combo.map(x => x + 1))

    return foo ::: bar
  }

  // https://leetcode.com/problems/subsets/
  // Given an integer array nums of unique elements, return all possible
  // subsets (the power set).
  //
  // The solution set must not contain duplicate subsets. Return the solution
  // in any order.
  def subsets_recursive(nums: Array[Int]): List[List[Int]] = {
    // This is maybe an example of divide and conquer, and is definitely not
    // stack safe.
    def subsetsWithPrefix(
        prefix: List[Int],
        nums: Array[Int]
    ): List[List[Int]] = {
      if (nums.isEmpty) List(prefix)
      else
        subsetsWithPrefix(
          nums(0) :: prefix,
          nums.drop(1)
        ) ::: subsetsWithPrefix(prefix, nums.drop(1))
    }

    subsetsWithPrefix(Nil, nums)
  }
}
