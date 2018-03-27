package ru.ildar.algorithm.graph

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class MinSpanningTreeTest extends Specification {

    def "Test of the Prim's algorithm"() {
        given: "Some graph"
        GraphBuilder gb = GraphBuilder.adjacencyList(false)
        edges.each { edge, weight -> gb.edge(edge[0], edge[1], weight) }
        Graph graph = gb.create()

        when: "Trying to find a min spanning tree"
        MinSpanningTree.Algorithm algorithm = new MinSpanningTree.PrimsAlgorithm()
        algorithm.find(graph)
        Graph tree = algorithm.getTree()

        then: "The found spanning tree should equals expected"
        checkEdges(tree, expectedEdges as int[][])

        where:
        edges                                            | expectedEdges
        [[0, 1]: 5, [0, 2]: 7,
         [0, 3]: 4, [0, 4]: 2,
         [1, 4]: 2, [4, 3]: 3,
         [3, 2]: 9, [2, 5]: 5,
         [5, 3]: 7, [5, 6]: 12,
         [6, 4]: 7, [6, 3]: 4] as Map<Integer[], Double> | [[2, 5], [2, 0], [3, 6], [3, 4], [4, 0], [4, 1]]

        [[0, 1]: 4, [0, 7]: 8,
         [1, 2]: 8, [1, 7]: 11,
         [7, 8]: 7, [7, 6]: 1,
         [2, 8]: 2, [8, 6]: 6,
         [2, 3]: 7, [2, 5]: 4,
         [3, 5]: 14, [3, 4]: 9,
         [5, 4]: 10, [6, 5]: 2] as Map<Integer[], Double> | [[0, 1], [1, 2], [7, 6], [6, 5], [5, 2], [2, 8], [2, 3], [3, 4]]
    }

    def "Test of the Kruskal's algorithm"() {
        given: "Some graph"
        GraphBuilder gb = GraphBuilder.adjacencyList(false)
        edges.each { edge, weight -> gb.edge(edge[0], edge[1], weight) }
        Graph graph = gb.create()

        when: "Trying to find a min spanning tree"
        MinSpanningTree.Algorithm algorithm = new MinSpanningTree.KruskalsAlgorithm()
        algorithm.find(graph)
        Graph tree = algorithm.getTree()

        then: "The found spanning tree should equals expected"
        checkEdges(tree, expectedEdges as int[][])

        where:
        edges                                            | expectedEdges
        [[0, 1]: 5, [0, 2]: 7,
         [0, 3]: 4, [0, 4]: 2,
         [1, 4]: 2, [4, 3]: 3,
         [3, 2]: 9, [2, 5]: 5,
         [5, 3]: 7, [5, 6]: 12,
         [6, 4]: 7, [6, 3]: 4] as Map<Integer[], Double> | [[2, 5], [2, 0], [3, 6], [3, 4], [4, 0], [4, 1]]

        [[0, 1]: 4, [0, 7]: 8,
         [1, 2]: 8, [1, 7]: 11,
         [7, 8]: 7, [7, 6]: 1,
         [2, 8]: 2, [8, 6]: 6,
         [2, 3]: 7, [2, 5]: 4,
         [3, 5]: 14, [3, 4]: 9,
         [5, 4]: 10, [6, 5]: 2] as Map<Integer[], Double> | [[0, 1], [0, 7], [7, 6], [6, 5], [5, 2], [2, 8], [2, 3], [3, 4]]
    }

    def "Test of the Kruskal's algorithm that uses union-find structure"() {
        given: "Some graph"
        GraphBuilder gb = GraphBuilder.adjacencyList(false)
        edges.each { edge, weight -> gb.edge(edge[0], edge[1], weight) }
        Graph graph = gb.create()

        when: "Trying to find a min spanning tree"
        MinSpanningTree.Algorithm algorithm = new MinSpanningTree.UnionFindKruskalsAlgorithm()
        algorithm.find(graph)
        Graph tree = algorithm.getTree()

        then: "The found spanning tree should equals expected"
        checkEdges(tree, expectedEdges as int[][])

        where:
        edges                                            | expectedEdges
        [[0, 1]: 5, [0, 2]: 7,
         [0, 3]: 4, [0, 4]: 2,
         [1, 4]: 2, [4, 3]: 3,
         [3, 2]: 9, [2, 5]: 5,
         [5, 3]: 7, [5, 6]: 12,
         [6, 4]: 7, [6, 3]: 4] as Map<Integer[], Double> | [[2, 5], [2, 0], [3, 6], [3, 4], [4, 0], [4, 1]]

        [[0, 1]: 4, [0, 7]: 8,
         [1, 2]: 8, [1, 7]: 11,
         [7, 8]: 7, [7, 6]: 1,
         [2, 8]: 2, [8, 6]: 6,
         [2, 3]: 7, [2, 5]: 4,
         [3, 5]: 14, [3, 4]: 9,
         [5, 4]: 10, [6, 5]: 2] as Map<Integer[], Double> | [[0, 1], [0, 7], [7, 6], [6, 5], [5, 2], [2, 8], [2, 3], [3, 4]]
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
