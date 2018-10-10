package union_find

import java.util

import scala.util.Random

class ThreeSumNSquared(n: Int) {

  val array: Array[Int] = Array.ofDim[Int](n)

  for (i <- 0 until n) {
    array(i) = Random.nextInt(n + 1 + n) - n
  }

  util.Arrays.sort(array)

  def printObjectArray(): Unit = {
    print ("Object Array:\t ")
    for (i <- array.indices) {
      print(array(i) + " ")
    }
    println()
  }

  val allPairs: List[String] = findPairs()

  def findPairs(): List[String] = {
    val aggList = List.newBuilder[String]
    for (i <- 0 until array.length - 1) {
      val key = array(i)
      var start = i + 1
      var end = n - 1
      while (start < end) {
        if (key + array(start) + array(end) == 0) {
          aggList += (key + " " + array(start) + " " + array(end))
          start += 1
          end -= 1
        } else if (key + array(start) + array(end) < 0) {
          start += 1
        } else if (key + array(start) + array(end) > 0) {
          end -= 1
        }
      }
    }
    aggList.result()
  }


}

object ThreeSumNSquared {
  def main(args: Array[String]): Unit = {
    val threeSumNSquared: ThreeSumNSquared = new ThreeSumNSquared(10)
    threeSumNSquared.printObjectArray()
    println(threeSumNSquared.allPairs)
  }
}
