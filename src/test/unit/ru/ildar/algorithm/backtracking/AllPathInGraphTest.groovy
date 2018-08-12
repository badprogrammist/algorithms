package ru.ildar.algorithm.backtracking

import ru.ildar.algorithm.graph.Graph
import ru.ildar.algorithm.graph.GraphBuilder
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class AllPathInGraphTest extends Specification {

    def "Test of constructing all paths between two vertices in a graph"() {
        given: "Some graph"
        GraphBuilder gb = GraphBuilder.adjacencyList(directed)
        edges.each { edge -> gb.edge(edge[0], edge[1]) }
        Graph graph = gb.create()

        when: "Trying to construct all paths between two vertices in a graph"
        AllPathsInGraph alg = new AllPathsInGraph()
        alg.find(graph, from, to)

        then: "Found paths should equal expected"
        for (int i = 0; i < alg.getSolutions().size(); i++) {
            int[] path = alg.getSolutions().get(i)
            assert path == expectedPaths[i] as int[]
        }

        and: "The amount of found solutions should equals expected"
        alg.getSolutions().size() == expectedPaths.size()

        where:
        edges                                                            | directed | from | to | expectedPaths
        [[0, 1], [0, 2], [1, 2], [1, 3], [1, 4], [2, 3], [3, 4]]         | false    | 2    | 3  | [[2, 3], [2, 1, 4, 3], [2, 1, 3], [2, 0, 1, 4, 3], [2, 0, 1, 3]]
        [[0, 1], [0, 2], [1, 2], [1, 3], [2, 4], [2, 3], [3, 4]]         | false    | 1    | 4  | [[1, 3, 4], [1, 3, 2, 4], [1, 2, 3, 4], [1, 2, 4], [1, 0, 2, 3, 4], [1, 0, 2, 4]]
        [[0, 1], [0, 2], [3, 0], [2, 3], [2, 1], [1, 4], [1, 5], [5, 2]] | true     | 0    | 3  | [[0, 2, 3], [0, 1, 5, 2, 3]]

    }

}
