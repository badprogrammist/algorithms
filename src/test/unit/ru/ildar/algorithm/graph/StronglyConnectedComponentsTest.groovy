package ru.ildar.algorithm.graph

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class StronglyConnectedComponentsTest extends Specification {

    def "Test of searching strongly connected components algorithm"() {
        given: "Some directed acyclic graph"
        GraphBuilder gb = GraphBuilder.adjacencyList(true)
        edges.each { edge -> gb.edge(edge[0], edge[1]) }
        Graph graph = gb.create()

        when: "Trying to search strongly connected components"
        StronglyConnectedComponents s = new StronglyConnectedComponents()
        s.search(graph)

        then: "The amount of found components should equals expected"
        s.getComponents() == expectedComponents.size()

        and: "Found components should equals expected"
        checkComponents(s, expectedComponents)

        where:
        edges                            | expectedComponents
        [[0, 1], [1, 4], [4, 5], [5, 6],
         [6, 4], [2, 0], [1, 2], [1, 3],
         [3, 0], [3, 7], [7, 5], [3, 5]] | [0: [4, 5, 6], 1: [7], 2: [0, 1, 2, 3]]

    }

    boolean checkComponents(StronglyConnectedComponents s, Map expectedComponents) {
        for (int c : expectedComponents.keySet()) {
            if (expectedComponents.get(c) as int[] != s.getComponent(c).toArray()) {
                return false
            }
        }

        return true
    }

}
