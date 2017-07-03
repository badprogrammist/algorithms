package ru.ildar.algorithm.datastructure

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class IntegerDictionaryTest extends Specification {

    def "Test of adding, searching, removing elements from dictionary"() {

        given: "The dictionary"
        IntegerDictionary dict = new IntegerDictionary()

        and: "Elements"
        int N = 50

        when:"Adding elements"
        for(int i = 0; i < N; i++) {
            dict.put(i)
        }

        then: "The dictionary should contains elements"
        isContainsAllElements(dict, N)

        when: "Removing elements"
        for(int i = 0; i < N; i++) {
            dict.remove(i)
        }

        then: "The dictionary shouldn't contains elements"
        isNotContainsAllElements(dict, N)

    }

    def isContainsAllElements(IntegerDictionary dict, int N) {
        for(int i = 0; i < N; i++) {
            if(!dict.search(i)) {
                return false
            }
        }
        return true
    }

    def isNotContainsAllElements(IntegerDictionary dict, int N) {
        for(int i = 0; i < N; i++) {
            if(dict.search(i)) {
                return false
            }
        }
        return true
    }

}
