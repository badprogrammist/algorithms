package ru.ildar.algorithm.graph.tasks

import ru.ildar.algorithm.graph.Graph
import ru.ildar.algorithm.graph.GraphBuilder
import ru.ildar.algorithm.graph.MinSpanningTree
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class FindingChangeInTheCostTest extends Specification {

    def "Test of algorithm of finding the change of a weight in the non-MST edge"() {
        given: "Some graph"
        GraphBuilder gb = GraphBuilder.adjacencyList(false)
        edges.each { edge, weight -> gb.edge(edge[0], edge[1], weight) }
        Graph graph = gb.create()

        and: "MST of the graph"
        MinSpanningTree.Algorithm mstAlg = new MinSpanningTree.PrimsAlgorithm()
        mstAlg.find(graph)
        Graph mst = mstAlg.getTree()

        when: "Do the change in the cost to a non-MST edge"
        graph.setEdgeWeight(v1, v2, weight)
        FindingChangeInTheCost alg = new FindingChangeInTheCost()
        alg.find(graph, mst)

        then: "The algorithm should find the change"
        alg.getEdge()[0] == v1
        alg.getEdge()[1] == v2
        !mst.isAdjacent(v1, v2)


        where:
        edges                  | v1 | v2 | weight
        [[0, 1]: 5, [0, 2]: 7,
         [0, 3]: 4, [0, 4]: 2,
         [1, 4]: 2, [4, 3]: 3,
         [3, 2]: 9, [2, 5]: 5,
         [5, 3]: 7, [5, 6]: 12,
         [6, 4]: 7, [6, 3]: 4] | 0  | 3  | 2

        [[0, 1]: 5, [0, 2]: 7,
         [0, 3]: 4, [0, 4]: 2,
         [1, 4]: 2, [4, 3]: 3,
         [3, 2]: 9, [2, 5]: 5,
         [5, 3]: 7, [5, 6]: 12,
         [6, 4]: 7, [6, 3]: 4] | 5  | 6  | 6

        [[0, 1]: 5, [0, 2]: 7,
         [0, 3]: 4, [0, 4]: 2,
         [1, 4]: 2, [4, 3]: 3,
         [3, 2]: 9, [2, 5]: 5,
         [5, 3]: 7, [5, 6]: 12,
         [6, 4]: 7, [6, 3]: 4] | 3  | 2  | 6

    }

}
