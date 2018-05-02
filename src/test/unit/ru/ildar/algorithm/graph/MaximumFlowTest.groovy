package ru.ildar.algorithm.graph

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class MaximumFlowTest extends Specification {

    def "Test of Ford-Fulkerson Algorithm"() {
        given: "Some directed-weighted graph"
        GraphBuilder gb = GraphBuilder.adjacencyList(true)
        edges.each { edge, weight -> gb.edge(edge[0], edge[1], weight) }
        Graph graph = gb.create()

        when: "Trying to find maximum flow"
        MaximumFlow.Algorithm algorithm = new MaximumFlow.FordFulkersonAlgorithm()
        algorithm.find(graph, source, sink)
        double maxFlow = algorithm.getMaxFlow()

        then: "The max flow should equals expected"
        maxFlow == expectedMaxFlow

        where:
        edges                               | source | sink | expectedMaxFlow
        [[0, 1]: 10, [0, 2]: 10, [1, 2]: 2,
         [1, 4]: 8, [1, 3]: 4, [2, 4]: 9,
         [3, 5]: 10, [4, 3]: 6, [4, 5]: 10] | 0      | 5    | 19

    }

}
