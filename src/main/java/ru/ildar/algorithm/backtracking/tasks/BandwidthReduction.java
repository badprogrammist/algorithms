package ru.ildar.algorithm.backtracking.tasks;

import ru.ildar.algorithm.graph.Graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class BandwidthReduction {

    private Graph graph;
    private int[] solution;
    private int maxDistance;

    public void solve(Graph graph) {
        this.graph = graph;
        solution = new int[graph.getVerticesCount()];

        int[] permutation = new int[graph.getVerticesCount()];
        for (int i = 0; i < graph.getVerticesCount(); i++) {
            permutation[i] = i;
        }

        maxDistance = calcMaxDistance(permutation);

        permute(permutation, 0);
    }

    private void permute(int[] p, int k) {
        checkPermutation(p);
        if (k < p.length - 1) {
            for (int i = 0; i < p.length; i++) {
                if (i != k) {
                    swap(p, i, k);
                    permute(p, k + 1);
                    swap(p, i, k);
                }
            }
        }
    }

    private void checkPermutation(int[] p) {
        int distance = calcMaxDistance(p);

        if (distance < maxDistance) {
            System.arraycopy(p, 0, solution, 0, p.length);
            maxDistance = distance;
        }
    }

    private void swap(int[] p, int i, int j) {
        int item = p[i];
        p[i] = p[j];
        p[j] = item;
    }

    private int calcMaxDistance(int[] p) {
        int distance = Integer.MIN_VALUE;

        for (int i = 0; i < p.length; i++) {
            for (int j = i; j < p.length; j++) {
                if (i != j
                        && graph.isAdjacent(p[i], p[j])
                        && distance < (j - i)) {
                    distance = j - i;
                }
            }
        }

        return distance;
    }

    public int[] getSolution() {
        return solution;
    }

    public int getMaxDistance() {
        return maxDistance;
    }
}
