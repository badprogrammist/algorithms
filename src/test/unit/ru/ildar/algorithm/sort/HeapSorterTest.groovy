package ru.ildar.algorithm.sort

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class HeapSorterTest extends Specification {

    def "Test of sorting array by heap-sort algorithm"() {
        given: "An array of elements"
        Integer[] elements = [4, 6, 1, 7, 5, 3, 8, 2]
        Integer[] sorted = [1, 2, 3, 4, 5, 6, 7, 8]

        and: "An instance of sorter"
        Sorter sorter = new HeapSorter()

        when: "Trying to sort elements"
        Integer[] r = sorter.sort(elements)

        then: "Resulted array should be sorted"
        isArraySorted(r, sorted)
    }


    boolean isArraySorted(Integer[] r, Integer[] s) {
        if(r.length != s.length) {
            return false;
        }
        for(int i = 0; i < r.length; i++) {
            if(r[i] != s[i]) {
                return false
            }
        }
        return true
    }

}
