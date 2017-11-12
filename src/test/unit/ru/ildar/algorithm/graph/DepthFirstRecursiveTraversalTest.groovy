package ru.ildar.algorithm.graph

import spock.lang.Specification


/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class DepthFirstRecursiveTraversalTest extends Specification {


    def "Test of traversing through graph by depth-first traverse algorithm"() {
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
        def t = new DepthFirstRecursiveTraversal(graph)
        def tch = new TraverseChecker(expectedPath: expectedPath, expectedAdjacencyEdges: expectedAdjacencyEdges)
        t.setVertexPreProcessor { g, v -> tch.preProcessVertex(g, v) }
        t.setVertexPostProcessor { g, v -> tch.postProcessVertex(g, v) }
        t.setEdgeProcessor { g, v1, v2 -> tch.processEdge(g, v1, v2) }
        t.traverse(start)

        then: "The path of traversing graph and adjacency edges should equals expected"
        tch.success

        and: "The count of edges should equals expected"
        graph.getEdgesCount() == 7

        and: "The parent of vertex should equals expected"
        checkParents(t, expectedParents)

        where:
        start | expectedPath       | expectedAdjacencyEdges   | expectedParents
        0     | [0, 4, 3, 2, 1, 5] | [[0: 4], [4: 3], [3: 4],
                                      [3: 2], [2: 3], [2: 1],
                                      [1: 4], [1: 2], [1: 0],
                                      [4: 0], [0: 5], [5: 0]] | [0: 0, 1: 2, 2: 3, 3: 4, 4: 0, 5: 0]
    }

    class TraverseChecker {
        int vertexIdx = 0
        int edgeIdx = 0
        int[] expectedPath
        List<Map> expectedAdjacencyEdges
        boolean success = true

        void preProcessVertex(Graph g, int v) {
            success = v == expectedPath[vertexIdx]
            vertexIdx++
        }

        void postProcessVertex(Graph g, int v) {
            success = v == expectedPath[expectedPath.length - vertexIdx]
        }

        void processEdge(Graph g, int v1, int v2) {
            success = expectedAdjacencyEdges[edgeIdx][v1] == v2
            edgeIdx++
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
