package union_find

object SuccessorWithDeleteClient {
  def main(args: Array[String]): Unit = {
    val successorWithDelete = new SuccessorWithDeleteUnionFind(6)
    println("Successor = " + successorWithDelete.removeAndGetSuccessor(3))
    println("Successor = " + successorWithDelete.removeAndGetSuccessor(2))
    println("Successor = " + successorWithDelete.removeAndGetSuccessor(1))
    println("Successor = " + successorWithDelete.removeAndGetSuccessor(4))
    successorWithDelete.printIndices()
    successorWithDelete.printObjectArray()
  }
}

class SuccessorWithDeleteUnionFind(n: Int) extends QuickUnionFindStructure(n) {
  def removeAndGetSuccessor(elem: Int): Int = {
    union(elem, elem+1)
    id(elem)
  }
}
