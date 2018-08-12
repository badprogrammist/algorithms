package ru.ildar.algorithm.backtracking

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class StringPermutationsTest extends Specification {

    def "Test of generating all the string permutations"() {
        given: "Some string"

        when: "Try to generate all the string permutation"
        StringPermutations alg = new StringPermutations()
        alg.permute(str)

        then: "The set of the permutations should equal expected"
        alg.getPermutations() == expectedPermutations as String[]

        where:
        str   | expectedPermutations
        '123' | ['123', '132', '213', '231', '312', '321']
    }

}
