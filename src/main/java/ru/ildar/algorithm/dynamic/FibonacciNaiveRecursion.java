package ru.ildar.algorithm.dynamic;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class FibonacciNaiveRecursion {

    public long compute(long n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        return compute(n - 1) + compute(n - 2);
    }

}
