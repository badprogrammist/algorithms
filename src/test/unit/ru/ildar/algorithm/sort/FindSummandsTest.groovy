package ru.ildar.algorithm.sort

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class FindSummandsTest extends Specification {

    def "Test of finding two elements whose sum is exactly x"() {
        given: "The sorted array"
        double[] s = [1.0, 2.0, 3.0, 4.5, 5, 6.5, 7]

        when: "Trying to find two elements"
        FindSummands.Summands summands = FindSummands.find(s, x)

        then: "Two elements should equals expected"
        summands.i == i
        summands.j == j

        where:
        x    | i | j
        9.5  | 2 | 5
        8.0  | 0 | 6
        5.0  | 1 | 2
        11.0 | 3 | 5

    }


}
