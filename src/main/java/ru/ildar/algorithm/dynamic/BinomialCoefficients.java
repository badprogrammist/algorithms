package ru.ildar.algorithm.dynamic;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class BinomialCoefficients {

    public long compute(int n, int m) {
        if (n < m) {
            throw new IllegalArgumentException("The argument n should be greater or equal than the argument m");
        }

        long[][] cache = new long[n + 1][n + 1];

        for (int i = 0; i <= n; i++) {
            cache[i][0] = 1;
            cache[i][i] = 1;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                cache[i][j] = cache[i - 1][j - 1] + cache[i - 1][j];
            }
        }

        return cache[n][m];
    }

}
