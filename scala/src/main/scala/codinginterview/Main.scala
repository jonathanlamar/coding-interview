package codinginterview

import codinginterview.algorithms.Cetera

object Main extends App {
  println("Hello, World!")

  val nums = Array(1, 1, 0, 2, 3, 4, 2)
  val target = 5

  println(s"nums = ${nums}")
  println(s"target = ${target}")

  val solutions = Cetera.twoSum(nums)(target)

  solutions foreach println
}
