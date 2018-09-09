package ru.ildar.algorithm.dynamic

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class FibonacciMemorizedLoopTest extends Specification {

    def "Test of compute Fibonacci numbers via memorized loop algorithm"() {
        when: "Trying to Fibonacci numbers"
        FibonacciMemorizedLoop alg = new FibonacciMemorizedLoop()

        then: "The result should equal expected"
        alg.compute(n) == expectedResult

        where:
        n  | expectedResult
        0  | 0
        1  | 1
        6  | 8
        15 | 610
        50 | 12586269025
    }

}
