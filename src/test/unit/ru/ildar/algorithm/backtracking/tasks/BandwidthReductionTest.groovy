package ru.ildar.algorithm.backtracking.tasks

import ru.ildar.algorithm.graph.Graph
import ru.ildar.algorithm.graph.GraphBuilder
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class BandwidthReductionTest extends Specification {

    def "Test of reducing bandwidth by using backtracking algorithm"() {
        given: "Some graph that represents bandwidth problem"
        GraphBuilder gb = GraphBuilder.adjacencyMatrix(false)
        edges.each { edge -> gb.edge(edge[0], edge[1]) }
        Graph graph = gb.create()

        when: "Trying to reducing bandwidth"
        BandwidthReduction alg = new BandwidthReduction()
        alg.solve(graph)

        then: "The max distance between vertices should equals expected"
        alg.getMaxDistance() == expectedDistance

        and: "The permutation of vertices should equals expected"
        alg.getSolution() == expectedPermutation as int[]

        where:
        edges                            | expectedPermutation      | expectedDistance
        [[0, 7], [1, 6], [1, 7],
         [2, 5], [2, 6], [3, 4], [3, 5]] | [0, 7, 1, 6, 2, 5, 3, 4] | 1
    }

}
