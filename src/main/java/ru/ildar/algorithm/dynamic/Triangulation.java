package ru.ildar.algorithm.dynamic;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *
 * Minimum Weight Triangulation (Gilbert (1979) and Klincsek (1980))
 *
 */
public class Triangulation {

    private double[][] points;
    private double[][] distance;
    private int[][] distancePoints;

    public void triangulate(double[][] points) {
        this.points = points;
        distancePoints = new int[points.length][points.length];
        distance = new double[points.length][points.length];

        for (int i = 0; i < points.length; i++) {
            int j = i == points.length - 1 ? 0 : i + 1;
            distance[i][j] = calcDist(i, j);
        }

        for (int gap = 2; gap < points.length; gap++) {
            for (int i = 0; i < points.length - gap; i++) {
                int j = i + gap;
                double minDist = Double.MAX_VALUE;
                int minK = -1;

                for (int k = i + 1; k < j; k++) {
                    double dist = distance[i][k] + distance[k][j] + calcDist(i, k) + calcDist(k, j);

                    if (dist < minDist) {
                        minDist = dist;
                        minK = k;
                    }
                }
                distancePoints[i][j] = minK;
                distance[i][j] = minDist;
            }
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
        int[][] diagonals = new int[points.length - 3][2];
        int diagonalIdx = 0;
        int i = 0;
        int j = points.length - 1;
        int k = distancePoints[i][j];

        while ((j - k >= 2 || k - i >= 2) && diagonalIdx <= (points.length - 3)) {
            if (j - k >= 2) {
                diagonals[diagonalIdx] = new int[] {k, j};

                if (diagonalIdx == 0) {
                    diagonalIdx++;
                    diagonals[diagonalIdx] = new int[] {i, k};
                }

                i = k;
            } else if (k - i >= 2) {
                diagonals[diagonalIdx] = new int[] {i, k};

                if (diagonalIdx == 0) {
                    diagonalIdx++;
                    diagonals[diagonalIdx] = new int[] {k, j};
                }

                j = k;
            }

            diagonalIdx++;
            k = distancePoints[i][j];
        }

        return diagonals;
    }

    public double getWeight() {
        return distance[0][points.length - 1];
    }

}
