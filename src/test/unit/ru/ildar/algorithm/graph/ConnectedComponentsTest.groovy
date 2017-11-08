package ru.ildar.algorithm.graph

import spock.lang.Specification
import ru.ildar.algorithm.datastructure.list.List;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class ConnectedComponentsTest extends Specification {

    def "Test of finding connected components of graph"() {
        given: "A graph witch has three components"
        Graph graph = GraphBuilder.adjacencyList(false)
                .edge(0, 1)
                .edge(1, 2)
                .edge(2, 0)
                .edge(2, 3)

                .edge(4, 5)

                .edge(6, 7)
                .edge(7, 8)
                .create()

        and: "Expected components"
        def expectedComponents = [
                0: [0, 2, 1, 3] as int[],
                1: [4, 5] as int[],
                2: [6, 7, 8] as int[]
        ]

        when: "Trying to find all connected components"
        def cc = new ConnectedComponents()
        def result = [:]
        cc.setComponentProcessor { int c, List<Integer> vertices ->
            result[c] = vertices.toArray()
        }

        cc.search(graph)

        then: "The found components should equals expected"
        result[0] == expectedComponents[0]
        result[1] == expectedComponents[1]
        result[2] == expectedComponents[2]


    }


}
