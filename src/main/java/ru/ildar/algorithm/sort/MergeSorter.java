package ru.ildar.algorithm.sort;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class MergeSorter implements Sorter {

    @Override
    public <E extends Comparable<E>> E[] sort(E[] a) {
        sort(a, 0, a.length - 1);
        return a;
    }

    private <E extends Comparable<E>> void sort(E[] a, int l, int r) {
        if(l < r) {
            int m = (l + r) / 2;
            sort(a, l, m);
            sort(a, m + 1, r);
            merge(a, l, m, r);
        }
    }

    private <E extends Comparable<E>> void merge(E[] a, int l, int m, int r) {
        int lenLeft = (m - l) + 1;
        int lenRight = r - m;

        int leftIndex = 0;
        int rightIndex = 0;

        Comparable[] leftArray = new Comparable[lenLeft];
        Comparable[] rightArray = new Comparable[lenRight];
        for(int i = l; i <= m; i++) {
            leftArray[leftIndex] =  a[i];
            leftIndex++;
        }
        for(int i = m + 1; i <= r; i++) {
            rightArray[rightIndex] = a[i];
            rightIndex++;
        }

        leftIndex = 0;
        rightIndex = 0;
        int index = l;

        while(leftIndex < leftArray.length || rightIndex < rightArray.length) {
            if(leftIndex == leftArray.length && rightIndex == rightArray.length - 1) {
                a[index] = (E) rightArray[rightIndex];
                rightIndex++;
            } else if(leftIndex == leftArray.length - 1 && rightIndex == rightArray.length) {
                a[index] = (E) leftArray[leftIndex];
                leftIndex++;
            } else if(leftArray[leftIndex].compareTo(rightArray[rightIndex]) <= 0) {
                a[index] = (E) leftArray[leftIndex];
                leftIndex++;
            } else {
                a[index] = (E) rightArray[rightIndex];
                rightIndex++;
            }

            index++;
        }
    }


}
