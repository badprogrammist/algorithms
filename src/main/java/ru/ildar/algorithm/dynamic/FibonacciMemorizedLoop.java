package ru.ildar.algorithm.dynamic;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class FibonacciMemorizedLoop {

    public long compute(long n) {
        long[] cache = new long[(int) n + 2];
        cache[0] = 0;
        cache[1] = 1;

        for (int i = 2; i <= (int) n; i++) {
            cache[i] = cache[i - 1] + cache[i - 2];
        }

        return cache[(int) n];
    }

}
