package ru.ildar.algorithm.backtracking

import ru.ildar.algorithm.backtracking.PartitionProblem
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class PartitionProblemTest extends Specification {

    def "Test of finding the fairest way to divide up the sequence of numbers"() {
        when: "Trying to find the partition"
        PartitionProblem alg = new PartitionProblem()
        alg.find(sequence as int[], partsNumber)

        then:
        true

        where:
        sequence                       | partsNumber
        [3, 4, 8, 1, 2, 4, 5, 2, 1, 6] | 5
    }

}
