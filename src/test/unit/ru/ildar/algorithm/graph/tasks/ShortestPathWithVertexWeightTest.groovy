package ru.ildar.algorithm.graph.tasks

import ru.ildar.algorithm.graph.Graph
import ru.ildar.algorithm.graph.GraphBuilder
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class ShortestPathWithVertexWeightTest extends Specification {

    def "Test of algorithm of finding shortest-path of graph with weighted vertices"() {
        given: "Some directed weighted graph"
        GraphBuilder gb = GraphBuilder.adjacencyList(false)
        edges.each { edge, weight -> gb.edge(edge[0], edge[1], weight) }
        vertices.each { vertex, weight -> gb.setVertexWeight(vertex, weight) }
        Graph graph = gb.create()

        when: "Trying to find shortest-path of directed graph with weighted vertices"
        alg.find(graph, from, to)

        then: "The found path should equals expected"
        alg.getPath() == expectedPath as int[]
        alg.getDistance(to) == expectedDistance

        where:
        edges                  | vertices                                   | from | to | expectedPath | expectedDistance | alg
        [[0, 2]: 0, [0, 3]: 0,
         [1, 0]: 0, [1, 2]: 0,
         [3, 2]: 0, [4, 3]: 0,
         [2, 4]: 0, [4, 1]: 0,
         [4, 6]: 0, [5, 4]: 0,
         [3, 5]: 0, [6, 5]: 0] | [0: 1, 1: 1, 2: 1, 3: 1, 4: 1, 5: 1, 6: 1] | 0    | 6  | [0, 2, 4, 6] | 3                | new ShortestPathWithVertexWeight.ZeroEdgesConstantVerticesCost()
        [[0, 2]: 0, [0, 3]: 0,
         [1, 0]: 0, [1, 2]: 0,
         [3, 2]: 0, [4, 3]: 0,
         [2, 4]: 0, [4, 1]: 0,
         [4, 6]: 0, [5, 4]: 0,
         [3, 5]: 0, [6, 5]: 0] | [0: 1, 1: 1, 2: 1, 3: 1, 4: 1, 5: 1, 6: 1] | 6    | 3  | [6, 4, 3]    | 2                | new ShortestPathWithVertexWeight.ZeroEdgesConstantVerticesCost()
        [[0, 2]: 0, [0, 3]: 0,
         [1, 0]: 0, [1, 2]: 0,
         [3, 2]: 0, [4, 3]: 0,
         [2, 4]: 0, [4, 1]: 0,
         [4, 6]: 0, [5, 4]: 0,
         [3, 5]: 0, [6, 5]: 0] | [0: 1, 1: 2, 2: 3, 3: 3, 4: 5, 5: 4, 6: 2] | 6    | 0  | [6, 4, 1, 0] | 10               | new ShortestPathWithVertexWeight.VerticesOnlyWeighted()
        [[0, 2]: 0, [0, 3]: 0,
         [1, 0]: 0, [1, 2]: 0,
         [3, 2]: 0, [4, 3]: 0,
         [2, 4]: 0, [4, 1]: 0,
         [4, 6]: 0, [5, 4]: 0,
         [3, 5]: 0, [6, 5]: 0] | [0: 1, 1: 4, 2: 1, 3: 1, 4: 6, 5: 4, 6: 2] | 6    | 0  | [6, 5, 3, 0] | 10               | new ShortestPathWithVertexWeight.VerticesOnlyWeighted()
        [[0, 2]: 3, [0, 3]: 7,
         [1, 0]: 4, [1, 2]: 6,
         [3, 2]: 8, [4, 3]: 2,
         [2, 4]: 11, [4, 1]: 5,
         [4, 6]: 2, [5, 4]: 10,
         [3, 5]: 5, [6, 5]: 3] | [0: 1, 1: 8, 2: 3, 3: 3, 4: 5, 5: 4, 6: 2] | 6    | 0  | [6, 4, 3, 0] | 22               | new ShortestPathWithVertexWeight.EdgesAndVerticesWeighted()
        [[0, 2]: 3, [0, 3]: 7,
         [1, 0]: 4, [1, 2]: 6,
         [3, 2]: 8, [4, 3]: 2,
         [2, 4]: 11, [4, 1]: 5,
         [4, 6]: 2, [5, 4]: 10,
         [3, 5]: 5, [6, 5]: 3] | [0: 1, 1: 2, 2: 3, 3: 3, 4: 5, 5: 4, 6: 2] | 6    | 0  | [6, 4, 1, 0] | 21               | new ShortestPathWithVertexWeight.EdgesAndVerticesWeighted()

    }

}
