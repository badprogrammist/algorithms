package ru.ildar.algorithm.backtracking

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class AllSubsetsTest extends Specification {

    def "Test of generating all subsets"() {
        when: "Trying to generate all subsets"
        AllSubsets alg = new AllSubsets()
        alg.construct(n)

        then: "The generated subsets should equal expected"
        for (int i = 0; i < alg.getSubsets().size(); i++) {
            int[] subset = alg.getSubsets().get(i)
            assert subset == expectedSubsets[i] as int[]
        }

        where:
        n | expectedSubsets
        1 | [[0], []]
        2 | [[0, 1], [0], [1], []]
        3 | [[0, 1, 2], [0, 1], [0, 2], [0], [1, 2], [1], [2], []]
    }

}
