package ru.ildar.algorithm.dynamic;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class LevenshteinDistanceNaiveRecursive {

    private char[] s1;
    private char[] s2;
    private int distance;


    public void compare(String s1, String s2) {
        this.s1 = s1.toCharArray();
        this.s2 = s2.toCharArray();

        distance = compare(this.s1.length - 1, this.s2.length - 1);
    }

    private int compare(int i, int j) {
        if (Math.min(i, j) == 0) {
            return Math.max(i, j);
        }

        int match = s1[i] != s2[j] ? 1 : 0;
        int deletion = compare(i - 1, j) + 1;
        int insertion = compare(i, j - 1) + 1;
        int matching = compare(i - 1, j - 1) + match;

        return min(deletion, insertion, matching);
    }

    private int min(int... items) {
        int min = Integer.MAX_VALUE;

        for (int item : items) {
            if (item < min) {
                min = item;
            }
        }

        return min;
    }

    public int getDistance() {
        return distance;
    }
}
