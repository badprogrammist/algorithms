package ru.ildar.algorithm.dynamic;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *
 * Assume that there are n numbers (some possibly negative) on a circle,
 * and we wish to find the maximum contiguous sum along an arc of the circle.
 * Give an efficient algorithm for solving this problem.
 *
 */
public class MaxSumArcOfCircle {

    private int maxSum;
    private int start;
    private int length;

    public void find(int[] circle) {
        maxSum = Integer.MIN_VALUE;
        start = -1;
        length = -1;

        for (int i = 0; i < circle.length; i++) {
            int sum = circle[i];

            for(int l = 0; l < circle.length; l++) {
                if (l != 0) {
                    int end = i + l >= circle.length ? (i + l) - circle.length : i + l;
                    sum += circle[end];
                }

                if (sum > maxSum) {
                    maxSum = sum;
                    start = i;
                    length = l;
                }
            }
        }
    }

    public int getMaxSum() {
        return maxSum;
    }

    public int getStart() {
        return start;
    }

    public int getLength() {
        return length;
    }
}
