package ru.ildar.algorithm.sort;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *
 * Given a set of S containing n real numbers, and a real number x. We seek an algorithm to determine whether two elements
 * of S exist whose sum is exactly x.
 * Assume that S is sorted. Give an O(n) algorithm for the problem.
 *
 */
public class FindSummands {

    /**
     * Find summands of number x in a sorted array s of real numbers
     * @param s - sorted array of real numbers
     * @param x - a number which sum need to find
     * @return i and j as indexes of found elements
     */
    public static Summands find(double[] s, double x) {
        double[] t = new double[s.length];
        for(int i = 0; i < s.length; i++) {
            t[i] = x - s[i];
        }
        int i = 0;
        int j = t.length - 1;
        while(i < s.length && j >= 0) {
            if(s[i] == t[j]) {
                return new Summands(i, j);
            } else {
                if(s[i] < t[j]) {
                    i++;
                } else {
                    j--;
                }
            }
        }
        return Summands.NOT_FOUND;
    }

    static class Summands {

        static Summands NOT_FOUND = new Summands(-1, -1);

        int i;
        int j;

        Summands(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }
}
