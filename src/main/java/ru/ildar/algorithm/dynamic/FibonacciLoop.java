package ru.ildar.algorithm.dynamic;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class FibonacciLoop {

    public long compute(long n) {
        long back1 = 1;
        long back2 = 0;
        long next;

        if (n == 0) {
            return 0;
        }

        for (int i = 2; i < (int) n; i++) {
            next = back1 + back2;
            back2 = back1;
            back1 = next;
        }


        return back1 + back2;
    }

}
