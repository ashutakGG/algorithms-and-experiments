package algorithms.hackerrank

import scala.collection.mutable

object Graphs {
  type Edge[T] = (Int, Int, T)
  type Graph[T] = Seq[Edge[T]]

  def minimumSpanningTreeKruskal[T <: Ordered[T]](graph: Graph[T]): Graph[T] = {
    val disjointSet = new DisjointSetForest[Int]()

    val vertexes = graph.flatMap { case (a, b, _) => Seq(a, b) }.toSet
    for (v <- vertexes)
      disjointSet.makeSet(v)

    var start = System.currentTimeMillis()

    val vertexNum = vertexes.size
    val sortedEdges = graph.sortBy { case (_, _, len) => len }
    var res = List[(Int, Int, T)]()

    println("Sort and vertexNum in " + (System.currentTimeMillis() - start) )
    start = System.currentTimeMillis()

    for (edge <- sortedEdges) {
      val setA = disjointSet.findSet(edge._1)
      val setB = disjointSet.findSet(edge._2)
      if (setA != setB) {
        disjointSet.union(setA, setB)
        res = edge :: res
      }

      if (res.size == (vertexNum - 1)) {
        return res
      }
    }

    throw new IllegalStateException()
  }
}