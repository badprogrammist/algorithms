package ru.ildar.algorithm.dynamic

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class BinomialCoefficientsTest extends Specification {

    def "Test of compute Binomial Coefficients via loop algorithm"() {
        when: "Trying to Binomial Coefficients"
        BinomialCoefficients alg = new BinomialCoefficients()

        then: "The result should equal expected"
        alg.compute(n, m) == expectedResult

        where:
        n | m | expectedResult
        0 | 0 | 1
        1 | 0 | 1
        1 | 1 | 1
        2 | 0 | 1
        2 | 1 | 2
        2 | 2 | 1
        3 | 2 | 3
        5 | 2 | 10
        5 | 3 | 10
        4 | 3 | 4
    }

}
