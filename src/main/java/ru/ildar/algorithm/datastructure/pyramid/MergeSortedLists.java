package ru.ildar.algorithm.datastructure.pyramid;


import java.util.HashMap;
import java.util.Map;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *
 * Give an O(nlogk)-time algorithm that merges k sorted lists with a total of n elements into one sorted list.
 * (Hint: use a heap to speed up the elementary O(kn)-time algorithm).
 *
 */
public class MergeSortedLists {

    public static <E extends Comparable<E>> E[] bruteForce(E[][] lists) {
        int n = 0;
        for (E[] list : lists) {
            n += list.length;
        }

        Comparable[] result = new Comparable[n];

        int[] seeks = new int[lists.length];
        for (int k = 0; k < lists.length; k++) {
            seeks[k] = 0;
        }

        for (int i = 0; i < n; i++) {
            E min = null;
            int minListIndex = 0;

            for (int k = 0; k < lists.length; k++) {
                if (seeks[k] < lists[k].length) {
                    E e = lists[k][seeks[k]];
                    if (min == null || min.compareTo(e) > 0) {
                        min = e;
                        minListIndex = k;
                    }
                }

            }
            result[i] = min;
            seeks[minListIndex] = seeks[minListIndex] + 1; // May to exceed the length of list

        }

        return (E[])result;
    }

    public static <E extends Comparable<E>> E[] usingPyramid(E[][] lists) {
        int n = 0;
        for (E[] list : lists) {
            n += list.length;
        }

        Comparable[] result = new Comparable[n];


        int[] seeks = new int[lists.length];
        for (int k = 0; k < lists.length; k++) {
            seeks[k] = 1;
        }

        Map<Comparable, Integer> heads = new HashMap<>();

        Pyramid<E> pyramid = new Pyramid<>(n);
        for (int k = 0; k < lists.length; k++) {
            E e = lists[k][0];
            heads.put(e, k);
            pyramid.add(e);
        }

        for (int i = 0; i < n; i++) {
            Comparable min = pyramid.pollMin();
            result[i] = min;

            int listIndex = heads.get(min);
            heads.remove(min);

            E e = null;
            int newListIndex = 0;
            if (seeks[listIndex] < lists[listIndex].length) {
                e = lists[listIndex][seeks[listIndex]];
                newListIndex = listIndex;
            } else {
                for (int k = 0; k < lists.length; k++) {
                    if (seeks[k] < lists[k].length) {
                        e = lists[k][seeks[k]];
                        newListIndex = k;
                        break;
                    }
                }
            }
            if (e != null) {
                heads.put(e, newListIndex);
                pyramid.add(e);
                seeks[newListIndex] = seeks[newListIndex] + 1;
            }
        }


        return (E[])result;
    }


}
