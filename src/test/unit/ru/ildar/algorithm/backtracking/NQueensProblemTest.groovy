package ru.ildar.algorithm.backtracking

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class NQueensProblemTest extends Specification {

    def "Test of solving n-queens problem via Backtracking"() {
        when: "Trying to solve n-queens problem via Backtracking"
        NQueensProblem alg = new NQueensProblem()
        alg.solve(n)

        then: "The amount of solutions should equlas expected"
        alg.getSolutions().size() == expectedQueens.size()

        and: "The found solutions should equals expected"
        for (int i = 0; i < alg.getSolutions().size(); i++) {
            int[] queens = alg.getSolutions().get(i)
            assert queens == expectedQueens[i] as int[]
        }

        where:
        n | expectedQueens
        4 | [[1, 3, 0, 2], [2, 0, 3, 1]]
//        8 | [5810...solutions ???]
    }

}
