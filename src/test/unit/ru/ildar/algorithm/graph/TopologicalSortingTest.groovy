package ru.ildar.algorithm.graph

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class TopologicalSortingTest extends Specification {

    def "Test topological sorting algorithm"() {
        given: "Some directed graph"
        GraphBuilder gb = GraphBuilder.adjacencyList(true)
        edges.each { edge -> gb.edge(edge[0], edge[1]) }
        Graph graph = gb.create()

        when: "Trying to apply topological sorting"
        TopologicalSorting sorter = new TopologicalSorting()
        sorter.sort(graph)

        then: "The order of vertices should equals expected"
        sorter.getSortedVertices().toArray() == expectedOrder

        where:
        edges                                                                    | expectedOrder
        [[0, 1], [0, 2], [1, 3], [2, 4], [2, 5], [4, 3], [5, 4], [6, 0], [6, 5]] | [6, 0, 1, 2, 5, 4, 3] as int[]
        [[0, 1], [0, 2], [2, 5], [1, 3], [1, 4], [5, 6], [4, 6]]                 | [0, 1, 3, 4, 2, 5, 6] as int[]
    }

    def "Should return error because of cyclic graph"() {
        given: "Some cyclic directed graph"
        GraphBuilder gb = GraphBuilder.adjacencyList(true)
        edges.each { edge -> gb.edge(edge[0], edge[1]) }
        Graph graph = gb.create()

        when: "Trying to apply topological sorting"
        TopologicalSorting sorter = new TopologicalSorting()
        sorter.sort(graph)

        then: "An exception should be thrown"
        thrown(Error.class)

        where:
        edges                    | _
        [[0, 1], [1, 2], [2, 1]] | _
    }

}
