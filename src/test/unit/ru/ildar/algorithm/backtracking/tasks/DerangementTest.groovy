package ru.ildar.algorithm.backtracking.tasks

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class DerangementTest extends Specification {

    def "Test of derangement algorithm"() {
        given: "The implementation of algorithm"
        Derangement alg = new Derangement()

        when: "Trying to derange the sequence of numbers"
        alg.derange(items as int[])

        then: "The the sequence of numbers should be deranged"
        int[] deranged = alg.getDeranged()
        for (int i = 0; i < items.size(); i++) {
            assert deranged[i] != items[i]
        }

        where:
        items           | _
        [1, 2, 3, 4, 5] | _
    }

}
