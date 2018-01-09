package ru.ildar.algorithm.graph

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class ArticulationPointsTest extends Specification {

    def "Test of finding articulation points of graph"() {
        given: "A graph that has several articulation points"
        GraphBuilder gb = GraphBuilder.adjacencyList(false)
        edges.each { edge -> gb.edge(edge[0], edge[1]) }
        Graph graph = gb.create()

        when: "Trying to find articulation points of graph"
        ArticulationPoints ap = new ArticulationPoints()
        ap.find(graph, start)

        then: "The points that have been found should equals expected"
        ap.getPoints() == expectedPoints

        where:
        edges                                    | start | expectedPoints
        [[0, 1], [1, 2], [2, 0], [0, 3], [3, 4]] | 0     | [0, 3] as int[]
        [[0, 1], [1, 2], [2, 3]]                 | 0     | [1, 2] as int[]
        [[0, 1], [0, 2], [2, 1], [1, 6], [1, 4],
         [4, 5], [5, 3], [3, 1]]                 | 0     | [1] as int[]
    }

}
