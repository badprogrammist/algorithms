package ru.ildar.algorithm.graph

import spock.lang.Specification

import java.util.function.Function

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class BreadthFirstTraversalTest extends Specification {


    def "Test of traversing through graph by breadth-first traverse algorithm"() {
        given: "Some undirected graph"
        def graph = GraphBuilder.adjacencyList(false)
                .edge(0, 1)
                .edge(0, 5)
                .edge(0, 4)
                .edge(1, 2)
                .edge(1, 4)
                .edge(2, 3)
                .edge(3, 4)
                .create()


        when: "Trying to traverse through graph"
        def t = new BreadthFirstTraversal()
        def tch = new TraverseChecker(expectedPath: expectedPath, expectedAdjacencyEdges: expectedAdjacencyEdges)
        t.setVertexPreProcessor { g, v -> tch.preProcessVertex(g, v) }
        t.setVertexPostProcessor { g, v -> tch.postProcessVertex(g, v) }
        t.setEdgeProcessor { g, v1, v2 -> tch.processEdge(g, v1, v2) }
        t.run(graph, start)

        then: "The path of traversing graph and adjacency edges should equals expected"
        tch.success

        and: "The parent of vertex should equals expected"
        checkParents(t, expectedParents)

        where:
        start | expectedPath       | expectedAdjacencyEdges | expectedParents
        0     | [0, 1, 2, 3, 5, 4] | [0: [4, 5, 1] as int[],
                                      1: [4, 2, 0] as int[],
                                      2: [3, 1] as int[],
                                      3: [4, 2] as int[],
                                      4: [0, 4] as int[],
                                      5: [0] as int[]]      | [1: 0, 2: 1, 3: 2, 4: 0, 5: 0]
    }

    class TraverseChecker {
        int vertexIdx = 0
        int edgeIdx = 0
        int[] expectedPath
        Map<Integer, int[]> expectedAdjacencyEdges
        boolean success = true

        void preProcessVertex(Graph g, int v) {
            success = v == expectedPath[vertexIdx]
        }

        void postProcessVertex(Graph g, int v) {
            success = v == expectedPath[vertexIdx]
            vertexIdx++
            edgeIdx = 0
        }

        void processEdge(Graph g, int v1, int v2) {
            success = expectedPath[vertexIdx] == v1 && expectedAdjacencyEdges[vertexIdx][edgeIdx] == v2
            edgeIdx++
        }
    }

    boolean checkParents(BreadthFirstTraversal t, Map expectedParents) {
        for (def entry : expectedParents.entrySet()) {
            if (t.parentOf(entry.getKey()) != entry.getValue()) {
                return false
            }
        }
        return true
    }
}
