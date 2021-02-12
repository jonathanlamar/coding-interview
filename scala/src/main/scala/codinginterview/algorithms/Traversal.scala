package codinginterview.algorithms

import codinginterview.datastructures.BinaryTree._
import scala.collection.mutable.Queue
import scala.collection.mutable.Stack

object Traversal {

  /** Depth first search of a binary search tree.
    * @return the list of nodes in the order traversed.
    */
  def depthFirstTraversal(tree: TreeNode): List[Int] = {
    var visitStack: Stack[Int] = new Stack()

    tree match {
      case b: BinaryTreeNode => {
        var adjStack: Stack[BinaryTreeNode] = new Stack()
        adjStack.push(b)

        while (!adjStack.isEmpty) {
          val subTree = adjStack.pop()
          visitStack.push(subTree.root)

          subTree.left match {
            case b: BinaryTreeNode => adjStack.push(b)
            case _                 =>
          }
          subTree.right match {
            case b: BinaryTreeNode => adjStack.push(b)
            case _                 =>
          }
        }
      }
      case _ =>
    }

    return visitStack.toList
  }

  /** Breadth first search of a binary search tree.
    * @return the list of nodes in the order traversed.
    */
  def breadthFirstTraversal(tree: TreeNode): List[Int] = {
    var visitStack: Stack[Int] = new Stack()

    tree match {
      case b: BinaryTreeNode => {
        var adjQueue: Queue[BinaryTreeNode] = new Queue()
        adjQueue.enqueue(b)

        while (!adjQueue.isEmpty) {
          val subTree = adjQueue.dequeue()
          visitStack.push(subTree.root)

          subTree.left match {
            case b: BinaryTreeNode => adjQueue.enqueue(b)
            case _                 =>
          }
          subTree.right match {
            case b: BinaryTreeNode => adjQueue.enqueue(b)
            case _                 =>
          }
        }
      }
      case _ =>
    }

    return visitStack.toList
  }

  /** in-order traversal of a binary search tree. */
  def inOrderTraversal(tree: TreeNode, visitStack: List[Int]): List[Int] = {
    var visits: List[Int] = visitStack
    tree match {
      case b: BinaryTreeNode => {
        b.left match {
          case b: BinaryTreeNode =>
            visits = inOrderTraversal(b.left, visits)
          case _ =>
        }

        visits :+= b.root

        b.right match {
          case b: BinaryTreeNode =>
            visits = inOrderTraversal(b.right, visits)
          case _ =>
        }
      }
      case _ =>
    }

    return visits
  }
}
