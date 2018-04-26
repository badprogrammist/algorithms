package ru.ildar.algorithm.graph

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class AllPairsShortestPathTest extends Specification {

    def "Test of Floyd-Warshall algorithm of finding all-pairs shortest path"() {
        given: "Some undirected-weighted graph"
        GraphBuilder gb = GraphBuilder.adjacencyList(false)
        edges.each { edge, weight -> gb.edge(edge[0], edge[1], weight) }
        Graph graph = gb.create()

        when: "Trying to find all-pairs shortest path"
        AllPairsShortestPath.Algorithm algorithm = new AllPairsShortestPath.FloydWarshallAlgorithm()
        double[][] weights = algorithm.find(graph)

        then: "The weights of all-pairs paths should equals expected"
        equalsWeights(weights, expectedWeights as double[][])

        where:
        edges                              | expectedWeights
        [[0, 1]: 4, [0, 2]: 3, [0, 3]: 7,
         [1, 4]: 5, [1, 2]: 6, [2, 4]: 11,
         [2, 3]: 8, [3, 4]: 2, [3, 5]: 5,
         [4, 5]: 10, [4, 6]: 2, [5, 6]: 3] | [[6, 4, 3, 7, 9, 12, 11],
                                              [4, 8, 6, 7, 5, 10, 7],
                                              [3, 6, 6, 8, 10, 13, 12],
                                              [7, 7, 8, 4, 2, 5, 4],
                                              [9, 5, 10, 2, 4, 5, 2],
                                              [12, 10, 13, 5, 5, 6, 3],
                                              [11, 7, 12, 4, 2, 3, 4]]

    }

    boolean equalsWeights(double[][] weights, double[][] expected) {
        for (int v1 = 0; v1 < expected.length; v1++) {
            for (int v2 = 0; v2 < expected[v1].length; v2++) {

                if (weights[v1][v2] != expected[v1][v2]) {
                    return false
                }

            }
        }

        return true
    }

}
