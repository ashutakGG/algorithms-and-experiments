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

  def tmpmethod(): Unit = {
    var a = mutable.ListBuffer(1)
    a = mutable.LinkedList(1)
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

    sum = distances2(spanningTree)
    println("Distances in: " + (System.currentTimeMillis() - start))
    println("Actual=" + sum)
    toBinaryString(sum)
  }

  private def distances2(g: Graph[BigInt]): BigInt = {
    val adjacentVert: Map[Int, Seq[(Int, BigInt)]] =
      g.flatMap { case (a, b, c) => Seq((a, b, c), (b, a, c)) }
        .groupBy { case (a, _, _) => a }
        .mapValues(s => s.map { case (_, b, c) => (b, c) })

    val adjacentVertex = mutable.HashMap(adjacentVert.toSeq: _*)
    val vertexNum = g.flatMap { case (a, b, _) => Seq(a, b) }.toSet.size

    println("start calculation")

    var edgeN = 0

    val start = System.currentTimeMillis()
    var timeIn = 0L

    val costs = for (edge <- g) yield {
      edgeN += 1
      if (edgeN % 1000 == 0)
        println("Distance for new edge: " + edgeN + ", time(countIn)/totalTime: " + (timeIn * 1.0/(System.currentTimeMillis() - start)))

      timeIn -= System.currentTimeMillis()

      val watched: Array[Boolean] = Array.fill[Boolean](vertexNum + 1)(false)
      watched(edge._1) = true
      watched(edge._2) = true

//      val countInLeft = countIn2(edge._1, watched, g, adjacentVertex)
      val countInLeft = countIn2(edge._1, watched, adjacentVertex)
      timeIn += System.currentTimeMillis()

      val countInRight = vertexNum - countInLeft
      edge._3 * countInLeft * countInRight
    }

    costs.sum
  }

  private def countIn2(startV: Int, watched: Array[Boolean], adjacentVertex: mutable.HashMap[Int, Seq[(Int, BigInt)]]): Int = {
    val q = mutable.Queue(startV)

    var count = 0

    while (q.nonEmpty) {
      val vertex = q.dequeue()
      count += 1

      for ((v, _) <- adjacentVertex(vertex)) {
        if (!watched(v)) {
          watched(v) = true
          q.enqueue(v)
        }
      }
    }

    count
  }
}
