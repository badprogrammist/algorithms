package ru.ildar.algorithm.datastructure.queue

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class LinkedQueueTest extends Specification {

    def "Test of adding and getting elements"() {
        given: "Three elements"
        def e1 = 1
        def e2 = 2
        def e3 = 3

        and: "The queue"
        Queue<Integer> queue = new LinkedQueue<>()

        when: "Add new three elements"
        queue.add(e1)
        queue.add(e2)
        queue.add(e3)
        then: "Size should equals three"
        queue.size() == 3

        when: "Poll element"
        def first = queue.poll()
        then: "Polled element should be equaled first element"
        first == e1
        and: "Size should equals two"
        queue.size() == 2

        when: "Poll element"
        def second = queue.poll()
        then: "Polled element should be equaled second element"
        second == e2
        and: "Size should equals one"
        queue.size() == 1

        when: "Poll element"
        def third = queue.poll()
        then: "Polled element should be equaled third element"
        third == e3
        and: "Size should equals zero"
        queue.size() == 0

        when: "Try get last element"
        queue.poll()
        then: "NoSuchElementException is thrown"
        thrown(NoSuchElementException.class)

    }

}
