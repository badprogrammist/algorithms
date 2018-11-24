package ru.ildar.algorithm.dynamic

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class MaxSumArcOfCircleTest extends Specification {

    def "Test of finding max sum an arc of a circle of numbers"() {
        when: "Trying to find max sum an arc of a circle of numbers"
        MaxSumArcOfCircle alg = new MaxSumArcOfCircle()
        alg.find(circle as int[])

        then: "The max sum should equal expected"
        alg.getMaxSum() == maxSum

        and: "The start index should equlas expected"
        alg.getStart() == start

        and: "The arc length should equlas expected"
        alg.getLength() == length

        where:
        circle                       | maxSum | start | length
        [1, -3, 5, -2, 4, -1, 2, -4] | 8      | 2     | 4
    }

}
