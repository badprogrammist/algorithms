package ru.ildar.algorithm.graph

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class ColorizeTest extends Specification {

    def "Test of colorize the bipartite graph"() {
        given: "The bipartite graph"
        Graph graph = GraphBuilder.adjacencyList(false)
                .edge(0, 1)
                .edge(1, 3)
                .edge(3, 2)
                .edge(2, 0)
                .edge(2, 4)
                .edge(4, 5)
                .edge(5, 3)
                .edge(5, 6)
                .create()

        when: "Trying to colorize the bipartite graph"
        Colorize c = new Colorize()
        c.twoColor(graph, 0, Colorize.Color.WHITE)

        then: "The status of colorizing should be success"
        c.isSuccess()

        and: "The color of vertices should equals expected"
        c.getColor(0) == Colorize.Color.WHITE
        c.getColor(1) == Colorize.Color.BLACK
        c.getColor(2) == Colorize.Color.BLACK
        c.getColor(3) == Colorize.Color.WHITE
        c.getColor(4) == Colorize.Color.WHITE
        c.getColor(5) == Colorize.Color.BLACK
        c.getColor(6) == Colorize.Color.WHITE
    }

    def "Test of colorize the non bipartite graph"() {
        given: "The non bipartite graph"
        def graph = GraphBuilder.adjacencyList(false)
                .edge(0, 1)
                .edge(1, 2)
                .edge(2, 0)
                .create()

        when: "Trying to colorize the non bipartite graph"
        Colorize c = new Colorize()
        c.twoColor(graph, 0, Colorize.Color.WHITE)

        then: "The status of colorizing should be fail"
        !c.isSuccess()
    }

}
