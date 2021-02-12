package codinginterview.datastructures

object BinaryTree {
  sealed trait TreeNode {

    /** Inserts a node into a binary tree "in-order," i.e., goes left if less than
      * head, goes right if greater than head until it gets to a leaf node.  If
      * the algorithm encounters a head equal to num, then it will default right.
      */
    def insert(num: Int): TreeNode

    def print(): String
  }

  sealed trait NonemptyNode extends TreeNode {
    val root: Int
  }

  final object EmptyNode extends TreeNode {
    def insert(num: Int): BinaryTreeNode = BinaryTreeNode(num)

    def print(): String = ""
  }

  final case class BinaryTreeNode(
      root: Int,
      left: TreeNode = EmptyNode,
      right: TreeNode = EmptyNode
  ) extends NonemptyNode {
    def insert(num: Int): BinaryTreeNode = {
      if (num < root) BinaryTreeNode(root, left.insert(num), right)
      else BinaryTreeNode(root, left, right.insert(num))
    }

    def print(): String =
      s"(${left.print()} / ${root.toString()} \\ ${right.print()})"
  }

  /** Builds a binary tree from a list of numbers.  The elements will be sorted
    * in-order, i.e., printing with an in-order traversal will print a sorted
    * list of numbers.
    */
  def buildBinaryTreeFromList(nums: List[Int]): TreeNode = {
    return nums.foldLeft[TreeNode](EmptyNode)(_.insert(_))
  }
}
