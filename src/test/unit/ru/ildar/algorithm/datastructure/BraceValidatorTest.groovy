package ru.ildar.algorithm.datastructure

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class BraceValidatorTest extends Specification {

    def "Test of finding invalid brace"() {
        when: "Try to find invalid brace"
        def index = BraceValidator.validate(str)
        then: "Index should be equaled expected"
        index == expected
        where:
        str       | expected
        '((()))'  | -1
        '((())))' | 6
        ')((()))' | 0
        '(()(())' | 0

    }


}
