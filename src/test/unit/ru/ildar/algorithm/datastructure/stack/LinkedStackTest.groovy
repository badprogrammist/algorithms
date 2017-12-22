package ru.ildar.algorithm.datastructure.stack

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class LinkedStackTest extends Specification {

    def "Test of adding and getting elements"() {
        given: "Stack of three elements"
        LinkedStack<String> stack = new LinkedStack()
        def e1 = '1'
        def e2 = '2'
        def e3 = '3'

        when: "Add new three elements"
        stack.push(e1)
        stack.push(e2)
        stack.push(e3)
        then: "Size should equals three"
        stack.size() == 3
        and: "The array of stack should equals expected"
        stack.toArray() == ['3', '2', '1'] as String[]

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

    def "Test of reversing stack"() {
        given: "Stack of five elements"
        Stack<Integer> stack = new LinkedStack<>()
        stack.push(1)
        stack.push(2)
        stack.push(3)
        stack.push(4)
        stack.push(5)

        when: "Reverse stack"
        stack.reverse()

        then: "The size of stack should equals expected"
        stack.size() == 5

        and: "The order of elements should equals expected"
        stack.pop() == 1
        stack.pop() == 2
        stack.pop() == 3
        stack.pop() == 4
        stack.pop() == 5
    }

}
