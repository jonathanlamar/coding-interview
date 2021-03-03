package codinginterview.algorithms

import scala.collection.mutable.HashMap
import scala.math._

object Cetera {

  /** Given a array nums and a target value target, return the indices of two
    * elements of nums which sum to target.
    */
  def twoSum(nums: Array[Int])(target: Int): Array[(Int, Int)] = {
    // TODO: Super ugly syntax for appending to an array.

    // Map values -> index of complement
    var complementMap: HashMap[Int, Option[Int]] = new HashMap()
    complementMap.addAll(nums.map(x => x -> None))
    var solutionArray: Array[(Int, Int)] = Array()

    for (i <- 0 until nums.length) {
      val value = nums(i)
      complementMap(value) match {
        case None => {
          // println("Updating dict")
          // println(s"complementMap = ${complementMap}")
          // println(s"key = ${target - value}, val = ${i}")

          complementMap(target - value) = Some(i)
        }
        case Some(j) => {
          // println(
          //   s"nums(${i}) = ${value}, nums(${j}) = ${nums(j)}.  Adding (${i}, ${j})."
          // )

          solutionArray :+= (i, j)
        }
      }
    }

    return solutionArray
  }

  /** Reverse the elements of a array in place. */
  def reverseArray[A](nums: Array[A]): Array[A] = {
    var x: Int = 0
    var y: Int = nums.length - 1

    var reversedNums = nums.clone()
    while (x < y) {
      var z = reversedNums(x)
      reversedNums(x) = reversedNums(y)
      reversedNums(y) = z
      x += 1
      y -= 1
    }

    return reversedNums
  }

  // https://leetcode.com/problems/add-binary/
  // Given two binary strings a and b, return their sum as a binary string.
  //
  // Example 1:
  // Input: a = "11", b = "1"
  // Output: "100"
  // I hate this problem
  def sumBinaryString(a: String, b: String): String = {
    var N = max(a.length(), b.length())

    var newA = ("0" * (N - a.length()) + a).reverse
    var newB = ("0" * (N - b.length()) + b).reverse

    println(s"newA = ${newA}, newB = ${newB}")

    var carry: Boolean = false
    var runningSum = ""

    for (i <- 0 until N) {
      println(s"i = ${i}, carry = ${carry}, runningSum = ${runningSum}")

      if (newA(i) == '0' && newB(i) == '0' && !carry) {
        runningSum = "0" + runningSum
      } else if (newA(i) == '1' && newB(i) == '0' && !carry) {
        runningSum = "1" + runningSum
      } else if (newA(i) == '0' && newB(i) == '1' && !carry) {
        runningSum = "1" + runningSum
      } else if (newA(i) == '1' && newB(i) == '1' && !carry) {
        runningSum = "0" + runningSum
        carry = true
      } else if (newA(i) == '0' && newB(i) == '0' && carry) {
        runningSum = "1" + runningSum
        carry = false
      } else if (newA(i) == '1' && newB(i) == '0' && carry) {
        runningSum = "0" + runningSum
        carry = false
      } else if (newA(i) == '0' && newB(i) == '1' && carry) {
        runningSum = "0" + runningSum
        carry = false
      } else if (newA(i) == '1' && newB(i) == '1' && carry) {
        runningSum = "1" + runningSum
      }
    }

    if (carry) runningSum = "1" + runningSum

    return runningSum
  }

  // https://leetcode.com/problems/group-anagrams/
  // Given an array of strings strs, group the anagrams together. You can
  // return the answer in any order.
  //
  // An Anagram is a word or phrase formed by rearranging the letters of a
  // different word or phrase, typically using all the original letters exactly
  // once.
  //
  // Input: strs = ["eat","tea","tan","ate","nat","bat"]
  // Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
  def groupAnagrams_BAD(strs: Array[String]): List[List[String]] = {

    // The optimal solution would compute levenshtein distance, but I do not
    // remember how that is implemented.
    def isAnagram(word1: String, word2: String): Boolean = {
      if (word1.length() != word2.length()) return false
      if (word1 == "" && word2 == "") return true

      var charFreq: HashMap[Char, Int] = new HashMap()

      for (i <- 0 until word1.length()) {
        if (charFreq.get(word1(i)) == None) {
          charFreq.put(word1(i), 1)
        } else {
          charFreq(word1(i)) += 1
        }
      }

      for (i <- 0 until word2.length()) {
        if (charFreq.get(word2(i)) == None) {
          return false
        } else {
          charFreq(word2(i)) -= 1
        }
      }

      return charFreq.keySet.filter(charFreq(_) != 0).size == 0
    }

    var anagramClasses: Array[List[String]] = Array()

    for (str <- strs) {
      println(s"Considering ${str}")
      println(s"Current classes: ${anagramClasses.foldLeft("")(_ + ", " + _)}")

      var notDone = true
      var i = 0
      while (i < anagramClasses.length && notDone) {
        println(s"Does it fit in ${anagramClasses(i)}")

        if (isAnagram(str, anagramClasses(i)(0))) {
          println("yes")
          anagramClasses(i) = anagramClasses(i).:+(str)
          notDone = false
        }

        i += 1
      }

      if (notDone) {
        println("Adding new class")
        anagramClasses = anagramClasses ++ List(List(str))
      }
    }

    return anagramClasses.toList
  }

  def groupAnagrams_GOOD_ACTUALLY(strs: Array[String]): List[List[String]] = {
    def countRep(word: String): String = {
      var rep: Array[Int] = Array.fill(26)(0)

      for (c <- word) {
        val i = c.toInt - 'a'.toInt
        rep(i) += 1
      }

      return rep.map(_.toString()).reduce(_ + _)
    }

    var anagramClasses: HashMap[String, List[String]] = new HashMap()

    for (str <- strs) {
      var rep = countRep(str)

      if (anagramClasses.get(rep) == None) anagramClasses.put(rep, List(str))
      else anagramClasses(rep) = anagramClasses(rep).+:(str)
    }

    return anagramClasses.values.toList
  }

}
