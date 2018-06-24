package algorithms.hackerrank

import scala.collection.mutable

object Graphs {
  type Graph[T] = Seq[(Int, Int, T)]

  def minimumSpanningTreeKruskal[T <: Ordered[T]](graph: Graph[T]): Graph[T] = {
    val disjointSet = new DisjointSetForest[Int]()

    val vertexes = graph.flatMap { case (a, b, _) => Seq(a, b) }.toSet
    for (v <- vertexes)
      disjointSet.makeSet(v)

    val vertexNum = graph.flatMap { case (a, b, _) => Seq(a, b) }.toSet.size
    val sortedEdges = graph.sortBy { case (_, _, len) => len }
    var curIdx = 0
    var res: List[(Int, Int, T)] = List[(Int, Int, T)]()

    while (!(res.size == (vertexNum - 1))) {
      val edge = sortedEdges(curIdx)
      val componentA = disjointSet.findSet(edge._1)
      val componentB = disjointSet.findSet(edge._2)
      if (componentA != componentB) {
        disjointSet.union(componentA, componentB)
        res = edge :: res
      }
      curIdx += 1
    }

    res
  }
}