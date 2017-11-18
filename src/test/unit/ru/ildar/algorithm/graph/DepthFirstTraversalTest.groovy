package ru.ildar.algorithm.graph

import spock.lang.Specification


/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class DepthFirstTraversalTest extends Specification {


    def "Test of traversing through graph by depth-first traverse algorithm"() {
        given: "Some graph"
        GraphBuilder gb = GraphBuilder.adjacencyList(false)
        edges.each { edge -> gb.edge(edge[0], edge[1]) }
        Graph graph = gb.create()

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

        and: "The entry time should equals expected"
        checkTime(t, expectedEntryTime, true)

        and: "The exit time should equals expected"
        checkTime(t, expectedExitTime, false)

        where:
        edges                                                            | start | expectedPath                | expectedAdjacencyEdges                       | expectedParents                                  | expectedEntryTime                           | expectedExitTime
        [[0, 1], [0, 5], [0, 4], [1, 2], [1, 4], [2, 3], [3, 4]]         | 0     | [0, 1, 2, 3, 5, 4]          | [0: [4, 5, 1], 1: [4, 2], 2: [3], 3: [4]]    | [1: 0, 2: 1, 3: 2, 4: 0, 5: 0]                   | [1, 6, 9, 12, 16, 14] as int[]              | [5, 8, 11, 13, 17, 15] as int[]
        [[0, 1], [1, 4], [1, 5], [0, 2], [2, 6], [0, 3], [3, 7], [3, 8]] | 0     | [0, 1, 4, 5, 2, 6, 3, 7, 8] | [0: [3, 2, 1], 1: [5, 4], 2: [6], 3: [8, 7]] | [1: 0, 2: 0, 3: 0, 4: 1, 5: 1, 6: 2, 7: 3, 8: 3] | [1, 6, 14, 19, 10, 12, 17, 23, 25] as int[] | [5, 9, 16, 22, 11, 13, 18, 24, 26] as int[]

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
