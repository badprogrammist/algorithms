package ru.ildar.algorithm.graph

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class CycleTest extends Specification {

    def "Test of solving whether is graph cyclical or not of cyclical graph"() {
        given: "Some cyclical graph"
        def graph = GraphBuilder.adjacencyList(false)
                .edge(0, 1)
                .edge(0, 5)
                .edge(0, 4)
                .edge(1, 2)
                .edge(1, 4)
                .edge(2, 3)
                .edge(3, 4)
                .create()

        when: "Trying to solve whether is graph cyclical"
        Cycle c = new Cycle()
        c.isCyclical(graph)

        then: "Graph should be cyclical"
        c.isCyclical()

        and: "Cyclical edge should equals expected"
        c.getV1() == 1
        c.getV2() == 4
    }

}
