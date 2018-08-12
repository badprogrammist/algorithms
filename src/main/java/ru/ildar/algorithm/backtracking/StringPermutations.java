package ru.ildar.algorithm.backtracking;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class StringPermutations {

    private String[] permutations;
    private int idx;

    private void permute(String s, String chosen) {
        if (s.isEmpty()) {
            addPermutation(chosen);
        } else {
            for (int i = 0; i <= s.length() - 1; i++) {
                permute(deleteCharAt(s, i), chosen + s.charAt(i));
            }
        }
    }

    public void permute(String s) {
        permutations = new String[factorial(s.length())];
        idx = 0;

        permute(s, "");
    }

    public String[] getPermutations() {
        return permutations;
    }

    private void addPermutation(String permutation) {
        permutations[idx] = permutation;
        idx++;
    }

    private static int factorial(int number) {
        if (number <= 1) return 1;
        else return number * factorial(number - 1);
    }

    private static String deleteCharAt(String s, int idx) {
        StringBuilder sb = new StringBuilder(s);
        sb.deleteCharAt(idx);

        return sb.toString();
    }

}
