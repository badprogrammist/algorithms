package ru.ildar.algorithm.dynamic

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class SubSetProblemTest extends Specification {

    def "Test of finding a subset that adds up to sum"() {
        when: "Trying to find a subset that adds up to sum"
        SubsetSumProblem alg = new SubsetSumProblem()
        alg.find(set as int[], sum)

        then: "The result should equals expected"
        alg.isAddUp() == isAddUp
        alg.getSubSet() == expectedSubset as int[]

        where:
        set                                             | sum | expectedSubset                             | isAddUp
        [2, 3, 5, 6]                                    | 11  | [6, 3, 2, 0]                               | true
        [2, 3, 5, 6]                                    | 12  | [0, 0, 0, 0]                               | false
        [1, 2, 5, 9, 10]                                | 22  | [10, 9, 2, 1, 0]                           | true
        [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14] | 31  | [8, 7, 6, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0] | true
    }

}
