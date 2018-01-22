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
        GraphBuilder gb = GraphBuilder.adjacencyList(directed)
        edges.each { edge -> gb.edge(edge[0], edge[1]) }
        Graph graph = gb.create()

        when: "Trying to find a minimum-size vertex cover"
        VertexCover vertexCover = new VertexCover()
        vertexCover.findVerticesCover(graph)

        then: "Found vertices cover should equals expected"
        vertexCover.getVerticesCover() == expectedVerticesCover

        and: "Vertices cover weights should equals expected"
        checkWeights(vertexCover, expectedVerticesCoverWeight)


        where:
        edges                                              | directed | expectedVerticesCover    | expectedVerticesCoverWeight
        [[0, 1], [0, 2], [0, 3], [1, 4], [2, 5],
         [2, 6], [5, 11], [5, 10], [3, 7], [3, 8], [3, 9]] | false    | [1, 2, 3, 5] as int[]    | [1: 2, 2: 2, 3: 4, 5: 3]
        [[0, 1], [0, 2], [0, 3], [1, 4], [2, 5],
         [2, 6], [5, 11], [5, 10], [3, 7], [3, 8], [3, 9]] | true     | [0, 1, 2, 3, 5] as int[] | [0: 3, 1: 1, 2: 2, 3: 3, 5: 2]
    }

    boolean checkWeights(VertexCover vertexCover, Map<Integer, Integer> expectedVerticesCoverWeight) {
        for (int v : vertexCover.getVerticesCover()) {
            if (vertexCover.getVertexWeight(v) != expectedVerticesCoverWeight[v]) {
                return false
            }
        }

        return true
    }

}
