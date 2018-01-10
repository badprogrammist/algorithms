package ru.ildar.algorithm.datastructure.tasks

import ru.ildar.algorithm.datastructure.tasks.IntegerDictionary
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class IntegerDictionaryTest extends Specification {

    def "Test of adding, searching, removing elements from dictionary"() {

        given: "The dictionary"
        IntegerDictionary dict = new IntegerDictionary(591)
        and: "Elements"
        int[] elements = [3, 35, 89, 1, 0, 590, 34, 90, 14]

        when:"Adding elements"
        for(int i = 0; i < elements.length; i++) {
            dict.put(elements[i])
        }

        then: "The dictionary should contains elements"
        isContainsAllElements(dict, elements)

        when: "Try to get ordered elements"
        def result = dict.values()

        then: "Elements are in order as expected"
        result == [0, 1, 3, 14, 34, 35, 89, 90, 590] as int[]

        when: "Removing elements"
        for(int i = 0; i < elements.length; i++) {
            dict.remove(elements[i])
        }

        then: "The dictionary shouldn't contains elements"
        isNotContainsAllElements(dict, elements)

    }

    def isContainsAllElements(IntegerDictionary dict, int[] elements) {
        for(int i = 0; i < elements.length; i++) {
            if(!dict.search(elements[i])) {
                return false
            }
        }
        return true
    }

    def isNotContainsAllElements(IntegerDictionary dict, int[] elements) {
        for(int i = 0; i < elements.length; i++) {
            if(dict.search(elements[i])) {
                return false
            }
        }
        return true
    }

}
