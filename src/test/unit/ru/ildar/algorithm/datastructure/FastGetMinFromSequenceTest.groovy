package ru.ildar.algorithm.datastructure

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class FastGetMinFromSequenceTest extends Specification {

    def "Test of cubic spaced algorithm for finding min element from sequence"() {
        given: "A sequence of elements"
        int[] elements = [6, 1, 3, 8, 9, 4, 5]

        and: "An instance of class of algorithm"
        FastGetMinFromSequence.GetMinFromSequence finder = FastGetMinFromSequence.cubicSpace(elements)

        when: "Trying to find min element from sub-sequence"
        int min = finder.getMin(i, j)

        then: "The min elements should equals expected"
        min == expected

        where:
        i | j | expected
        3 | 6 | 4
        1 | 6 | 1
        3 | 5 | 4
        0 | 4 | 1
    }

    def "Test of N space algorithm for finding min element from sequence"() {
        given: "A sequence of elements"
        int[] elements = [6, 1, 3, 8, 9, 4, 5]

        and: "An instance of class of algorithm"
        FastGetMinFromSequence.GetMinFromSequence finder = FastGetMinFromSequence.nSpace(elements)

        when: "Trying to find min element from sub-sequence"
        int min = finder.getMin(i, j)

        then: "The min elements should equals expected"
        min == expected

        where:
        i | j | expected
        3 | 6 | 4
        1 | 6 | 1
        3 | 5 | 4
        0 | 4 | 1
        1 | 5 | 1
    }

}
