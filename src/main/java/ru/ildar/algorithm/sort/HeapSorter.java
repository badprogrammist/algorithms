package ru.ildar.algorithm.sort;

import ru.ildar.algorithm.datastructure.heap.Heap;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class HeapSorter implements Sorter {

    @Override
    public <E extends Comparable<E>> E[] sort(E[] a) {
        Heap<E> p = new Heap<>(a);
        for(int i = 0; i < a.length; i++) {
            a[i] = p.pollMin();
        }
        return a;
    }

}
