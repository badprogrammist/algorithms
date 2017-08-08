package ru.ildar.algorithm.sort;

import ru.ildar.algorithm.datastructure.pyramid.Pyramid;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class HeapSorter implements Sorter {

    @Override
    public <E extends Comparable<E>> E[] sort(E[] a) {
        Pyramid<E> p = new Pyramid<>(a);
        for(int i = 0; i < a.length; i++) {
            a[i] = p.pollMin();
        }
        return a;
    }

}
