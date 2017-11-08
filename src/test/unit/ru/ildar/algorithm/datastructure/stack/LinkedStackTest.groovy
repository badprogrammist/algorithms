package ru.ildar.algorithm.datastructure.stack

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class LinkedStackTest extends Specification {

    LinkedStack<String> stack

    def setup() {
        stack = new LinkedStack()
    }

    def "Test of adding and getting elements"() {
        given: "Three elements"
        def e1 = '1'
        def e2 = '2'
        def e3 = '3'

        when: "Add new three elements"
        stack.push(e1)
        stack.push(e2)
        stack.push(e3)
        then: "Size should equals three"
        stack.size() == 3

        when: "Poll last element"
        def third = stack.pop()
        then: "Last element should be equaled third element"
        third == e3
        and: "Size should equals two"
        stack.size() == 2

        when: "Poll last element"
        def second = stack.pop()
        then: "Last element should be equaled second element"
        second == e2
        and: "Size should equals one"
        stack.size() == 1

        when: "Poll last element"
        def first = stack.pop()
        then: "Last element should be equaled first element"
        first == e1
        and: "Size should equals zero"
        stack.size() == 0

        when: "Try get last element"
        stack.pop()
        then: "NoSuchElementException is thrown"
        thrown(NoSuchElementException.class)

    }

}
