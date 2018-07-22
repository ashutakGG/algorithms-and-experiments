package algorithms.hackerrank;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGraphs {
//    public static class Edge<T> {
//        public final int v1;
//        public final int v2;
//        public final T len;
//
//        public Edge(int v1, int v2, T len) {
//            this.v1 = v1;
//            this.v2 = v2;
//            this.len = len;
//        }
//    }
//
//    public static class Graph<T extends Comparable<T>> {
//        public final Edge<T>[] edges;
//
//        public Graph(Edge<T>[] edges) {
//            this.edges = edges;
//        }
//
//        public Set<Integer> vertexes() {
//            return Arrays.stream(edges)
//                    .flatMap((Function<Edge<T>, Stream<Integer>>) tEdge -> Stream.of(tEdge.v1, tEdge.v2))
//                    .collect(Collectors.toCollection(HashSet::new));
//        }
//
//        public Map<Integer, List<Integer>> adjacentVert() {
//            Map<Integer, List<Integer>> res = new HashMap<>();
//
//            for(Edge e: edges) {
//                List<Integer> l = res.computeIfAbsent(e.v1, k -> new ArrayList<>());
//                List<Integer> l2 = res.computeIfAbsent(e.v2, k -> new ArrayList<>());
//
//                l.add(e.v2);
//                l2.add(e.v1);
//            }
//
//            return res;
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    public static <T extends Comparable<T>> Graph<T> findMinimalSpainTreeKruskal(Graph<T> graph) {
//        Set<Integer> vertexes = graph.vertexes();
//        int vertexNum = vertexes.size();
//        List<Edge<T>> resEdges = new ArrayList<>(vertexNum - 1);
//
//        JavaDisjointSetForest<Integer> components = new JavaDisjointSetForest<>();
//        for (Integer v : vertexes) {
//            components.makeSet(v);
//        }
//
//        Arrays.sort(graph.edges, Comparator.comparing(o -> o.len));
//
//
//        for (Edge edge : graph.edges) {
//            Integer set1 = components.findSet(edge.v1);
//            Integer set2 = components.findSet(edge.v2);
//            if (!set1.equals(set2)) {
//                components.link(set1, set2);
//                resEdges.add(edge);
//            }
//        }
//
//        return new Graph((Edge[]) resEdges.toArray());
//    }
}
