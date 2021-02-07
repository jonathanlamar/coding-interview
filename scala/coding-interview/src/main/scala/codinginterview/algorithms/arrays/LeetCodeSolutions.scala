package codinginterview.algorithms.arrays

import scala.collection.mutable.HashMap
import scala.collection.mutable.Stack

object LeetCodeSolutions {
  def validParens(parenString: String): Boolean = {

    // Just for convenience (could use if-else)
    var matchMap: HashMap[Char, Char] =
      HashMap.apply('(' -> ')', '[' -> ']', '{' -> '}')

    // Critical
    var openParenStack: Stack[Char] = new Stack()

    for (char <- parenString.toCharArray) {
      if (char == '(' || char == '[' || char == '{') {
        openParenStack.push(char)
      } else if (char == ')' || char == ']' || char == '}') {
        if (openParenStack.isEmpty) return false
        else {
          val openChar = openParenStack.pop()
          if (matchMap(openChar) != char) return false
        }
      }
    }

    return openParenStack.isEmpty
  }
}
