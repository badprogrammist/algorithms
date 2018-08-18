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
        equalsAny(alg.getSolution(), expectedPaths as int[][])

        where:
        edges                  | start | stepsNumber | expectedCost | expectedPaths
        [[0, 1]: 5, [0, 2]: 2,
         [0, 3]: 12, [1, 4]: 3,
         [1, 3]: 10, [2, 3]: 8,
         [2, 4]: 4, [3, 4]: 3] | 0     | 10          | 21           | [[0, 1, 4, 3, 2, 0],
                                                                       [0, 2, 3, 4, 1, 0]]

        [[0, 1]: 4, [0, 2]: 9,
         [0, 3]: 7, [1, 2]: 6,
         [1, 4]: 5, [2, 3]: 8,
         [2, 4]: 11, [3, 4]: 2,
         [3, 5]: 5, [4, 5]: 10,
         [4, 6]: 2, [5, 6]: 3] | 0     | 10          | 36           | [[0, 2, 3, 5, 6, 4, 1, 0],
                                                                       [0, 1, 4, 6, 5, 3, 2, 0]]
    }

    boolean equalsAny(int[] result, int[][] expectedPaths) {
        for (int[] path: expectedPaths) {
            if (result == path) {
                return true
            }
        }

        return false
    }

}
