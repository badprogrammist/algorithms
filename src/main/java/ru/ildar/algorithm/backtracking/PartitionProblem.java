package ru.ildar.algorithm.backtracking;

import java.util.Arrays;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class PartitionProblem {

    private int[] sequence;
    private int partsNumber;
    private int[] pointers;
    private int[] solution;
    private int minDiff;

    private int iter = 0;

    public void find(int[] sequence, int partsNumber) {
        this.sequence = sequence;
        this.partsNumber = partsNumber;
        this.solution = new int[partsNumber];
        this.pointers = new int[partsNumber];
        for (int i = 0; i < pointers.length; i++) {
            pointers[i] = i;
        }
        this.minDiff = Integer.MAX_VALUE;

        find(partsNumber - 1);
    }

    private void find(int index) {
        checkSolution();

        int pointer = pointers[index];
        int begin = index;
        int end = index == partsNumber - 1 ? sequence.length : pointers[index + 1];

        for (int i = begin; i < end; i++) {
            pointers[index] = i;

            if (index > 0) {
                find(index - 1);
            }
        }
        pointers[index] = pointer;
    }

    private void checkSolution() {
        int diff = calcDiff();
        if (diff < this.minDiff) {
            System.arraycopy(pointers, 0, solution, 0, partsNumber);
            this.minDiff = diff;
        }
    }

    private int calcDiff() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < pointers.length; i++) {
            int begin = pointers[i];
            int end = i == pointers.length - 1 ? sequence.length - 1 : pointers[i + 1] - 1;
            int sum = sum(begin, end);

            if (sum < min) {
                min = sum;
            }
            if (sum > max) {
                max = sum;
            }
        }

        return max - min;
    }

    private int sum(int begin, int end) {
        int sum = 0;

        for (int i = begin; i <= end; i++) {
            sum += sequence[i];
        }

        return sum;
    }


    public int[] getSolution() {
        return solution;
    }

    public int getMinDiff() {
        return minDiff;
    }
}
