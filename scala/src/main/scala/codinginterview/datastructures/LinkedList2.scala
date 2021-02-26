package codinginterview.datastructures

object LinkedList2 {
  case class ListNode(x: Int, next: ListNode = null) {
    def insertAtHead(y: Int) = ListNode(y, this)
  }

  def buildLinkedListFromList(nums: List[Int]): ListNode = {
    val reverseList = nums.reverse

    // Faster to reverse and insert at head (produces correct value)
    reverseList match {
      case Nil          => null
      case head :: Nil  => ListNode(head)
      case head :: tail => tail.foldLeft(ListNode(head))(_.insertAtHead(_))
    }
  }
}
