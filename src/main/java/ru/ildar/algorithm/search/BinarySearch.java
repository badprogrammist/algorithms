package ru.ildar.algorithm.search;


/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class BinarySearch {

    public <E extends Comparable<E>> int search(E[] a, E e) {
        return search(a, e, 0, a.length - 1);
    }

    private <E extends Comparable<E>> int search(E[] a, E e, int l, int r) {
        if(l > r) {
            return -1;
        }
        int m = (l + r) / 2;
        if(a[m].compareTo(e) == 0) {
            return m;
        } else if(a[m].compareTo(e) > 0) {
            return search(a, e, l, m - 1);
        } else {
            return search(a, e, m + 1, r);
        }
    }

}
