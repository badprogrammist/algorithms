package ru.ildar.algorithm.datastructure.tasks

import ru.ildar.algorithm.datastructure.tasks.MergeSortedLists
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class MergeSortedListsTest extends Specification {

    Integer[][] lists = [
            [2, 4, 5],
            [8, 9, 12, 56],
            [6, 7, 13],
            [45, 67]
    ]

    Integer[] expected = [2, 4, 5, 6, 7, 8, 9, 12, 13, 45, 56, 67]

    def "Test of merging k sorted lists by brute-force algorithm"() {
        given: "The k sorted lists"

        when: "Trying to merge lists"
        Integer[] result = MergeSortedLists.bruteForce(lists)

        then: "Result should equals expected"
        expected == result

    }

    def "Test of merging k sorted lists by pyramid algorithm"() {
        given: "The k sorted lists"

        when: "Trying to merge lists"
        Integer[] result = MergeSortedLists.usingPyramid(lists)

        then: "Result should equals expected"
        expected == result

    }

}
