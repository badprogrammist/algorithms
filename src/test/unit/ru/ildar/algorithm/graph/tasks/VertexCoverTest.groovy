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
        VertexCover vertexCover = new VertexCover()
        vertexCover.findMinVerticesCover(graph)

        then: "Found vertices cover should equals expected"
        vertexCover.getVerticesCover() == expectedVerticesCover

        and: "Vertices cover weights should equals expected"
        checkWeights(vertexCover, expectedVerticesCoverWeight)


        where:
        edges                                                    | expectedVerticesCover | expectedVerticesCoverWeight
        [[0, 1], [0, 2], [0, 3], [1, 4], [2, 5],
         [2, 6], [5, 11], [5, 10], [3, 7], [3, 8], [3, 9]]       | [1, 2, 3, 5] as int[] | [1: 2, 2: 2, 3: 4, 5: 3]
        [[0, 1], [0, 2], [1, 3], [1, 4], [3, 6], [3, 7], [4, 5]] | [0, 3, 4] as int[]    | [0: 2, 3: 3, 4: 2]
    }

    def "Test of finding a minimum-weighted vertex cover"() {
        given: "Some graph"
        GraphBuilder gb = GraphBuilder.adjacencyList(false)
        edges.each { edge -> gb.edge(edge[0], edge[1]) }
        Graph graph = gb.create()

        when: "Trying to find a minimum-weighted vertex cover"
        VertexCover vertexCover = new VertexCover()
        vertexCover.findMinWeightedVerticesCover(graph)

        then: "Found vertices cover should equals expected"
        vertexCover.getVerticesCover() == expectedVerticesCover

        and: "Vertices cover weights should equals expected"
        checkWeights(vertexCover, expectedVerticesCoverWeight)

        and: "The amount of covered edges equals the amount of edges"
        getSumOfVertexCoverWeights(vertexCover) == graph.getEdgesCount()


        where:
        edges                                       | expectedVerticesCover           | expectedVerticesCoverWeight
        [[0, 1], [0, 2], [0, 3], [0, 4], [1, 5],
         [5, 11], [5, 12], [5, 13], [3, 6], [3, 7],
         [4, 8], [4, 9], [4, 10], [6, 14], [6, 15]] | [0, 5, 6, 7, 8, 9, 10] as int[] | [0: 4, 5: 4, 6: 3, 7: 1, 8: 1, 9: 1, 10: 1]
    }

    boolean checkWeights(VertexCover vertexCover, Map<Integer, Integer> expectedVerticesCoverWeight) {
        for (int v : vertexCover.getVerticesCover()) {
            if (vertexCover.getVertexWeight(v) != expectedVerticesCoverWeight[v]) {
                return false
            }
        }

        return true
    }

    int getSumOfVertexCoverWeights(VertexCover vertexCover) {
        int sum = 0

        for (int v : vertexCover.getVerticesCover()) {
            sum += vertexCover.getVertexWeight(v)
        }

        return sum
    }

}
