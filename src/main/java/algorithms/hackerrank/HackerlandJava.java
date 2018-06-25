package algorithms.hackerrank;

import com.sun.javafx.geom.Edge;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HackerlandJava {
    private static String toBinaryString(BigInteger sum){
        int cnt = sum.bitLength();
        StringBuilder res = new StringBuilder();
        for (int i = 1; i <= cnt; i++) {
            res.append(sum.testBit(cnt - i) ? "1" : "0");
        }

        return res.toString();
    }


    @SuppressWarnings("unchecked")
    static String roadsInHackerland(int n, int[][] roads) {
        JavaGraphs.Edge<BigInteger>[] edges = Arrays.stream(roads)
                .map(ints -> new JavaGraphs.Edge<>(ints[0], ints[1], BigInteger.ZERO.setBit(ints[2])))
                .toArray(JavaGraphs.Edge[]::new);

        JavaGraphs.Graph<BigInteger> spanningTree = JavaGraphs.findMinimalSpainTreeKruskal(new JavaGraphs.Graph<>(edges));

        BigInteger sum = distances(spanningTree);

        return toBinaryString(sum);
    }

    private static BigInteger distances(JavaGraphs.Graph<BigInteger> g) {
        Map<Integer, List<Integer>> adjacentVert = g.adjacentVert();

        int vertexNum = g.vertexes().size();

        BigInteger sum = BigInteger.ZERO;

        for (JavaGraphs.Edge<BigInteger> edge: g.edges) {
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
}
