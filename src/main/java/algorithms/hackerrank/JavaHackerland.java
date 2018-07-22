package algorithms.hackerrank;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaHackerland {
    public static class JavaDisjointSetForest<T> {
        private final Map<T, T> parent = new HashMap<>();
        private final Map<T, Integer> rank = new HashMap<>();

        public void makeSet(T x) {
            parent.put(x, x);
            rank.put(x, 0);
        }

        public void union(T x, T y) {
            link(findSet(x), findSet(y));
        }

        public void link(T root1, T root2) {
            Integer rank1 = rank.get(root1);
            Integer rank2 = rank.get(root2);
            if (rank1 > rank2) {
                parent.put(root2, root1);
            } else {
                parent.put(root1, root2);
                if (rank1.equals(rank2)) {
                    rank.put(root2, rank2 + 1);
                }
            }
        }

        public T findSet(T x) {
            T parentX = parent.get(x);
            if (!parentX.equals(x)) {
                parent.put(x, findSet(parentX));
            }
            return parent.get(x);
        }
    }

    public static class Edge<T> {
        public final int v1;
        public final int v2;
        public final T len;

        public Edge(int v1, int v2, T len) {
            this.v1 = v1;
            this.v2 = v2;
            this.len = len;
        }
    }

    public static class Graph<T extends Comparable<T>> {
        public final Edge<T>[] edges;

        public Graph(Edge<T>[] edges) {
            this.edges = edges;
        }

        public Set<Integer> vertexes() {
            return Arrays.stream(edges)
                    .flatMap((Function<Edge<T>, Stream<Integer>>) tEdge -> Stream.of(tEdge.v1, tEdge.v2))
                    .collect(Collectors.toCollection(HashSet::new));
        }

        public Map<Integer, List<Integer>> adjacentVert() {
            Map<Integer, List<Integer>> res = new HashMap<>();

            for(Edge e: edges) {
                List<Integer> l = res.computeIfAbsent(e.v1, k -> new ArrayList<>());
                List<Integer> l2 = res.computeIfAbsent(e.v2, k -> new ArrayList<>());

                l.add(e.v2);
                l2.add(e.v1);
            }

            return res;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> Graph<T> findMinimalSpainTreeKruskal(Graph<T> graph) {
        Set<Integer> vertexes = graph.vertexes();
        int vertexNum = vertexes.size();
        List<Edge<T>> resEdges = new ArrayList<>(vertexNum - 1);

        JavaDisjointSetForest<Integer> components = new JavaDisjointSetForest<>();
        for (Integer v : vertexes) {
            components.makeSet(v);
        }

        Arrays.sort(graph.edges, Comparator.comparing(o -> o.len));


        for (Edge edge : graph.edges) {
            Integer set1 = components.findSet(edge.v1);
            Integer set2 = components.findSet(edge.v2);
            if (!set1.equals(set2)) {
                components.link(set1, set2);
                resEdges.add(edge);
            }
        }

        return new Graph((Edge[]) resEdges.toArray(new Edge[0]));
    }

    private static String toBinaryString(BigInteger sum){
        int cnt = sum.bitLength();
        StringBuilder res = new StringBuilder();
        for (int i = 1; i <= cnt; i++) {
            res.append(sum.testBit(cnt - i) ? "1" : "0");
        }

        return res.toString();
    }


    @SuppressWarnings("unchecked")
    public static String roadsInHackerland(int n, int[][] roads) {
        Edge<BigInteger>[] edges = Arrays.stream(roads)
                .map(ints -> new Edge<>(ints[0], ints[1], BigInteger.ZERO.setBit(ints[2])))
                .toArray(Edge[]::new);

        Graph<BigInteger> spanningTree = findMinimalSpainTreeKruskal(new Graph<>(edges));

        BigInteger sum = distances(spanningTree);

        return toBinaryString(sum);
    }

    private static BigInteger distances(Graph<BigInteger> g) {
        Map<Integer, List<Integer>> adjacentVert = g.adjacentVert();

        int vertexNum = g.vertexes().size();

        BigInteger sum = BigInteger.ZERO;

        for (Edge<BigInteger> edge: g.edges) {
            boolean[] watched = new boolean[vertexNum + 1];
            watched[edge.v1] = true;
            watched[edge.v2] = true;

            int countInLeft = countIn(edge.v1, watched, adjacentVert);
            int countInRight = vertexNum - countInLeft;
            sum = sum.add(edge.len.multiply(BigInteger.valueOf(countInLeft)).multiply(BigInteger.valueOf(countInRight)));
        }

        return sum;
    }


    private static int countIn(Integer startV, boolean[] watched, Map<Integer, List<Integer>> adjacentVertex) {
        Queue<Integer> q = new LinkedList<>();
        q.add(startV);

        int count = 0;

        while (!q.isEmpty()) {
            Integer vertex = q.poll();
            count++;

            Iterable<Integer> adj = adjacentVertex.get(vertex);
            for (Integer v: adj) {
                if (!watched[v]) {
                    watched[v] = true;
                    q.add(v);
                }
            }
        }

        return count;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] nm = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nm[0].trim());

        int m = Integer.parseInt(nm[1].trim());

        int[][] roads = new int[m][3];

        for (int roadsRowItr = 0; roadsRowItr < m; roadsRowItr++) {
            String[] roadsRowItems = scanner.nextLine().split(" ");

            for (int roadsColumnItr = 0; roadsColumnItr < 3; roadsColumnItr++) {
                int roadsItem = Integer.parseInt(roadsRowItems[roadsColumnItr].trim());
                roads[roadsRowItr][roadsColumnItr] = roadsItem;
            }
        }

        String result = roadsInHackerland(n, roads);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedWriter.close();
    }
}
