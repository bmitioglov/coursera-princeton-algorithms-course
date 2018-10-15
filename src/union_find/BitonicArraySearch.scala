package union_find

/**
  * Created by Boris Mitioglov on 15/10/2018.
  */
class BitonicArraySearch(n: Int) {
//  val array: Array[Int] = Array.ofDim[Int](n)

//  for (i <- 0 until n) {
//    if (i > n / 2) {
//      array(i) = n - i - 1
//    } else {
//      array(i) = i
//    }
//  }
  val array = Array(-9, 2, 7, 11, 12, -5)

  def printObjectArray(): Unit = {
    print("Object Array:\t ")
    for (i <- array.indices) {
      print(array(i) + " ")
    }
    println()
  }

  def findRootElementInBitonicArray(): Int = {
    if (n < 3) throw new Exception("not a bitonic array")
    var start = 0
    var end = n - 1
    while (end >= start) {
      val mid = (end - start) / 2 + start
      if (array(mid) > array(mid - 1) && array(mid) > array(mid + 1)) {
        return mid
      }
      if (array(mid) > array(mid - 1)) {
        start = mid
      }
      if (array(mid) < array(mid - 1)) {
        end = mid
      }
    }
    throw new Exception("not a bitonic array")
  }

  def findElem(elem: Int): Int = {
    val rootIndex = findRootElementInBitonicArray()
    println("root index = " + rootIndex)
    var result = binarySearchIterative(array, elem, 0, rootIndex)
    if (result == -1) {
      return revertBinarySearchIterative(array, elem, rootIndex, n - 1)
    } else {
      return result
    }
  }

  def binarySearchIterative(list: Array[Int], target: Int, leftBound: Int, rightBound: Int): Int = {
    var left = leftBound
    var right = rightBound
    while (left <= right) {
      val mid = left + (right - left) / 2
      if (list(mid) == target)
        return mid
      else if (list(mid) > target)
        right = mid - 1
      else
        left = mid + 1
    }
    -1
  }

  def revertBinarySearchIterative(list: Array[Int], target: Int, leftBound: Int, rightBound: Int): Int = {
    var left = rightBound
    var right = leftBound
    while (left >= right) {
      val mid = left + (right - left) / 2
      if (list(mid) == target)
        return mid
      else if (list(mid) > target)
        right = mid + 1
      else
        left = mid - 1
    }
    -1
  }
}

//3logN complexity
object BitonicArraySearch {
  def main(args: Array[String]): Unit = {
    val bas: BitonicArraySearch = new BitonicArraySearch(6)
    bas.printObjectArray()
    println("find index = " + bas.findElem(12))
  }
}
