package algorithms.hackerrank

import scala.collection.mutable

object Hackerland {

  def toBinaryString(sum: BigInt): String = {
    val cnt = sum.bitLength
    val res = StringBuilder.newBuilder
    for (i <- 1 to cnt) {
      res.append(if (sum.testBit(cnt - i)) "1" else "0")
    }
    res.mkString
  }

  def roadsInHackerland(n: Int, roads: Array[Array[Int]]): String = {
    var sum = BigInt(0)

    val r: Array[(Int, Int, Int)] = roads.map(arr => (arr(0), arr(1), arr(2)))

    var start = System.currentTimeMillis()
    val edges = r.map { case (a, b, pow) => (a, b, BigInt(0).setBit(pow)) }
    println("Calculated edges")
    val spanningTree = Graphs.minimumSpanningTree(new Graph(edges))
    println("Spanning tree in: " + (System.currentTimeMillis() - start))
    println("spanningTree=" + spanningTree)

    start = System.currentTimeMillis()

    sum = distances(spanningTree)
    println("Distances in: " + (System.currentTimeMillis() - start))
    println("Actual=" + sum)
    toBinaryString(sum)
  }

  private def distances(g: Graph): BigInt = {
    val costs = for (e <- g.edges) yield {
      val countInLeft = countIn(e._1, Set(e._1, e._2), g)
      val countInRight = countIn(e._2, Set(e._1, e._2), g)
      e._3 * countInLeft * countInRight
    }

    costs.sum
  }

  private def countIn(startV: Int, watched: Set[Int], g: Graph): Int = {
    val counts = for (
      (v, _) <- g.adjacentWith(startV)
      if !watched.contains(v)
    ) yield {
      countIn(v, watched + v, g)
    }

    val s = if (counts.isEmpty) 0 else counts.sum
    s + 1
  }
}
