package ru.ildar.algorithm.dynamic

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class ChangeMakingProblemTest extends Specification {

    def "Test of greedy algorithm of making change of an amount of money"() {
        when: "Trying to make change"
        ChangeMakingProblem.GreedyAlgorithm alg = new ChangeMakingProblem.GreedyAlgorithm()
        int[] result = alg.makeChange(amount, coins as int[])

        then: "The number of each coin should equal expected"
        result == expected as int[]

        where:
        amount | coins         | expected
        12     | [10, 6, 1]    | [0, 2, 0]
        20     | [10, 6, 1]    | [2, 0, 0]
        24     | [10, 6, 1]    | [0, 4, 0]
        25     | [10, 6, 1]    | [0, 4, 1]
        27     | [10, 6, 1]    | [2, 1, 1]
        28     | [10, 6, 1]    | [2, 1, 2]
        56786  | [10, 6, 1]    | [5678, 1, 0]
        56786  | [10, 5, 2, 1] | [5678, 1, 0, 1]
        27     | [10, 5, 2, 1] | [2, 1, 1, 0]
    }

    def "Test of classic algorithm of making change of an amount of money"() {
        when: "Trying to make change"
        ChangeMakingProblem.ClassicAlgorithm alg = new ChangeMakingProblem.ClassicAlgorithm()
        int[] result = alg.makeChange(amount, coins as int[])

        then: "The number of each coin should equal expected"
        result == expected as int[]

        where:
        amount | coins         | expected
        12     | [10, 6, 1]    | [0, 2, 0]
        20     | [10, 6, 1]    | [2, 0, 0]
        24     | [10, 6, 1]    | [0, 4, 0]
        25     | [10, 6, 1]    | [0, 4, 1]
        27     | [10, 6, 1]    | [2, 1, 1]
        28     | [10, 6, 1]    | [1, 3, 0]
    }

}
