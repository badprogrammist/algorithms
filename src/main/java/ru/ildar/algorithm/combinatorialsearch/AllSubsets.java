package ru.ildar.algorithm.combinatorialsearch;

import ru.ildar.algorithm.datastructure.list.ArrayList;
import ru.ildar.algorithm.datastructure.list.List;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class AllSubsets {

    private List<Integer[]> subsets = new ArrayList<>();
    private int n;

    public void construct(int n) {
        this.n = n;
        this.subsets = new ArrayList<>();
        boolean[] set = new boolean[n];

        construct(set, 0);
    }

    private void construct(boolean[] set, int k) {
        if (k == n) {
            addSubset(set);
        } else {
            set[k] = true;
            construct(set, k + 1);

            set[k] = false;
            construct(set, k + 1);
        }

    }

    private void addSubset(boolean[] set) {
        List<Integer> subset = new ArrayList<>();

        for (int i = 0; i < set.length; i++) {
            if (set[i]) {
                subset.add(i);
            }
        }

        subsets.add(subset.toArray());
    }

    public List<Integer[]> getSubsets() {
        return this.subsets;
    }

}
