package ru.ildar.algorithm.backtracking.tasks;

import java.util.Arrays;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class Derangement {

    private int[] deranged;
    private boolean finished;

    public void derange(int[] items) {
        finished = false;
        deranged = new int[items.length];
        derange(items, 0);
    }

    private void derange(int[] items, int k) {
        if (finished) {
            return;
        }

        if (k == items.length - 1) {
            System.arraycopy(items, 0, deranged, 0, items.length);
            finished = true;
        } else {
            for (int i = k + 1; i < items.length; i++) {
                    swap(items, i, k);
                    derange(items, k + 1);
                    swap(items, i, k);
            }
        }
    }

    private void swap(int[] items, int i, int j) {
        int item = items[i];
        items[i] = items[j];
        items[j] = item;
    }

    public int[] getDeranged() {
        return deranged;
    }

}
