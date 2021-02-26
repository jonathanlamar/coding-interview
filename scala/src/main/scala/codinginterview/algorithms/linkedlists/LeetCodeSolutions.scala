package codinginterview.algorithms.linkedlists

import codinginterview.datastructures.LinkedList._
import codinginterview.datastructures.LinkedList2._

object LeetCodeSolutions {

  /** https://leetcode.com/problems/add-two-numbers/
    * You are given two non-empty linked lists representing two non-negative
    * integers. The digits are stored in reverse order, and each of their nodes
    * contains a single digit. Add the two numbers and return the sum as a
    * linked list.
    *
    * You may assume the two numbers do not contain any leading zero, except
    * the number 0 itself.
    */
  def addTwoNumbers_WRONG(
      l1: LinkedListNode,
      l2: LinkedListNode
  ): LinkedListNode = {

    // Stack safe version of the tail recursion.
    def innerLoop(
        l1: LinkedListNode,
        l2: LinkedListNode,
        accumulator: LinkedListNode,
        carry: Int
    ): LinkedListNode = (l1, l2) match {
      case (EmptyNode, EmptyNode) =>
        if (carry > 0) accumulator.insertAtTail(carry) else accumulator
      case (EmptyNode, b: SinglyLinkedNode) =>
        innerLoop(SinglyLinkedNode(carry), b, accumulator, 0)
      case (a: SinglyLinkedNode, EmptyNode) =>
        innerLoop(a, SinglyLinkedNode(carry), accumulator, 0)
      case (a: SinglyLinkedNode, b: SinglyLinkedNode) => {
        var partSum = carry + a.value + b.value
        innerLoop(
          a.next,
          b.next,
          accumulator.insertAtTail(partSum % 10),
          partSum / 10
        )
      }
    }

    return innerLoop(l1, l2, EmptyNode, 0)
  }

  def addTwoNumbers(l1: LinkedListNode, l2: LinkedListNode): LinkedListNode =
    (l1, l2) match {
      case (EmptyNode, EmptyNode) => EmptyNode
      case (EmptyNode, _)         => l2
      case (_, EmptyNode)         => l1

      // Generic case
      case (SinglyLinkedNode(val1, next1), SinglyLinkedNode(val2, next2)) => {
        if (val1 + val2 < 10)
          SinglyLinkedNode(val1 + val2, addTwoNumbers(next1, next2))
        else
          SinglyLinkedNode(
            val1 + val2 - 10,
            addTwoNumbers(addTwoNumbers(SinglyLinkedNode(1), next1), next2)
          )
      }
    }
}
