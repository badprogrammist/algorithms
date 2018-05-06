package ru.ildar.algorithm.datastructure.list

import ru.ildar.algorithm.datastructure.Iterator
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class LinkedListTest extends Specification {

    def "Test of adding and removing elements"() {
        given: "An instance of LinkedList"
        LinkedList list = new LinkedList()

        and: "three elements to adding"
        def e1 = '1'
        def e2 = '2'
        def e3 = '3'

        when: "Add three element"
        list.add(e1)
        list.add(e2)
        list.add(e3)

        then: "Size of line should be equaled three"
        list.size() == 3

        and: "Indexes of elements should be equaled to adding order"
        list.indexOf(e1) == 0
        list.indexOf(e2) == 1
        list.indexOf(e3) == 2

        and: "Check of getting elements by index"
        list.get(0) == e1
        list.get(1) == e2
        list.get(2) == e3

        when: "Remove first elements"
        list.remove(0)

        then: "Size of line should be equaled two"
        list.size() == 2

        and: "Check elements indexes"
        list.indexOf(e2) == 0
        list.indexOf(e3) == 1

        and: "Check of getting elements by index"
        list.get(0) == e2
        list.get(1) == e3

        when: "Set element in definite position"
        list.set('0', 0)

        then: "Element should be in that position"
        list.get(0) == '0'

    }

    def "Test of list iterator"() {
        given: "An instance of LinkedList"
        LinkedList list = new LinkedList()

        and: "three elements to adding"
        def e1 = '1'
        def e2 = '2'
        def e3 = '3'

        and: "Add three element"
        list.add(e1)
        list.add(e2)
        list.add(e3)

        when: "Get iterator"
        Iterator iter = list.iterator()

        then: "Traversing through list by iterator should be correct"
        iter.hasNext()
        iter.next() == '1'
        iter.hasNext()
        iter.next() == '2'
        iter.hasNext()
        iter.next() == '3'

    }

}
