package ru.ildar.algorithm.dynamic

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class LevenshteinDistanceMemorizedRecursiveTest extends Specification {

    def "Test of calculation editing distance between two strings by naive recursive algorithm"() {
        when: "Trying to calculate editing distance"
        LevenshteinDistanceMemorizedRecursive alg = new LevenshteinDistanceMemorizedRecursive()
        alg.compare(s1, s2)

        then: "The distance should equal expected"
        alg.getDistance() == expectedResult

        where:
        s1                           | s2                          | expectedResult
        "shot"                       | "spot"                      | 1
        "ago"                        | "agog"                      | 1
        "hour"                       | "our"                       | 1
        "sunday"                     | "saturday"                  | 3
        "kitten"                     | "sitting"                   | 3
        "svopt"                      | "shot"                      | 2
        "wegwgererherrgegrthwefvsjk" | "wegwerfdfewfwerhytkythrtg" | 18
    }

}
