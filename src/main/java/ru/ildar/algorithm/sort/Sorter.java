package ru.ildar.algorithm.sort;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public interface Sorter {

    /**
     *
     * @param a - an array which have to be sorted
     * @param <E> - type of array element
     * @return - return the array in ascending order
     */
    <E extends Comparable<E>> E[] sort(E[] a);

}
