package ru.ildar.algorithm.sort.tasks;

import ru.ildar.algorithm.sort.QuickSorter;
import ru.ildar.algorithm.sort.tasks.FindSummands;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *
 *  Given a set S of n integers and an integer T, give an O(n^(k−1)logn) algorithm to test whether k of the integers in S add up to T.
 */
public class SumOfKNumbers {

    public static boolean isExistSumOfK(Integer[] a, int k, int t) {
        QuickSorter sorter = new QuickSorter();
        Integer[] s = sorter.sort(a);
        return checkExistSumOfK(s, k, t);
    }

    private static boolean checkExistSumOfK(Integer[] s, int k, int t) {
        if (k <= 2) {
            double[] d = new double[s.length];
            for (int i = 0; i < s.length; i++) {
                d[i] = new Double(s[i]);
            }
            FindSummands.Summands sum = FindSummands.find(d, t);
            return sum != FindSummands.Summands.NOT_FOUND;
        } else {
            Integer[] a = new Integer[s.length - 1];
            for (int i = 0; i < s.length; i++) {
                int z = 0;
                for (int j = 0; j < s.length; j++) {
                    if (i != j) {
                        a[z] = s[j];
                        z++;
                    }
                }
                // If S[i] is included in the k integers that sum up to T, then there must
                // exactly (k - 1) integers in the rest of S (i.e., A) that sum to (T - S[i]).
                // We can find that out by calling ourselves recursively.
                if (checkExistSumOfK(a, z - 1, t - s[i])) {
                    return true;
                }
            }
            return false;
        }
    }

}
