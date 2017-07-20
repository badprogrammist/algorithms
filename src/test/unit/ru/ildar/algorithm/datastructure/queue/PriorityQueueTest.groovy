package ru.ildar.algorithm.datastructure.queue

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class PriorityQueueTest extends Specification {

    def "Test of adding and polling elements"() {
        given: "The instance of PriorityQueue"
        PriorityQueue<Double> queue = new TreePriorityQueue<>()

        when: "Add some elements"
        queue.add(4.67)
        queue.add(1.3)
        queue.add(-6.8)
        queue.add(1.8)
        queue.add(7.7)

        then: "Size of queue should equals expected"
        queue.size() == 5

        and: "A max element should equals expected"
        queue.getMax() == 7.7

        and: "A min element should equals expected"
        queue.getMin() == -6.8

        when: "Poll max element"
        double max = queue.pollMax()

        then: "Polled max element should equals expected"
        max == 7.7

        and: "Next max element should equals expected"
        queue.getMax() == 4.67

        and: "Size of queue should equals expected"
        queue.size() == 4

        when: "Poll min element"
        double min = queue.pollMin()

        then: "Polled min element should equals expected"
        min == -6.8

        and: "Next min element should equals expected"
        queue.getMin() == 1.3

        and: "Size of queue should equals expected"
        queue.size() == 3

        when: "Poll rest elements"
        double e1 = queue.pollMax()
        double e2 = queue.pollMin()
        double e3 = queue.pollMax()

        then: "Size of queue should equals expected"
        queue.size() == 0

        and: "Polled elements should equal expected"
        e1 == 4.67
        e2 == 1.3
        e3 == 1.8

        when: "Try to poll element"
        queue.pollMax()

        then: "An exception is thrown"
        thrown(NoSuchElementException.class)

    }


}
