package ru.ildar.algorithm.graph

import spock.lang.Specification


/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class DepthFirstTraversalTest extends Specification {


    def "Test of traversing through cycled graph by depth-first traverse algorithm"() {
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
        def t = new DepthFirstTraversal(graph)
        def tch = new TraverseChecker(t: t, expectedPath: expectedPath, expectedAdjacencyEdges: expectedAdjacencyEdges)
        t.setVertexPreProcessor { g, v -> tch.preProcessVertex(g, v) }
        t.setVertexPostProcessor { g, v -> tch.postProcessVertex(g, v) }
        t.setEdgeProcessor { g, v1, v2 -> tch.processEdge(g, v1, v2) }
        t.traverse(start)

        then: "The path of traversing graph and adjacency edges should equals expected"
        tch.success

        and: "The count of edges should equals expected"
        tch.edgesCount == graph.getEdgesCount()

        and: "The parent of vertex should equals expected"
        checkParents(t, expectedParents)

        where:
        start | expectedPath       | expectedAdjacencyEdges | expectedParents
        0     | [0, 1, 2, 3, 5, 4] | [0: [4, 5, 1] as int[],
                                      1: [4, 2] as int[],
                                      2: [3] as int[],
                                      3: [4] as int[]]      | [1: 0, 2: 1, 3: 2, 4: 0, 5: 0]
    }

    def "Test of traversing through acyclic graph by depth-first traverse algorithm"() {
        given: "Some acyclic undirected graph"
        def graph = GraphBuilder.adjacencyList(false)
                .edge(0, 1)
                .edge(1, 4)
                .edge(1, 5)

                .edge(0, 2)
                .edge(2, 6)

                .edge(0, 3)
                .edge(3, 7)
                .edge(3, 8)
                .create()


        when: "Trying to traverse through graph"
        def t = new DepthFirstTraversal(graph)
        def tch = new TraverseChecker(t: t, expectedPath: expectedPath, expectedAdjacencyEdges: expectedAdjacencyEdges)
        t.setVertexPreProcessor { traversal, v -> tch.preProcessVertex(traversal, v) }
        t.setVertexPostProcessor { traversal, v -> tch.postProcessVertex(traversal, v) }
        t.setEdgeProcessor { traversal, v1, v2 -> tch.processEdge(traversal, v1, v2) }
        t.traverse(start)

        then: "The path of traversing graph and adjacency edges should equals expected"
        tch.success

        and: "The count of edges should equals expected"
        tch.edgesCount == graph.getEdgesCount()

        and: "The parent of vertex should equals expected"
        checkParents(t, expectedParents)

        where:
        start | expectedPath                | expectedAdjacencyEdges | expectedParents
        0     | [0, 1, 4, 5, 2, 6, 3, 7, 8] | [0: [3, 2, 1] as int[],
                                               1: [5, 4] as int[],
                                               2: [6] as int[],
                                               3: [8, 7] as int[]]   | [1: 0, 2: 0, 3: 0, 4: 1, 5: 1, 6: 2, 7: 3, 8: 3]
    }

    class TraverseChecker {

        DepthFirstTraversal t

        int vertexIdx = 0
        int edgeIdx = 0
        int[] expectedPath
        int edgesCount = 0
        Map<Integer, int[]> expectedAdjacencyEdges
        boolean success = true

        void preProcessVertex(GraphTraversal traversal, int v) {
            success = v == expectedPath[vertexIdx]
        }

        void postProcessVertex(GraphTraversal traversal, int v) {
            success = v == expectedPath[vertexIdx]
            vertexIdx++
            edgeIdx = 0
        }

        void processEdge(GraphTraversal traversal, int v1, int v2) {
            success = expectedAdjacencyEdges[v1][edgeIdx] == v2
            edgeIdx++
            edgesCount++
        }
    }

    boolean checkParents(GraphTraversal t, Map expectedParents) {
        for (def entry : expectedParents.entrySet()) {
            if (t.parentOf(entry.getKey()) != entry.getValue()) {
                return false
            }
        }
        return true
    }
}
