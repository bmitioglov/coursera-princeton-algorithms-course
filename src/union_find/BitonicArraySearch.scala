package union_find

/**
  * Created by Boris Mitioglov on 15/10/2018.
  */
class BitonicArraySearch(n: Int) {
  val array: Array[Int] = Array.ofDim[Int](n)

  for (i <- 0 until n) {
    if (i > n / 2) {
      array(i) = n - i - 1
    } else {
      array(i) = i
    }
  }

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
      val index = (end - start) / 2 + start
      println(start + " " + end + " " + index)
      if (array(index) > array(index - 1) && array(index) > array(index + 1)) {
        return index
      }
      if (array(index) > array(index - 1)) {
        start = index
      }
      if (array(index) < array(index - 1)) {
        end = index
      }
    }
    throw new Exception("not a bitonic array")
  }

  def findElem(elem: Int): Int = {
    val rootIndex = findRootElementInBitonicArray()
    var result = binarySearchIterative(array, elem, rootIndex, 0)
    //TODO: needs refactoring
    if (result != -1) return result
    if (result == -1) result = binarySearchIterative(array, elem, 0, rootIndex)
    if (result != -1) return result
    else result = -1


    result
  }

  def binarySearchIterative(list: Array[Int], target: Int, rightParam: Int, leftParam: Int): Int = {
    var left = leftParam
    var right = rightParam
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
}

object BitonicArraySearch {
  def main(args: Array[String]): Unit = {
    val bas: BitonicArraySearch = new BitonicArraySearch(6)
    bas.printObjectArray()
    println("ROOT = " + bas.findRootElementInBitonicArray())
    println("find = " + bas.findElem(0))
  }
}
