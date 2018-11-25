package ru.ildar.algorithm.dynamic

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class DataCompressionTest extends Specification {

    def "Test of encoding data string using as few text strings as possible via backtracking approach"() {
        when: "Trying to encode data string using as few text strings as possible via backtracking approach"
        DataСompression.BacktrackingApproach alg = new DataСompression.BacktrackingApproach()
        alg.encode(data.toCharArray(), compressionTable as char[][])

        then: "The archive should equal expected"
        alg.getArchive() == expectedArchive.toCharArray()

        where:
        data           | compressionTable                                 | expectedArchive
        "bababbaababa" | [["a"], ["b", "a"], ["a", "b", "a", "b"], ["b"]] | "32120"
    }

}
