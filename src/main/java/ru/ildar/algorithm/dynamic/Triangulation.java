package ru.ildar.algorithm.dynamic;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class Triangulation {

    private double[][] points;
    private double[][] distance;
    private int[][] diagonals;
    private boolean[] discarded;
    private double weight;

    public void triangulate(double[][] points) {
        this.points = points;
        diagonals = new int[points.length - 3][2];
        weight = 0;
        distance = new double[points.length][points.length];
        discarded = new boolean[points.length];

        for (int p1 = 0; p1 < points.length - 2; p1++) {
            int p2 = p1 + 2;
            calcDist(p1, p2);
        }

        for (int diagonalIdx = 0; diagonalIdx < points.length - 3; diagonalIdx++) {
            int[] minDiagonal = findMinDiagonal();

            discard(minDiagonal);
            putDiagonal(diagonalIdx, minDiagonal);
            calcDistAboveDiagonal(minDiagonal);
        }

    }

    private int[] findMinDiagonal() {
        double minDistance = Double.MAX_VALUE;
        int[] minDiagonal = new int[2];

        for (int p1 = 0; p1 < points.length - 2; p1++) {
            if (discarded[p1]) {
                continue;
            }

            int p2 = p1;
            int notDiscardedNumber = 0;
            do {
                p2++;
                if (!discarded[p2]) {
                    notDiscardedNumber++;
                }
            } while ((discarded[p2] || notDiscardedNumber != 2)
                    && p2 < points.length - 1);

            if (discarded[p2] || notDiscardedNumber != 2) {
                continue;
            }

            if (distance[p1][p2] < minDistance) {
                minDistance = distance[p1][p2];
                minDiagonal[0] = p1;
                minDiagonal[1] = p2;
            }
        }

        return minDiagonal;
    }

    private int discard(int[] diagonalPoints) {
        int point = diagonalPoints[0] + 1;
        while (discarded[point] && point < diagonalPoints[1]) {
            point++;
        }

        discarded[point] = true;
        return point;
    }

    private void putDiagonal(int diagonalIdx, int[] diagonalPoints) {
        diagonals[diagonalIdx] = diagonalPoints;
        weight += distance[diagonalPoints[0]][diagonalPoints[1]];
    }

    private void calcDistAboveDiagonal(int[] diagonalPoints) {
        int p1 = diagonalPoints[0];
        int p2 = diagonalPoints[1] + 1;

        if (p1 >= 0 && p2 < points.length) {
            calcDist(p1, p2);
        }

        p1 = diagonalPoints[0] - 1;
        p2 = diagonalPoints[1];

        if (p1 >= 0 && p2 < points.length) {
            calcDist(p1, p2);
        }
    }

    private double calcDist(int p1, int p2) {
        double dist= Math.sqrt(
                Math.pow((points[p2][0] - points[p1][0]), 2)
                        + Math.pow(points[p2][1] - points[p1][1], 2));

        distance[p1][p2] = dist;
        distance[p2][p1] = dist;

        return dist;
    }

    public int[][] getDiagonals() {
        return diagonals;
    }

    public double getWeight() {
        return weight;
    }

}
