package ru.ildar.algorithm.dynamic

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class PartitionProblemTest extends Specification {

    def "Test of finding the fairest way to divide up the sequence of numbers by dynamic programming approach"() {
        when: "Trying to find the partition"
        PartitionProblem alg = new PartitionProblem()
        alg.find(sequence as int[], partsNumber)

        then: "The result should equals expected result"
        alg.getSolution() == expected as int[]


        where:
        sequence                             | partsNumber | expected
        [8, 4, 2, 5, 8, 5, 2, 8, 5, 1, 5,
         7, 4, 3, 2, 4, 1, 3, 6, 3, 8, 2,
         5, 9, 3, 1, 4, 5, 7, 2, 5, 9, 4, 3] | 12          | [0, 3, 5, 7, 9, 12, 15, 19, 22, 24, 28, 31]
        [3, 4, 8, 1, 2, 4, 5, 2, 1, 6]       | 5           | [0, 2, 3, 6, 8]
        [1, 1, 1, 1, 1, 1, 1, 1, 1,]         | 3           | [0, 3, 6]
        [5, 2, 1, 6]                         | 2           | [0, 2]
    }

}
