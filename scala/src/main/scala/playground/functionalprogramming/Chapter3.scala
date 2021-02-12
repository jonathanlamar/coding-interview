package playground.functionalProgramming

import com.sun.org.apache.xpath.internal.compiler.FuncLoader

object Chapter3 {

  /** FunList
    *
    * Implemeting list as a functional data structure.  Using any methods from
    * native List in scala is cheating.
    */

  /** Defining a List trait.  TODO: Why do case classes not suffice?  The +
    * reflects that this is a covariant functor A -> List[A]
    */
  sealed trait FunList[+A]

  /** We're using objects because they are only implemented once.  This
    * reflects the immutability of functional data structures.  List
    * intersections are received through data sharing.  I believe the point of
    * extends in this line is to tell scala that FunNil is an instance of
    * FunList[Nothing], which is a subtype of FunList[A] for all A, hence
    * FunNil can be used in any context.
    */
  case object FunNil extends FunList[Nothing]

  /** Case class for pattern matching.  Cons holds the constructor of a new
    * list formed by appending an element to the head of an existing list.
    */
  case class FunCons[+A](head: A, tail: FunList[A]) extends FunList[A]

  /** This is a companion object.  As far as I can tell, it has no intrinsic
    * relationship to the trait FunList, but is merely named that way for the
    * convenience of the programmer.  Think of methods of List (e.g. append) in
    * native scala.
    */
  object FunList {
    def sum(ints: FunList[Int]): Int = ints match {
      case FunNil         => 0
      case FunCons(x, xs) => x + sum(xs)
    }

    def product(ds: FunList[Double]): Double = ds match {
      case FunNil          => 1
      case FunCons(0.0, _) => 0
      case FunCons(x, xs)  => x * product(xs)
    }

    def apply[A](as: A*): FunList[A] = {
      if (as.isEmpty) FunNil
      // This feels like cheatting...
      else FunCons(as.head, apply(as.tail: _*))
    }
    // Implement tail.  Easy
    def tail[A](as: FunList[A]): FunList[A] = as match {
      case FunNil          => FunNil
      case FunCons(a, aas) => aas
    }

    // Implement setHead.  Also Easy
    def setHead[A](as: FunList[A], a: A): FunList[A] = as match {
      case FunNil           => FunNil
      case FunCons(aa, aas) => FunCons(a, aas)
    }

    // Generalize tail to drop first n as a tail recursion.
    def drop[A](as: FunList[A], n: Int): FunList[A] = {
      def loop(index: Int, aas: FunList[A]): FunList[A] = {
        if (index == 0) aas
        else
          aas match {
            case FunNil           => FunNil
            case FunCons(a, aaas) => loop(index - 1, aaas)
          }
      }

      loop(n, as)
    }

    /** Implement dropWhile, which removes elements from the List prefix as
      * long as they match a predicate.
      */
    def dropWhile[A](l: FunList[A], f: A => Boolean): FunList[A] = l match {
      case FunNil         => FunNil
      case FunCons(a, as) => if (f(a)) dropWhile(as, f) else l
    }

    // Book version which allows type inference of f (via currying?)
    // Example usage dropWhile(FunList(1,2))(_==1) == FunList(1)
    def dropWhile2[A](as: FunList[A])(f: A => Boolean): FunList[A] = as match {
      case FunCons(h, t) if f(h) => dropWhile2(t)(f)
      case _                     => as
    }

    /** Not everything works out so nicely. Implement a function, init, that
      * returns a List consisting of all but the last element of a List. So,
      * given List(1,2,3,4), init will return List(1,2,3). Why can’t this
      * function be implemented in constant time like tail?  Answer: I quite
      * like this question.  The reason this takes linear time is that each
      * Cons will have to be replaced with one that points to a new tail.  We
      * prove this by induction: The final Cons must point to Nil, and each
      * time a Cons is changed, the tail of the preceeding Cons must be changed
      * to point to the current altered Cons.
      */
    def init[A](l: FunList[A]): FunList[A] = l match {
      case FunNil             => FunNil
      case FunCons(a, FunNil) => FunNil
      case FunCons(a, as)     => FunCons(a, init(as))
    }

    // Redefining sum and product with foldRight
    def foldRight[A, B](as: FunList[A], z: B)(f: (A, B) => B): B = as match {
      case FunNil         => z
      case FunCons(x, xs) => f(x, foldRight(xs, z)(f))
    }

    def sum2(ns: FunList[Int]): Int = foldRight(ns, 0)((x, y) => x + y)
    def product2(ns: FunList[Double]): Double = foldRight(ns, 1.0)(_ * _)

    /** Question:  Can product, implemented using foldRight, immediately halt
      * the recursion and return 0.0 if it encounters a 0.0? Why or why not?
      * Consider how any short-circuiting might work if you call foldRight with
      * a large list.  Answer: This implementation definitely halts as soon as
      * it encounters 0, however product2 does not (foldRight must return
      * before the product takes place).
      */
    def product3(ns: FunList[Double]): Double = {
      def loop(ns: FunList[Double], acc: Double): Double = {
        ns match {
          case FunNil          => acc
          case FunCons(0, _)   => 0
          case FunCons(n, nns) => loop(nns, n * acc)
        }
      }

      loop(ns, acc = 1)
    }

    // Compute the length of a list using foldRight
    def length[A](as: FunList[A]): Int = {
      foldRight(as, 0)({ case (a, n) => n + 1 })
    }

    /** Our implementation of foldRight is not tail-recursive and will result
      * in a StackOverflowError for large lists (we say it’s not stack-safe).
      * Convince yourself that this is the case, and then write another general
      * list-recursion function, foldLeft, that is tail-recursive, using the
      * techniques we discussed in the previous chapter.
      *
      * Answer: This implementation of foldRight is not stack-safe because each
      * internal call to itself must return to the previous level to complete
      * computation with variables left on the stack.  In other words, it is
      * not tail-recursive.  This implementation of foldLeft is, however.
      */
    def foldLeft[A, B](as: FunList[A], z: B)(f: (B, A) => B): B = as match {
      case FunNil          => z
      case FunCons(a, aas) => foldLeft(aas, f(z, a))(f)
    }

    /** Write a function that returns the reverse of a list (given List(1,2,3)
      * it returns List(3,2,1)). See if you can write it using a fold.  First
      * attempt: This has linear complexity.  I can't think of a faster
      * solution.
      */
    def reverseList[A](as: FunList[A]): FunList[A] = {
      def insertAtEnd[A](a: A, as: FunList[A]): FunList[A] = as match {
        case FunNil             => FunCons(a, FunNil)
        case FunCons(b, FunNil) => FunCons(b, FunCons(a, FunNil))
        case FunCons(b, bs)     => FunCons(b, insertAtEnd(a, bs))
      }

      foldRight(as: FunList[A], FunNil: FunList[A])(insertAtEnd)
    }

    /** Implement append in terms of either foldLeft or foldRight.  Answer: I
      * accidentally implemented a long version of this function in the
      * previous solution.  Here is a short one.
      */
    def append[A](a: A, as: FunList[A]): FunList[A] = {
      foldRight(as: FunList[A], FunCons(a, FunNil): FunList[A])(FunCons(_, _))
    }

    /** This one is hard: Implement foldRight in terms of foldLeft. */
  }
}
