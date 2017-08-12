package ru.ildar.algorithm.sort;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class QuickSorter implements Sorter {

    @Override
    public <E extends Comparable<E>> E[] sort(E[] a) {
        sort(a, 0, a.length - 1);
        return a;
    }

    private <E extends Comparable<E>> void sort(E[] a, int l, int r) {
        if(l < r) {
            int q = partition(a, l, r);
            sort(a, l, q -1);
            sort(a, q + 1, r);
        }
    }

    private <E extends Comparable<E>> int partition(E[] a, int l, int r) {
        E x = a[r];
        int i = l - 1;
        for(int j = l; j < r -1; j++) {
            if(a[j].compareTo(x) <= 0) {
                i++;
                swap(a, i, j);
            }
        }
        swap(a, i + 1, r);
        return i + 1;
    }

    private <E extends Comparable<E>> void swap(E[] a, int i, int j) {
        if(i < a.length && j < a.length) {
            E e = a[i];
            a[i] = a[j];
            a[j] = e;
        }
    }

}
