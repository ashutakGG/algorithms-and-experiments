import algorithms.hackerrank.JavaHackerland;

public class HackerlandTest {
    public static void main(String[] args) {
        int[][] roads = new int[][]{new int[]{1, 3, 5}, new int[]{4, 5, 0}, new int[]{2, 1, 3}, new int[]{3, 2, 1}, new int[]{4, 3, 4}, new int[]{4, 2, 2}};
        String expected = "1000100";

        String res = JavaHackerland.roadsInHackerland(5, roads);

        System.out.println(res);
    }

    private def readInput(String fname) {
        int i = 0;
        int n = 0;
        int m = 0;
        int roads = new int[m][3];

        for (line <- linesOfFile(fname)) {
            if (i == 0) {
                val nm = line.split(" ")
                n = nm(0).trim.toInt
                m = nm(1).trim.toInt
                roads = Array.ofDim[Int](m, 3)
            } else {
                roads(i-1) = line.split(" ").map(_.trim.toInt)
            }
            i+=1
        }

        (n, roads)
    }

}
