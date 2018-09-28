/**
  * Created by Boris Mitioglov on 27/09/2018.
  */
object Main {
  def main(args: Array[String]): Unit = {
    val quickUnionFindStructure = new QuickUnionFindStructure(10)
    quickUnionFindStructure.union(0, 5)
    quickUnionFindStructure.union(5, 6)
    println(quickUnionFindStructure.connected(0, 6))
    println(quickUnionFindStructure.connected(0, 7))
  }
}

class QuickUnionFindStructure(n: Int) {
  private var id: Array[Int] = Array.ofDim[Int](n)
  for (i <- 0 until n) {
    id(i) = i
  }

  def root(i: Int): Int = {
    var x: Int = i
    while (x != id(x)) {
      x = id(x)
    }
    x
  }

  def connected(p: Int, q: Int): Boolean = {
    root(p) == root(q)
  }

  def union(p: Int, q: Int): Unit = {
    val i = root(p)
    val j = root(q)
    id(i) = j
  }
}
