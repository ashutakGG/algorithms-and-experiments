package algorithms.hackerrank;

import java.util.*;

//public class JavaDisjointSetForest<T> implements JavaDisjointSet<T> {
//    private final Map<T, T> parent = new HashMap<>();
//    private final Map<T, Integer> rank = new HashMap<>();
//
//    @Override
//    public void makeSet(T x) {
//        parent.put(x, x);
//        rank.put(x, 0);
//    }
//
//    @Override
//    public void union(T x, T y) {
//        link(findSet(x), findSet(y));
//    }
//
//    public void link(T root1, T root2) {
//        Integer rank1 = rank.get(root1);
//        Integer rank2 = rank.get(root2);
//        if (rank1 > rank2) {
//            parent.put(root2, root1);
//        } else {
//            parent.put(root1, root2);
//            if (rank1.equals(rank2)) {
//                rank.put(root2, rank2 + 1);
//            }
//        }
//    }
//
//    @Override
//    public T findSet(T x) {
//        T parentX = parent.get(x);
//        if (!parentX.equals(x)) {
//            parent.put(x, findSet(parentX));
//        }
//        return parent.get(x);
//    }
//}
