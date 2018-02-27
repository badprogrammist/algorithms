package ru.ildar.algorithm.graph.tasks

import ru.ildar.algorithm.graph.Graph
import ru.ildar.algorithm.graph.GraphBuilder
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class VertexCoverTest extends Specification {

    def "Test of finding a minimum-size vertex cover"() {
        given: "Some graph"
        GraphBuilder gb = GraphBuilder.adjacencyList(false)
        edges.each { edge -> gb.edge(edge[0], edge[1]) }
        Graph graph = gb.create()

        when: "Trying to find a minimum-size vertex cover"
        VertexCover.VertexCoverAlgorithm vc = VertexCover.findVertexCoverOfNotWeightedTree(graph)

        then: "Found vertices cover should equals expected"
        vc.getVerticesCover() == expectedVerticesCover

        and: "Vertices cover coveredEdges should equals expected"
        checkCoveredEdgesAmount(vc, expectedCoveredEdgesAmount)

        and: "Sum of covered edges should equals amount of edges in graph"
        checkThatSumOfCoveredEdgesEqualsAmountOfEdgesInGraph(vc, graph)

        where:
        edges                                                    | expectedVerticesCover | expectedCoveredEdgesAmount
        [[0, 1], [0, 2], [0, 3], [1, 4], [2, 5],
         [2, 6], [5, 11], [5, 10], [3, 7], [3, 8], [3, 9]]       | [1, 2, 3, 5] as int[] | [1: 2, 2: 2, 3: 4, 5: 3]
        [[0, 1], [0, 2], [1, 3], [1, 4], [3, 6], [3, 7], [4, 5]] | [0, 3, 4] as int[]    | [0: 2, 3: 3, 4: 2]
    }

    def "Test of finding a vertex cover where vertex weight is degree"() {
        given: "Some graph"
        GraphBuilder gb = GraphBuilder.adjacencyList(false)
        edges.each { edge -> gb.edge(edge[0], edge[1]) }
        Graph graph = gb.create()

        when: "Trying to find a minimum-weighted vertex cover"
        VertexCover.VertexCoverAlgorithm vc = VertexCover.findVertexCoverOfDegreeWeightedTree(graph)

        then: "Found vertices cover should equals expected"
        vc.getVerticesCover() == expectedVerticesCover

        and: "Vertices cover coveredEdges should equals expected"
        checkCoveredEdgesAmount(vc, expectedCoveredEdgesAmount)

        and: "The vertex cover weight should equals the amount of edges"
        vc.getVerticesCoverWeight() == graph.getEdgesCount()

        and: "Sum of covered edges should equals amount of edges in graph"
        checkThatSumOfCoveredEdgesEqualsAmountOfEdgesInGraph(vc, graph)


        where:
        edges                                       | expectedVerticesCover           | expectedCoveredEdgesAmount
        [[0, 1], [0, 2], [0, 3], [0, 4], [1, 5],
         [5, 11], [5, 12], [5, 13], [3, 6], [3, 7],
         [4, 8], [4, 9], [4, 10], [6, 14], [6, 15]] | [0, 5, 6, 7, 8, 9, 10] as int[] | [0: 4, 5: 4, 6: 3, 7: 1, 8: 1, 9: 1, 10: 1]
    }

    def "Test of finding a vertex cover where vertex has random weight"() {
        given: "Some graph"
        GraphBuilder gb = GraphBuilder.adjacencyList(false)
        edges.each { edge -> gb.edge(edge[0], edge[1]) }
        vertexWeight.each { vertex, weight -> gb.setVertexWeight(vertex, weight) }
        Graph graph = gb.create()

        when: "Trying to find a minimum-weighted vertex cover"
        VertexCover.VertexCoverAlgorithm vc = VertexCover.findVertexCoverOfRandomWeightedTree(graph)

        then: "Found vertices cover should equals expected"
        vc.getVerticesCover() == expectedVerticesCover

        and: "Vertices cover coveredEdges should equals expected"
        checkCoveredEdgesAmount(vc, expectedCoveredEdgesAmount)

        and: "The vertex cover weight should equals expected"
        vc.getVerticesCoverWeight() == expectedVerticesCoverWeight


        where:
        edges                                       | vertexWeight                    | expectedVerticesCover    | expectedCoveredEdgesAmount     | expectedVerticesCoverWeight
        [[0, 1], [0, 2], [0, 3], [0, 4], [1, 5],
         [5, 11], [5, 12], [5, 13], [3, 6], [3, 7],
         [4, 8], [4, 9], [4, 10], [6, 14], [6, 15]] | [0 : 1, 1: 8, 2: 4, 3: 11,
                                                       4 : 5, 5: 2, 6: 7, 7: 6,
                                                       8 : 3, 9: 12, 10: 14, 11: 16,
                                                       12: 9, 13: 13, 14: 15, 15: 10] | [0, 4, 5, 6, 7] as int[] | [0: 4, 4: 4, 5: 4, 6: 3, 7: 1] | 21

        [[0, 1], [0, 2], [0, 3], [0, 4], [1, 5],
         [5, 11], [5, 12], [5, 13], [3, 6], [3, 7],
         [4, 8], [4, 9], [4, 10], [6, 14], [6, 15]] | [0 : 2, 1: 5, 2: 1, 3: 30,
                                                       4 : 4, 5: 10, 6: 2, 7: 12,
                                                       8 : 6, 9: 7, 10: 3, 11: 8,
                                                       12: 9, 13: 19, 14: 1, 15: 3]   | [0, 4, 5, 6, 7] as int[] | [0: 4, 4: 4, 5: 4, 6: 3, 7: 1] | 30
    }

    boolean checkCoveredEdgesAmount(VertexCover.VertexCoverAlgorithm vertexCover, Map<Integer, Integer> expectedVerticesCoverWeight) {
        for (int v : vertexCover.getVerticesCover()) {
            if (vertexCover.getCoveredEdgesAmount(v) != expectedVerticesCoverWeight[v]) {
                return false
            }
        }

        return true
    }

    boolean checkThatSumOfCoveredEdgesEqualsAmountOfEdgesInGraph(VertexCover.VertexCoverAlgorithm vertexCover, Graph graph) {
        int coveredEdgesAmount = 0

        for (int v : vertexCover.getVerticesCover()) {
            coveredEdgesAmount += vertexCover.getCoveredEdgesAmount(v)
        }

        return coveredEdgesAmount == graph.getEdgesCount()
    }

}
