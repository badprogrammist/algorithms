package ru.ildar.algorithm.graph.tasks

import ru.ildar.algorithm.graph.Graph
import ru.ildar.algorithm.graph.GraphBuilder
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class UnionFindOfDirectedGraphTest extends Specification {

    @Shared
    UnionFindOfDirectedGraph uf

    def setupSpec() {
        def edges = [[0, 1]: 10, [0, 2]: 5, [0, 3]: 15,
                     [1, 2]: 4, [1, 5]: 15, [1, 4]: 9,
                     [2, 3]: 3, [2, 5]: 8, [3, 6]: 16,
                     [4, 5]: 10, [4, 7]: 12, [5, 7]: 11,
                     [6, 5]: 15, [6, 7]: 13, [6, 2]: 6]
        GraphBuilder gb = GraphBuilder.adjacencyList(true)
        edges.each { edge, weight -> gb.edge(edge[0], edge[1], weight) }
        Graph graph = gb.create()
        uf = new UnionFindOfDirectedGraph(graph)
    }

    def "Test of the union-find structure of a directed weighted graph"() {
        when: "Trying to create components"
        boolean status = uf.union(v1, v2)

        then: "The should be merged"
        status == expectedStatus
        uf.find(v1) == v1Comp
        uf.find(v2) == v2Comp
        uf.getMinEdge(uf.find(v1)) == c1Min as int[]
        uf.getMinEdge(uf.find(v2)) == c2Min as int[]

        where:
        v1 | v2 | expectedStatus | v1Comp | v2Comp | c1Min  | c2Min
        5  | 7  | true           | 5      | 5      | [5, 7] | [5, 7]
        6  | 5  | true           | 5      | 5      | [5, 7] | [5, 7]
        6  | 7  | false          | 5      | 5      | [5, 7] | [5, 7]

        0  | 1  | true           | 0      | 0      | [0, 1] | [0, 1]
        0  | 2  | true           | 0      | 0      | [1, 2] | [1, 2]
        0  | 4  | true           | 0      | 0      | [1, 2] | [1, 2]

        4  | 3  | true           | 0      | 0      | [2, 3] | [2, 3]

        1  | 7  | true           | 0      | 0      | [2, 3] | [2, 3]


    }

}
