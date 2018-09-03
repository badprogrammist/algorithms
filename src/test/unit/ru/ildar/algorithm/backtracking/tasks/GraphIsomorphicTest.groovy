package ru.ildar.algorithm.backtracking.tasks

import ru.ildar.algorithm.graph.Graph
import ru.ildar.algorithm.graph.GraphBuilder
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class GraphIsomorphicTest extends Specification {

    def "Test of algorithm for testing whether two graphs are isomorphic to each other"() {
        given: "Some graphs"
        GraphBuilder gb = GraphBuilder.adjacencyList(false)
        edges1.each { edge -> gb.edge(edge[0], edge[1]) }
        Graph g1 = gb.create()
        gb = GraphBuilder.adjacencyList(false)
        edges2.each { edge -> gb.edge(edge[0], edge[1]) }
        Graph g2 = gb.create()

        when: "Testing whether two graphs are isomorphic to each other"
        GraphIsomorphic alg = new GraphIsomorphic()
        alg.test(g1, g2)

        then: "The result should equal expected"
        alg.isIsomorphic() == isIsomorphic

        and: "The mapping of graphs vertices should equals expected"
        alg.getMapping() == mapping as int[]

        where:
        edges1                           | edges2                           | isIsomorphic | mapping
        [[0, 1], [0, 2], [1, 3],
         [1, 4], [2, 3], [2, 4], [3, 4]] | [[0, 1], [0, 4], [0, 2],
                                            [1, 2], [1, 3], [2, 3], [3, 4]] | true         | [4, 0, 3, 1, 2]

        [[0, 1], [0, 2], [0, 4],
         [1, 3], [1, 5], [2, 7],
         [2, 8], [3, 7], [3, 6],
         [4, 6], [4, 9], [5, 8],
         [5, 9], [6, 8], [7, 9]]         | [[0, 1], [0, 4], [0, 5],
                                            [1, 8], [1, 3], [2, 5],
                                            [2, 9], [2, 8], [3, 6],
                                            [3, 9], [4, 7], [4, 9],
                                            [5, 6], [6, 7], [7, 8]]         | true         | [0, 5, 1, 6, 4, 2, 7, 3, 8, 9]
    }

}
