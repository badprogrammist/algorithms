package ru.ildar.algorithm.sort

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class InsertionSorterTest extends Specification {

    def "Test of sorting array by insertion-sort algorithm"() {
        given: "An array of elements"
        Integer[] elements = [4, 6, 1, 7, 5, 3, 8, 2]

        and: "An instance of sorter"
        Sorter sorter = new InsertionSorter()

        when: "Trying to sort elements"
        int[] r = sorter.sort(elements)

        then: "Resulted array should be sorted"
        isArraySorted(r)
    }


    boolean isArraySorted(int[] a) {
        for(int i = 1; i < a.length; i++) {
            if(a[i - 1] > a[i]) {
                return false
            }
        }
        return true
    }

}
