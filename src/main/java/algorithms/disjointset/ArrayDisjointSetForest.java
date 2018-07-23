package algorithms.disjointset;

import java.util.Arrays;

@SuppressWarnings("unused")
public class ArrayDisjointSetForest {
    // Contains information about both: parents and ranks;
    // if array[i] < 0 - it means that 'i' is a root, where |array[i]| - a number of elements in that set.
    // if array[i] >= 0 - it means that 'i' is a child node and array[i] is refer to root.
    private int[] array;

    public ArrayDisjointSetForest(int numElements) {
        array = new int[numElements];
        for (int i = 0; i < array.length; i++) {
            array[i] = -1; // Each set contains only 1 element.
        }
    }

    public void union(int x, int y) {
        linkRoots(find(x), find(y));
    }

    public void linkRoots(int root1, int root2) {
        if (root1 == root2)
            return;

        int arr1 = array[root1];
        int arr2 = array[root2];

        if (arr1 >= 0 || arr2 >= 0)
            throw new IllegalArgumentException("One or both argument(s) not root(s) " +
                    "[root1: " + root1 + ", root2: " + root2 + ", arr1: " + arr1 + ", arr2: " + arr2 + "]");

        if (arr2 < arr1) {// root2 has larger tree.
            // Update # of items in root2's tree and make root2 new root.
            array[root2] += arr2;
            array[root1] = root2;
        } else {// root1 has equal or larger tree.
            // Update # of items in root1's tree and make root1 new root.
            array[root1] += arr1;
            array[root2] = root1;
        }
    }

    public int find(int x) {
        if (array[x] < 0) {
            return x;
        } else {
            // Compress path by making the root x's parent.
            array[x] = find(array[x]);
            return array[x];
        }
    }
}
