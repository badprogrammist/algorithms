package ru.ildar.algorithm.datastructure.heap

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class HeapTest extends Specification {

    def "Test of adding and getting element from pyramid"() {
        given: "An array of elements"
        int[] elements = [1945, 1804, 1941, 1865, 1918, 2001, 1963, 1783, 1776, 1492]

        and: "Pyramid structure"
        Heap<Integer> p = new Heap<>()

        when: "Add elements"
        elements.each { p.add(it) }

        then: "The size of the heap should be equal 10"
        p.size() == 10

        when: "Polling min elements to array"
        int[] min = new int[10]
        min[0] = p.pollMin()
        min[1] = p.pollMin()
        min[2] = p.pollMin()
        min[3] = p.pollMin()
        min[4] = p.pollMin()
        min[5] = p.pollMin()
        min[6] = p.pollMin()
        min[7] = p.pollMin()
        min[8] = p.pollMin()
        min[9] = p.pollMin()

        then: "The size of the heap should be equal 0"
        p.size() == 0

        and: "The array of min elements in ascending order"
        inAscendingOrder(min)
    }

    boolean inAscendingOrder(int[] a) {
        for(int i = 0; i < a.length - 1; i++) {
            if(a[i] > a[i + 1]){
                return false
            }
        }
        return true
    }

}
