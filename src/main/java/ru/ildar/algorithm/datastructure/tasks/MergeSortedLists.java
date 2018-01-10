package ru.ildar.algorithm.datastructure.tasks;


import ru.ildar.algorithm.datastructure.heap.Heap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *
 * Give an O(nlogk)-time algorithm that merges k sorted lists with a total of n elements into one sorted list.
 * (Hint: use a heap to speed up the elementary O(kn)-time algorithm).
 *
 * Scan through all k lists in any order and use the stream of elements to build a heap of k elements.
 * Since bubble_down works in O(logk) for a heap of k elements, we thus solve the problem in O(nlogk).
 *
 * The elementary algorithm compares the heads of each of the k sorted lists to find the minimum element,
 * puts this in the sorted list and repeats. The total time is O(kn). Suppose instead that we build a heap on
 * the head elements of each of the k lists, with each element labeled as to which list it is from.
 * The minimum element can be found and deleted in O(logk) time. Further, we can insert the new head of this list
 * in the heap in O(logk) time. An alternate O(nlogk) approach would be to merge the lists from as in mergesort,
 * using a binary tree on k leaves (one for each list). problem
 *
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

        Heap<E> heap = new Heap<>(n);
        for (int k = 0; k < lists.length; k++) {
            E e = lists[k][0];
            heads.put(e, k);
            heap.add(e);
        }

        for (int i = 0; i < n; i++) {
            Comparable min = heap.pollMin();
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
                heap.add(e);
                seeks[newListIndex] = seeks[newListIndex] + 1;
            }
        }


        return (E[])result;
    }


}
