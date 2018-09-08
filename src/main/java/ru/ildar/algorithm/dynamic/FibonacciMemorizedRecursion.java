package ru.ildar.algorithm.dynamic;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class FibonacciMemorizedRecursion {

    public long compute(long n) {
        long[] cache = new long[(int) n + 1];
        return compute(n, cache);
    }

    private long compute(long n, long[] cache) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        if (cache[(int) n] == 0) {
            long value = compute(n - 1, cache) + compute(n - 2, cache);
            cache[(int) n] = value;
        }

        return cache[(int) n];
    }

}
