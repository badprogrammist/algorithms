package ru.ildar.algorithm.graph

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class IncidenceMatrixTest extends Specification {

    def "Test of incidence matrix structure"() {
        when: "Create some incidence matrix structure"
        GraphBuilder gb = GraphBuilder.incidenceMatrix(directed)
        edges.each { edge -> gb.edge(edge[0], edge[1]) }
        Graph matrix = gb.create()

        then: "Converted graph should equals expected"
        checkAdjacencyEdges(matrix, expectedAdjacencyEdges)

        and: "Check adjacency vertices"
        checkAdjacencies(edges, matrix)

        when: "Removing an edge"
        def degree1 = matrix.getDegree(removingEdge[0])
        def degree2 = matrix.getDegree(removingEdge[1])
        def count = matrix.getEdgesCount()

        matrix.removeEdge(removingEdge[0], removingEdge[1])

        then: "Edge should be removed"
        matrix.getDegree(removingEdge[0]) == degree1 - 1
        if (!directed) {
            matrix.getDegree(removingEdge[1]) == degree2 - 1
        }

        !matrix.isAdjacent(removingEdge[0], removingEdge[1])
        !matrix.isAdjacent(removingEdge[1], removingEdge[0])
        matrix.getEdgesCount() == count - 1

        where:
        edges                                                            | directed | expectedAdjacencyEdges                                                                     | removingEdge
        [[0, 1], [0, 2], [1, 2], [1, 3], [1, 4], [2, 3], [3, 4]]         | false    | [0: [1, 2], 1: [0, 2, 3, 4], 2: [0, 1, 3], 3: [1, 2, 4], 4: [1, 3]] as Map<Integer, int[]> | [1, 4]
        [[0, 1], [0, 2], [3, 0], [2, 3], [2, 1], [1, 4], [1, 5], [5, 2]] | true     | [0: [1, 2], 1: [4, 5], 2: [3, 1], 3: [0], 4: [], 5: [2]] as Map<Integer, int[]>            | [2, 1]

    }

    boolean checkAdjacencyEdges(Graph graph, Map<Integer, int[]> expectedAdjacencyEdges) {
        for (int v = 0; v < graph.getVerticesCount(); v++) {
            if (graph.getAdjacentVertices(v) != expectedAdjacencyEdges[v]) {
                return false
            }
        }

        return true
    }

    boolean checkAdjacencies(List<List<Integer>> edges, Graph matrix) {
        for (List<Integer> edge : edges) {
            if (!matrix.isAdjacent(edge.get(0), edge.get(1))) {
                return false
            }
        }

        return true
    }

}
