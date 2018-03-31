package ru.ildar.algorithm.graph

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class ShortestPathTest extends Specification {

    def "Test of Dijkstra Algorithm"() {
        given: "Some undirected-weighted graph"
        GraphBuilder gb = GraphBuilder.adjacencyList(false)
        edges.each { edge, weight -> gb.edge(edge[0], edge[1], weight) }
        Graph graph = gb.create()
        and: "And vertices that point the from and the to it should find the path"

        when: "Trying to find shortest path"
        ShortestPath.Algorithm algorithm = new ShortestPath.DijkstrasAlgoritm()
        algorithm.find(graph, from, to)
        int[] path = algorithm.getPath()

        then: "The path should equals expected"
        path == expectedPath

        where:
        edges                              | from | to | expectedPath
        [[0, 1]: 4, [0, 2]: 3, [0, 3]: 7,
         [1, 4]: 5, [1, 2]: 6, [2, 4]: 11,
         [2, 3]: 8, [3, 4]: 2, [3, 5]: 5,
         [4, 5]: 10, [4, 6]: 2, [5, 6]: 3] | 0    | 6  | [0, 1, 4, 6] as int[]

        [[0, 1]: 4, [0, 2]: 3, [0, 3]: 7,
         [1, 4]: 5, [1, 2]: 6, [2, 4]: 11,
         [2, 3]: 8, [3, 4]: 2, [3, 5]: 5,
         [4, 5]: 10, [4, 6]: 2, [5, 6]: 3] | 1    | 5  | [1, 4, 6, 5] as int[]

        [[0, 1]: 7, [0, 2]: 9, [0, 5]: 14,
         [2, 5]: 2, [2, 3]: 11, [2, 1]: 10,
         [5, 4]: 9, [4, 3]: 8, [1, 3]: 15] | 0    | 3  | [0, 2, 3] as int[]
    }

}
