package codinginterview.datastructures

object LinkedList {
  sealed trait LinkedListNode {
    def insertAtHead(value: Int): NonemptyNode

    def insertAtTail(value: Int): NonemptyNode

    def print(): String
  }

  sealed trait NonemptyNode extends LinkedListNode {
    val value: Int
  }

  final object EmptyNode extends LinkedListNode {
    def insertAtHead(num: Int): SinglyLinkedNode = SinglyLinkedNode(num)

    def insertAtTail(num: Int): SinglyLinkedNode = SinglyLinkedNode(num)

    def print(): String = ""
  }

  final case class SinglyLinkedNode(
      value: Int,
      next: LinkedListNode = EmptyNode
  ) extends NonemptyNode {
    def insertAtHead(num: Int): SinglyLinkedNode = SinglyLinkedNode(num, this)

    def insertAtTail(num: Int): SinglyLinkedNode =
      SinglyLinkedNode(value, next.insertAtTail(num))

    def print(): String = s"${value}, ${next.print()}"
  }

  /** Builds a singly linked list by inserting nodes at head from the reversed list. */
  def buildSinglyLinkedListFromList(nums: List[Int]): LinkedListNode = {
    nums.reverse.foldLeft[LinkedListNode](EmptyNode)(_.insertAtHead(_))
  }

  /** Reversed a linked list. */
  def reverseBruteForce(linkedList: LinkedListNode): LinkedListNode = {
    linkedList match {
      case EmptyNode => linkedList
      case SinglyLinkedNode(value, next) =>
        reverseBruteForce(next).insertAtTail(value)
    }
  }

  def reverseInPlace(linkedList: LinkedListNode): LinkedListNode = ???

  /** Builds a doubly linked list by inserting nodes at head from the reversed list. */
  // def buildDoublyLinkedListFromList(nums: List[Int]): DoublyLinkedNode = ???
}
