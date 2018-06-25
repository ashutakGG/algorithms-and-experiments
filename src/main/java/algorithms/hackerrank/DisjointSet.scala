package algorithms.hackerrank

import scala.collection.mutable

/**
  * Set of Sets of elements T.
  *
  * @tparam T - type of elements.
  */
trait DisjointSet[T] {
  def makeSet(x: T)

  /**
    * Unions sets which contains x and y in one set.
    */
  def union(x: T, y: T)

  /**
    * Finds and returns representative element of set which contains x.
    */
  def findSet(x: T): T
}

/**
  * Implementation of disjoint-set forest (Kormen part 21.3)
  */
class DisjointSetForest[T] extends DisjointSet[T] {
  private val parent = mutable.HashMap[T, T]()
  private val rank = mutable.HashMap[T, Int]()

  override def makeSet(x: T): Unit = {
    parent(x) = x
    rank(x) = 0
  }

  override def union(x: T, y: T): Unit = {
    link(findSet(x), findSet(y))
  }

  private def link(x: T, y: T): Unit = {
    val rankX = rank(x)
    val rankY = rank(y)

    if (rankX > rankY) {
      parent(y) = x
    } else {
      parent(x) = y
      if (rankX == rankY) {
        rank(y) = rankY + 1
      }
    }
  }

  override def findSet(x: T): T = {
    val p = parent(x)
    if (p != x)
      parent(x) = findSet(p)
    parent(x)
  }

  def printSet(x: T): Unit = {

  }
}
