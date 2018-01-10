package ru.ildar.algorithm.sort.tasks

import ru.ildar.algorithm.sort.tasks.SumOfKNumbers
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class SumOfKNumbersTest extends Specification {

    def "Test of algorithm that checks whether k of the integers in S add up to T"() {
        given: "The array of numbers S, count of summands k and sum T"

        when: "Trying to decide whether k of the integers in S add up to T"
        boolean result = SumOfKNumbers.isExistSumOfK(s, k, t)

        then: "The result should equals expected"
        result == expected

        where:
        s                                  | k | t  | expected
        [4, 5, 7, 2, 1, 3, 9] as Integer[] | 3 | 18 | true
    }

}
