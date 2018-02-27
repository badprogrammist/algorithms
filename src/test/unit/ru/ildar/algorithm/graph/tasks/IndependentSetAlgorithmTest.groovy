package ru.ildar.algorithm.graph.tasks

import ru.ildar.algorithm.graph.Graph
import ru.ildar.algorithm.graph.GraphBuilder
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class IndependentSetAlgorithmTest extends Specification {

    def "Test of finding a max-size independent set of tree"() {
        given: "Some indirected tree"
        GraphBuilder gb = GraphBuilder.adjacencyList(false)
        edges.each { edge -> gb.edge(edge[0], edge[1]) }
        Graph graph = gb.create()

        when: "Trying to find a max-size independent set of tree"
        IndependentSetAlgorithm.IndependentSet set = IndependentSetAlgorithm.findMaxSetOfTree(graph)

        then: "The size of found independent set should equals expected"
        set.size() == expectedSet.length

        and: "Found independent set should equals expected"
        set.getSet() == expectedSet


        where:
        edges                                       | expectedSet
        [[0, 1], [0, 2], [0, 3], [0, 4], [1, 5],
         [5, 11], [5, 12], [5, 13], [3, 6], [3, 7],
         [4, 8], [4, 9], [4, 10], [6, 14], [6, 15]] | [1, 2, 7, 8, 9, 10, 11, 12, 13, 14, 15] as int[]
        [[0, 1], [0, 2], [0, 3], [1, 4], [1, 5],
         [2, 6], [2, 7], [3, 8], [6, 9], [7, 10],
         [7, 11], [10, 12], [11, 13]] | [0, 4, 5, 7, 8, 9, 12, 13] as int[]
    }

}
