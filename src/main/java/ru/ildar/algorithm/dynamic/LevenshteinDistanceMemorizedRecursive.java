package ru.ildar.algorithm.dynamic;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class LevenshteinDistanceMemorizedRecursive {

    private char[] s1;
    private char[] s2;
    private int[][] cache;

    public void compare(String s1, String s2) {
        this.s1 = s1.toCharArray();
        this.s2 = s2.toCharArray();
        this.cache = new int[this.s1.length + 1][this.s2.length + 1];

        for (int i = 0; i <= this.s1.length; i++) {
            for (int j = 0; j <= this.s2.length; j++) {
                if (i == 0) {
                    cache[i][j] = j;
                } else if (j == 0) {
                    cache[i][j] = i;
                } else {
                    cache[i][j] = -1;
                }
            }
        }

        compare(this.s1.length, this.s2.length);
    }

    private int compare(int i, int j) {
        if (cache[i][j] != -1) {
            return cache[i][j];
        }

        if (Math.min(i, j) == 0) {
            cache[i][j] = Math.max(i, j);
        } else {
            int match = s1[i - 1] != s2[j - 1] ? 1 : 0;
            int deletion = compare(i - 1, j) + 1;
            int insertion = compare(i, j - 1) + 1;
            int matching = compare(i - 1, j - 1) + match;

            cache[i][j] = min(deletion, insertion, matching);
        }

        return cache[i][j];
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
        return cache[this.s1.length][this.s2.length];
    }
}
