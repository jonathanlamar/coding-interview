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
}
