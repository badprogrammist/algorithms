package ru.ildar.algorithm.dynamic

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class FibonacciNaiveRecursionTest extends Specification {

    def "Test of compute Fibonacci numbers via naive recursive algorithm"() {
        when: "Trying to Fibonacci numbers"
        FibonacciNaiveRecursion alg = new FibonacciNaiveRecursion()

        then: "The result should equal expected"
        alg.compute(n) == expectedResult

        where:
        n  | expectedResult
        0  | 0
        1  | 1
        6  | 8
        15 | 610
    }

}
