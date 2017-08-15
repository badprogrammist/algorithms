package ru.ildar.algorithm.search

import ru.ildar.algorithm.sort.HeapSorter
import ru.ildar.algorithm.sort.Sorter
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class BinarySearchTest extends Specification {

    def "Test of binary search algorithm"() {
        given: "An sorted array of elements"
        Integer[] a = [4, 7, 9, 34, 67, 22, 54, 73, 345, 6, 32]
        Sorter sorter = new HeapSorter()
        Integer[] s = sorter.sort(a) //[4, 6, 7, 9, 22, 32, 34, 54, 67, 73, 345]

        and:
        BinarySearch searcher = new BinarySearch();

        when: "Trying to find elements"
        int result = searcher.search(s, e)

        then: "Found elements should be equal expected"
        result == exp

        where:
        e   | exp
        34  | 6
        7   | 2
        73  | 9
        345 | 10
        4   | 0
    }

}
