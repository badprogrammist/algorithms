package ru.ildar.algorithm.graph.tasks

import ru.ildar.algorithm.graph.Graph
import ru.ildar.algorithm.graph.GraphBuilder
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class FindMinSpanningTreeAfterEdgeTest extends Specification {

    def "Test of algorithm of finding min spanning tree after adding new edge"() {
        given: "Some graph"
        GraphBuilder gb = GraphBuilder.adjacencyList(false)
        edges.each { edge, weight -> gb.edge(edge[0], edge[1], weight) }
        Graph graph = gb.create()

        when: "Trying to find initial min spanning tree"
        FindMinSpanningTreeAfterEdge algorithm = new FindMinSpanningTreeAfterEdge()
        algorithm.init(graph)

        then: "The initial spanning tree should equals expected"
        checkEdges(algorithm.getTree(), expectedInitTree as int[][])

        when: "Add new edge"
        algorithm.addEdge(v1, v2, weight)

        then: "The new spanning tree should equals expected"
        checkEdges(algorithm.getTree(), expectedNewTree as int[][])

        where:
        edges                                            | expectedInitTree                                 | v1 | v2 | weight | expectedNewTree
        [[0, 1]: 5, [0, 2]: 7,
         [0, 3]: 4, [0, 4]: 2,
         [1, 4]: 2, [4, 3]: 3,
         [3, 2]: 9, [2, 5]: 5,
         [5, 3]: 7, [5, 6]: 12,
         [6, 4]: 7, [6, 3]: 4] as Map<Integer[], Double> | [[0, 2], [2, 5], [3, 6], [4, 3], [0, 4], [4, 1]] | 5  | 4  | 1      | [[5, 4], [2, 5], [3, 6], [4, 3], [0, 4], [4, 1]]

        [[0, 1]: 5, [0, 2]: 7,
         [0, 3]: 4, [0, 4]: 2,
         [1, 4]: 2, [4, 3]: 3,
         [3, 2]: 9, [2, 5]: 5,
         [5, 3]: 7, [5, 6]: 12,
         [6, 4]: 7, [6, 3]: 4] as Map<Integer[], Double> | [[0, 2], [2, 5], [3, 6], [4, 3], [0, 4], [4, 1]] | 2  | 4  | 1      | [[4, 2], [2, 5], [3, 6], [3, 4], [4, 0], [4, 1]]
    }

    boolean checkEdges(Graph graph, int[][] edges) {
        if (graph.getEdgesCount() != edges.length) {
            return false
        }

        for (int[] edge : edges) {
            if (!graph.isAdjacent(edge[0], edge[1])) {
                return false
            }
        }

        return true
    }

}
