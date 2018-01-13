package ru.ildar.algorithm.graph

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class GraphConverterTest extends Specification {

    def "Test of converting adjacent matrix into adjacent list"() {
        given: "Some directed graph"
        GraphBuilder gb = GraphBuilder.adjacencyMatrix(directed)
        edges.each { edge -> gb.edge(edge[0], edge[1]) }
        Graph matrix = gb.create()

        when: "Trying to adjacent matrix into adjacent list"
        Graph list = GraphConverter.toAdjacencyList(matrix)

        then: "Converted graph should equals expected"
        checkAdjacencyEdges(list, expectedAdjacencyEdges)

        where:
        edges                                                            | directed | expectedAdjacencyEdges
        [[0, 1], [0, 2], [1, 2], [1, 3], [1, 4], [2, 3], [3, 4]]         | false    | [0: [2, 1], 1: [4, 3, 2, 0], 2: [3, 1, 0], 3: [4, 2, 1], 4: [3, 1]] as Map<Integer, int[]>
        [[0, 1], [0, 2], [3, 0], [2, 3], [2, 1], [1, 4], [1, 5], [5, 2]] | true     | [0: [2, 1], 1: [5, 4], 2: [3, 1], 3: [0], 4: [], 5: [2]] as Map<Integer, int[]>

    }

    boolean checkAdjacencyEdges(Graph graph, Map<Integer, int[]> expectedAdjacencyEdges) {
        for (int v = 0; v < graph.getVerticesCount(); v++) {
            if (graph.getAdjacentVertices(v) != expectedAdjacencyEdges[v]) {
                return false
            }
        }

        return true
    }

}
