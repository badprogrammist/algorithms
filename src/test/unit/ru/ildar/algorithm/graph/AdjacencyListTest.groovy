package ru.ildar.algorithm.graph

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class AdjacencyListTest extends Specification {

    def "Test of represent graph via Adjacency list"() {
        given: "Some undirected graph"
        def g = GraphBuilder.adjacencyList(false)
                .edge(0, 1)
                .edge(0, 5)
                .edge(0, 4)
                .edge(1, 2)
                .edge(1, 4)
                .edge(2, 3)
                .edge(3, 4)
                .create()

        when: "Getting information about degree of vertices"
        def degree0 = g.getDegree(0)
        def degree1 = g.getDegree(1)
        def degree2 = g.getDegree(2)
        def degree3 = g.getDegree(3)
        def degree4 = g.getDegree(4)
        def degree5 = g.getDegree(5)

        then: "The degree of vertices should equals expected"
        degree0 == 3
        degree1 == 3
        degree2 == 2
        degree3 == 2
        degree4 == 3
        degree5 == 1

        when: "Getting adjacency edges"
        def ae0 = g.getAdjacentEdges(0)
        def ae1 = g.getAdjacentEdges(1)
        def ae2 = g.getAdjacentEdges(2)
        def ae3 = g.getAdjacentEdges(3)
        def ae4 = g.getAdjacentEdges(4)
        def ae5 = g.getAdjacentEdges(5)

        then: "The adjacency edges should equals expected"

        isArraysEquals([4, 5, 1] as int[], ae0)
        isArraysEquals([4, 2, 0] as int[], ae1)
        isArraysEquals([3, 1] as int[], ae2)
        isArraysEquals([4, 2] as int[], ae3)
        isArraysEquals([3, 1, 0] as int[], ae4)
        isArraysEquals([0] as int[], ae5)

        when: "Getting adjacency edges iterator"
        def aei0 = g.getAdjacentEdgesIterator(0)
        def aei1 = g.getAdjacentEdgesIterator(1)
        def aei2 = g.getAdjacentEdgesIterator(2)
        def aei3 = g.getAdjacentEdgesIterator(3)
        def aei4 = g.getAdjacentEdgesIterator(4)
        def aei5 = g.getAdjacentEdgesIterator(5)

        then:
        checkAdjacentEdgesIterator(ae0, aei0)
        checkAdjacentEdgesIterator(ae1, aei1)
        checkAdjacentEdgesIterator(ae2, aei2)
        checkAdjacentEdgesIterator(ae3, aei3)
        checkAdjacentEdgesIterator(ae4, aei4)
        checkAdjacentEdgesIterator(ae5, aei5)
    }

    boolean isArraysEquals(int[] a1, int[] a2) {
        if (a1.length != a2.length) {
            return false
        }
        for (int i1 : a1) {
            boolean contains = false
            for (int i2 : a2) {
                if (i1 == i2) {
                    contains = true
                    break
                }
            }
            if (!contains) {
                return false
            }
        }
        return true
    }

    boolean checkAdjacentEdgesIterator(int[] ae, Iterator<Integer> i) {
        int idx = 0
        while (i.hasNext()) {
            if (ae[idx] != i.next()) {
                return false
            }
            idx++
        }
        return true
    }

}
