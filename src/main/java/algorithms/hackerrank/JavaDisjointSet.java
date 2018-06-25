package algorithms.hackerrank;

public interface JavaDisjointSet<T> {
    void makeSet(T x);

    /**
     * Unions sets which contains x and y in one set.
     */
    void union(T x, T y);

    /**
     * Finds and returns representative element of set which contains x.
     */
    T findSet(T x);
}
