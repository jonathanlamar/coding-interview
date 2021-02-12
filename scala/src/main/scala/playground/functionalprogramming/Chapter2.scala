package playground.functionalProgramming

object Chapter2 {

  /** Fibonacci sequence as a tail recursion.  I usually think of doing tail
    * recursions with an accumulator.  This is different though.  The sequence
    * is coded in two intermediate values: We traverse the sewuence from 0 to
    * n, examining the current position as well as peeking ahead to the next.
    * The recursive step sets current to next and next+current to next.
    */
  def fibonacci(n: Int): Int = {
    def loop(howManyMore: Int, currentFibNum: Int, nextFibNum: Int): Int = {
      if (howManyMore <= 0) currentFibNum
      else loop(howManyMore - 1, nextFibNum, currentFibNum + nextFibNum)
    }

    loop(n, currentFibNum = 0, nextFibNum = 1)
  }

  /** TODO: It might be neat to try generalizing the fibonacci function to take
    * a more arbitrary linear recursion (represented maybe as a list of
    * numbers) and calculate values as a tail recursion.
    */

  // Currying:
  def curry[A, B, C](f: (A, B) => C): A => (B => C) = {
    case a => { b => f(a, b) }
  }

  // Uncurrying:
  def uncurry[A, B, C](f: A => B => C): (A, B) => C = { case (a, b) =>
    f(a)(b)
  }

  // Function composition:
  def compose[A, B, C](f: B => C, g: A => B): A => C = { case a =>
    f(g(a))
  }

  /** At a job interview, I was asked to write a function for determining
    * whether a list is sorted.  This was my first guess: According to
    * intellij, this is also a tail recursion, but I am confused. I didn't want
    * to write it because I thought the final line ruled out the possibility of
    * tail recursion (as opposed to simple linear recursion).
    */
  def isSorted(L: List[Int]): Boolean = {
    if (L.length <= 1) true
    else L.head <= L.tail.head && isSorted(L.tail)
  }

  /** This is for sure a tail recursion, but it's also too complicated. */
  def isSorted2(L: List[Int]): Boolean = {
    def loop(M: List[Int], acc: Boolean): Boolean = {
      M match {
        case Nil      => acc
        case h :: Nil => acc
        case _        => loop(M.tail, M.head <= M.tail.head)
      }
    }
    if (L.length <= 1) true
    else loop(L.tail, L.head <= L.tail.head)
  }

}
