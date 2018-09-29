package ru.ildar.algorithm.dynamic;

import java.util.Arrays;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class PartitionProblem {

    private int partsNumber;
    private int[] sequence;
    private int[][] indices;

    public void find(int[] sequence, int partsNumber) {
        this.partsNumber = partsNumber;
        this.sequence = sequence;
        this.indices = new int[sequence.length][partsNumber];
        int[][] m = new int[sequence.length][partsNumber];

        for(int k = 0; k < partsNumber; k++) {
            m[0][k] = sequence[0];
        }
        for (int i = 1; i < sequence.length; i++) {
            m[i][0] = sequence[i] + m[i - 1][0];
        }

        for (int k = 1; k < partsNumber; k++) {
            for (int n = 1; n < sequence.length; n++) {
                m[n][k] = Integer.MAX_VALUE;

                for (int i = 0; i < n; i++) {
                    int sum = sum(sequence, i + 1, n);

                    int max = max(m[i][k-1], sum);

                    if (max < m[n][k]) {
                        m[n][k] = max;
                        indices[n][k] = i;
                    }
                }
            }
        }

    }

    private int max(int v1, int v2) {
        return v1 < v2 ? v2 : v1;
    }

    private int sum(int[] s, int begin, int end) {
        int sum = 0;

        for (int j = begin; j <= end; j++) {
            sum += s[j];
        }

        return sum;
    }

    public int[] getSolution() {
        int[] solution = new int[partsNumber];
        reconstructSolution(solution, sequence.length - 1, partsNumber - 1);

        return solution;
    }

    private void reconstructSolution(int[] solution, int n, int k) {
        if (k != 0) {
            int begin = indices[n][k] + 1;
            int end = n;
            solution[k] = begin;
            System.out.printf("[%d,%d] = {%s}, sum=%d%n",
                    begin, end,
                    Arrays.toString(Arrays.copyOfRange(sequence, begin, end)),
                    sum(sequence, begin, end));
            reconstructSolution(solution, indices[n][k], k - 1);
        }
    }

}
