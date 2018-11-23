package ru.ildar.algorithm.dynamic;

import java.util.*;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *         <p>
 *         8-9. The knapsack problem is as follows: given a set of integers S={s1,s2,…,sn}, and a given target number T,
 *         find a subset of S that adds up exactly to T. For example, within S={1,2,5,9,10} there is a subset that adds up
 *         to T=22 but not T=23. Give a correct programming algorithm for knapsack that runs in O(nT) time
 */
public class SubsetSumProblem {

    private int[] set;
    private int sum;
    private boolean[][] addsUp;

    public void find(int[] set, int sum) {
        this.set = set;
        this.sum = sum;
        Arrays.sort(this.set);

        addsUp = new boolean[set.length + 1][sum + 1];
        for (int i = 0; i < set.length + 1; i++) {
            addsUp[i][0] = true;
        }
        for (int i = 1; i < sum + 1; i++) {
            addsUp[0][i] = false;
        }

        for (int subSum = 1; subSum <= sum; subSum++) {
            for (int setIdx = 1; setIdx <= set.length; setIdx++) {
                int elem = set[setIdx - 1];

                if (elem == subSum) {
                    addsUp[setIdx][subSum] = true;
                } else if (subSum < elem) {
                    addsUp[setIdx][subSum] = addsUp[setIdx - 1][subSum];
                } else {
                    addsUp[setIdx][subSum] = addsUp[setIdx - 1][subSum - elem];
                }

            }
        }
    }

    public boolean isAddUp() {
        return addsUp[set.length][sum];
    }

    public int[] getSubSet() {
        int[] subset = new int[set.length];

        if (isAddUp()) {
            int subSum = sum;
            int idx = 0;

            while (subSum != 0) {
                for (int setIdx = 1; setIdx <= set.length; setIdx++) {
                    if (addsUp[setIdx][subSum]) {
                        subset[idx] = set[setIdx - 1];
                        idx++;
                        subSum -= set[setIdx - 1];
                        break;
                    }
                }
            }
        }

        return subset;
    }

}
