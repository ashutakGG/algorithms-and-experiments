package algorithms.hackerrank

import scala.collection.mutable

object Graphs {
    def minimumSpanningTree(graph: Graph): Graph = {
    var res = new Graph()
    val connectedComponents = mutable.Set[Set[Int]]()
    val knownVertexes = mutable.HashSet[Int]()

    val edges: Seq[(Int, Int, BigInt)] = graph.edges.sortBy { case (_, _, len) => len }

    val start = System.currentTimeMillis()
    var curIdx = 0
    while (!(res.vertexNum >= graph.vertexNum && connectedComponents.size == 1)) {
      val edge = edges(curIdx)

      if (curIdx % 1000 == 0)
        println(curIdx + " " + connectedComponents.size + " in " + (System.currentTimeMillis() - start))

      curIdx += 1
      val componentA = if (knownVertexes.contains(edge._1)) connectedComponents.find(comp => comp.contains(edge._1)) else None
      val componentB = if (knownVertexes.contains(edge._2)) connectedComponents.find(comp => comp.contains(edge._2)) else None

      if (componentA.isEmpty) {
        if (componentB.isEmpty) {
          val newComponent = Set(edge._1, edge._2)
          connectedComponents += newComponent

          res = res.add(edge)
          knownVertexes += edge._1
          knownVertexes += edge._2
        } else {
          connectedComponents.remove(componentB.get)
          connectedComponents += componentB.get + edge._1

          res = res.add(edge)

          knownVertexes += edge._1
        }
      } else {
        if (componentB.isEmpty) {
          connectedComponents.remove(componentA.get)
          connectedComponents += componentA.get + edge._2

          res = res.add(edge)
          knownVertexes += edge._2
        } else {
          if (componentA.equals(componentB)) {
            // Noop
          } else {
            val s = connectedComponents.size

            connectedComponents.remove(componentA.get)
            connectedComponents.remove(componentB.get)
            connectedComponents += componentA.get ++ componentB.get

            if (!(s == connectedComponents.size + 1))
              throw new IllegalStateException(connectedComponents.toString)

            res = res.add(edge)
          }
        }
      }
    }

    res
  }
}

class Graph(private val g: Seq[(Int, Int, BigInt)]) {
  private lazy val adjacentVertex: Map[Int, Seq[(Int, BigInt)]] =
    g.flatMap { case (a, b, c) => Seq((a, b, c), (b, a, c)) }
      .groupBy { case (a, _, _) => a }
      .mapValues(s => s.map { case (a, b, c) => (b, c) })

  lazy val vertexNum: Int = g.flatMap { case (a, b, _) => Seq(a, b) }.toSet.size

  def adjacentWith(vertex: Int) = adjacentVertex(vertex)

  def this() {
    this(Array[(Int, Int, BigInt)]())
  }

  def edges = g

  def add(edge: (Int, Int, BigInt)): Graph = {
    new Graph(g :+ edge)
  }

  override def toString: String = {
    g.toString()
  }
}
