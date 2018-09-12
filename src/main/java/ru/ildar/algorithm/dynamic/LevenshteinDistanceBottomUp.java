package ru.ildar.algorithm.dynamic;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class LevenshteinDistanceBottomUp {

    private char[] s1;
    private char[] s2;
    private int[][] cache;

    public void compare(String s1, String s2) {
        this.s1 = s1.toCharArray();
        this.s2 = s2.toCharArray();
        this.cache = new int[this.s1.length + 1][this.s2.length + 1];

        for (int i = 0; i <= this.s1.length; i++) {
            cache[i][0] =  i;
        }
        for (int j = 0; j <= this.s2.length; j++) {
            cache[0][j] = j;
        }

        for (int i = 1; i <= this.s1.length; i++) {
            for (int j = 1; j <= this.s2.length; j++) {
                int deletion = cache[i - 1][j] + 1;
                int insertion = cache[i][j - 1] + 1;
                int matching = cache[i - 1][j - 1] + match(i - 1, j - 1);

                cache[i][j] = min(deletion, insertion, matching);
            }
        }
    }


    private int match(int i, int j) {
        return s1[i] != s2[j] ? 1 : 0;
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
