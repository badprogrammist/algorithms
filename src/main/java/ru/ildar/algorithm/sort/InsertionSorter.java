package ru.ildar.algorithm.sort;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class InsertionSorter implements Sorter {

    @Override
    public <E extends Comparable<E>> E[] sort(E[] a) {
        if(a.length > 1) {
            for(int j = 1; j < a.length; j++) {
                E key = a[j];
                int i = j - 1;
                while(i >= 0 && a[i].compareTo(key) > 0) {
                    a[i + 1] = a[i];
                    i--;
                }
                a[i + 1] = key;
            }
        }
        return a;
    }

}
