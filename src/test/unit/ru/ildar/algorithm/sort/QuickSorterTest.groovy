package ru.ildar.algorithm.sort

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class QuickSorterTest extends Specification {

    def "Test of sorting array by quick-sort algorithm"() {
        given: "An instance of sorter"
        Sorter sorter = new QuickSorter()

        when: "Trying to sort elements"
        Integer[] r = sorter.sort(elements as Integer[])

        then: "Resulted array should be sorted"
        isArraySorted(r, expected as Integer[])

        where:
        elements                                  | expected
        [4, 7, 9, 34, 67, 22, 54, 73, 345, 6, 32] | [4, 6, 7, 9, 22, 32, 34, 54, 67, 73, 345]
        [4, 6, 1, 7, 5, 3, 8, 2]                  | [1, 2, 3, 4, 5, 6, 7, 8]
    }


    boolean isArraySorted(Integer[] r, Integer[] s) {
        if (r.length != s.length) {
            return false;
        }
        for (int i = 0; i < r.length; i++) {
            if (r[i] != s[i]) {
                return false
            }
        }
        return true
    }

}
