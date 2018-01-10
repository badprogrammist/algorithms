package ru.ildar.algorithm.graph.tasks

import ru.ildar.algorithm.graph.Graph
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class ReconstructTheTreeTest extends Specification {

    def "Test the algorithm of reconstructing binary tree from in-order traversal and pre-order traversal of this tree"() {
        given: "in-order traversal and pre-order traversal"

        when: "Trying to reconstruct binary tree"
        ReconstructTheTree rtt = new ReconstructTheTree()
        Graph graph = rtt.reconstruct(inOrder, preOrder)

        then: "The count of vertices should equals expected"
        graph.getVerticesCount() == expectedVerticesCount

        and: "The count of edges should equals expected"
        graph.getEdgesCount() == expectedEdgesCount

        and: "The graph should equals expected"
        checkAdjacencyEdges(graph, expectedAdjacencyEdges)

        where:
        inOrder                              | preOrder                             | expectedVerticesCount | expectedEdgesCount | expectedAdjacencyEdges
        [0, 2, 3, 5, 6, 7, 1, 8, 4] as int[] | [7, 2, 0, 5, 3, 6, 1, 4, 8] as int[] | 9                     | 8                  | [0: [2], 7: [1, 2], 2: [5, 0, 7], 3: [5], 5: [6, 3, 2], 6: [5], 1: [4, 7], 4: [8, 1], 8: [4]]

    }

    boolean checkAdjacencyEdges(Graph graph, Map<Integer, int[]> expectedAdjacencyEdges) {
        for (int v = 0; v < graph.getVerticesCount(); v++) {
            if (graph.getAdjacentEdges(v) != expectedAdjacencyEdges[v]) {
                return false
            }
        }

        return true
    }

}
