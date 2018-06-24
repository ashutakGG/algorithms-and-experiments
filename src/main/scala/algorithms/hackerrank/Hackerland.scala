package algorithms.hackerrank

import algorithms.hackerrank.Graphs.Graph

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
    val spanningTree = Graphs.minimumSpanningTreeKruskal(edges)
    println("Spanning tree in: " + (System.currentTimeMillis() - start))
    //    println("spanningTree=" + spanningTree)

    start = System.currentTimeMillis()

    sum = distances(spanningTree)
    println("Distances in: " + (System.currentTimeMillis() - start))
    println("Actual=" + sum)
    toBinaryString(sum)
  }

  private def distances(g: Graph[BigInt]): BigInt = {
    val adjacentVertex: Map[Int, Seq[(Int, BigInt)]] =
      g.flatMap { case (a, b, c) => Seq((a, b, c), (b, a, c)) }
        .groupBy { case (a, _, _) => a }
        .mapValues(s => s.map { case (a, b, c) => (b, c) })

    val costs = for (e <- g) yield {
      val countInLeft = countIn(e._1, Set(e._1, e._2), g, adjacentVertex)
      val countInRight = countIn(e._2, Set(e._1, e._2), g, adjacentVertex)
      e._3 * countInLeft * countInRight
    }

    costs.sum
  }

  private def countIn(startV: Int, watched: Set[Int], g: Graph[BigInt], adjacentVertex: Map[Int, Seq[(Int, BigInt)]]): Int = {
    val counts = for (
      (v, _) <- adjacentVertex(startV)
      if !watched.contains(v)
    ) yield {
      countIn(v, watched + v, g, adjacentVertex)
    }

    val s = if (counts.isEmpty) 0 else counts.sum

    s + 1
  }
}
