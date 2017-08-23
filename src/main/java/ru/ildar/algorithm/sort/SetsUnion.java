package ru.ildar.algorithm.sort;

import ru.ildar.algorithm.datastructure.list.ArrayList;
import ru.ildar.algorithm.datastructure.list.List;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *
 * 4-9. Give an efficient algorithm to compute the union of sets A and B, where n=max(|A|,|B|).
 * The output should be an array of distinct elements that form the union of the sets, such that they appear more than once in the union.
 *   Assume that A and B are unsorted. Give an O(nlogn) algorithm for the problem.
 *   Assume that A and B are sorted. Give an O(n) algorithm for the problem.
 *
 */
public class SetsUnion {

    public static <E extends Comparable<E>> List<E> union(E[] s1, E[] s2) {
        QuickSorter sorter = new QuickSorter();
        E[] ss1 = sorter.sort(s1);
        E[] ss2 = sorter.sort(s2);
        int i = 0;
        int j = 0;
        ArrayList<E> union = new ArrayList<>(s1.length + s2.length);
        while(i < ss1.length && j < ss2.length) {
            if(ss1[i].compareTo(ss2[j]) == 0) {
                union.add(ss1[i]);
                i++;
                j++;
            } else if(ss1[i].compareTo(ss2[j]) < 0) {
                union.add(ss1[i]);
                i++;
            } else {
                union.add(ss2[j]);
                j++;
            }
        }
        while(i < ss1.length) {
            union.add(ss1[i]);
            i++;
        }
        while(j < ss2.length) {
            union.add(ss2[j]);
            j++;
        }
        return union;
    }

}
