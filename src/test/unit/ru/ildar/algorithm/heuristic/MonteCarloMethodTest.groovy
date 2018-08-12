package ru.ildar.algorithm.heuristic

import ru.ildar.algorithm.graph.Graph
import ru.ildar.algorithm.graph.GraphBuilder
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class MonteCarloMethodTest extends Specification {

    def "Test of Monte-Carlo method for solving Travelling Salesman Problem"() {
        given: "A graph of TSP"
        GraphBuilder gb = GraphBuilder.adjacencyList(false)
        edges.each { edge, weight -> gb.edge(edge[0], edge[1], weight) }
        Graph tsp = gb.create()

        when: "Trying to solve problem"
        MonteCarloMethod alg = new MonteCarloMethod(stepsNumber)
        alg.solve(tsp, start)

        then: "The solution cost should be lower than expected value"
        alg.getSolutionCost() == expectedCost

        and: "The solution path should equals expected"
        alg.getSolution() == expectedPath as int[]

        where:
        edges                  | start | stepsNumber | expectedCost | expectedPath
        [[0, 1]: 5, [0, 2]: 2,
         [0, 3]: 12, [1, 4]: 3,
         [1, 3]: 10, [2, 3]: 8,
         [2, 4]: 4, [3, 4]: 3] | 0     | 100         | 16           | [0, 2, 3, 4, 1]
    }

}
