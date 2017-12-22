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
        int start = 0
        int[] expectedPath = [0, 4, 3, 2, 1, 5]
        List<Map> expectedAdjacencyEdges = [[0: 4], [4: 3], [3: 4],
                                            [3: 2], [2: 3], [2: 1],
                                            [1: 4], [1: 2], [1: 0],
                                            [4: 0], [0: 5], [5: 0]]
        def t = new DepthFirstRecursiveTraversal(graph)
        def tch = new TraverseChecker(expectedPath: expectedPath, expectedAdjacencyEdges: expectedAdjacencyEdges)
        t.setVertexPreProcessor { traversal, v -> tch.preProcessVertex(traversal, v) }
        t.setVertexPostProcessor { traversal, v -> tch.postProcessVertex(traversal, v) }
        t.setEdgeProcessor { traversal, v1, v2 -> tch.processEdge(traversal, v1, v2) }
        t.traverse(start)

        then: "The path of traversing graph and adjacency edges should equals expected"
        tch.success

        and: "The count of edges should equals expected"
        graph.getEdgesCount() == 7

        and: "The parent of vertex should equals expected"
        Map expectedParents = [0: 0, 1: 2, 2: 3, 3: 4, 4: 0, 5: 0]
        checkParents(t, expectedParents)

        and: "The entry time should equals expected"
        int[] expectedEntryTime = [1, 5, 4, 3, 2, 10]
        checkTime(t, expectedEntryTime, true)

        and: "The exit time should equals expected"
        int[] expectedExitTime = [12, 6, 7, 8, 9, 11]
        checkTime(t, expectedExitTime, false)
    }

    class TraverseChecker {
        int vertexIdx = 0
        int edgeIdx = 0
        int[] expectedPath
        List<Map> expectedAdjacencyEdges
        boolean success = true

        void preProcessVertex(GraphTraversal traversal, int v) {
            success = v == expectedPath[vertexIdx]
            vertexIdx++
        }

        void postProcessVertex(GraphTraversal traversal, int v) {
            success = v == expectedPath[expectedPath.length - vertexIdx]
        }

        void processEdge(GraphTraversal traversal, int v1, int v2) {
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

    boolean checkTime(AbstractDepthFirstTraversal t, int[] expected, boolean entry) {
        for (int v = 0; v < expected.length; v++) {
            if (entry) {
                if (t.getEntryTime(v) != expected[v]) {
                    return false
                }
            } else {
                if (t.getExitTime(v) != expected[v]) {
                    return false
                }
            }
        }
        return true
    }
}
