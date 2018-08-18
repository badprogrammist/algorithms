package ru.ildar.algorithm.heuristic

import ru.ildar.algorithm.graph.Graph
import ru.ildar.algorithm.graph.GraphBuilder
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class LocalSearchMethodTest extends Specification {

    def "Test of Local Search method for solving Travelling Salesman Problem"() {
        given: "A graph of TSP"
        GraphBuilder gb = GraphBuilder.adjacencyList(false)
        edges.each { edge, weight -> gb.edge(edge[0], edge[1], weight) }
        Graph tsp = gb.create()

        when: "Trying to solve problem"
        LocalSearchingMethod alg = new LocalSearchingMethod()
        alg.solve(tsp, start)

        then: "The solution path should equals expected"
        equalsAny(alg.getSolution(), alg.getSolutionCost(), expectedPaths as Map<int[], Double>)

        where:
        edges                              | start | expectedPaths
        [[0, 1]: 5, [0, 2]: 2,
         [0, 3]: 12, [1, 4]: 3,
         [1, 3]: 10, [2, 3]: 8,
         [2, 4]: 4, [3, 4]: 3]             | 0     | [[0, 1, 4, 3, 2, 0]: 21,
                                                      [0, 2, 3, 4, 1, 0]: 21]

        [[0, 1]: 4, [0, 2]: 9,
         [0, 3]: 7, [1, 2]: 6,
         [1, 4]: 5, [2, 3]: 8,
         [2, 4]: 11, [3, 4]: 2,
         [3, 6]: 12, [3, 5]: 5,
         [4, 5]: 10, [4, 6]: 2, [5, 6]: 3] | 0     | [[0, 2, 3, 5, 6, 4, 1, 0]: 36,
                                                      [0, 1, 4, 6, 5, 3, 2, 0]: 36,
                                                      [0, 2, 1, 4, 6, 5, 3, 0]: 37,
                                                      [0, 3, 5, 6, 4, 1, 2, 0]: 37,]
    }

    boolean equalsAny(int[] result, double cost, Map<int[], Double> expectedPaths) {
        for (Map.Entry<int[], Double> path : expectedPaths.entrySet()) {
            if (path.getKey() == result && path.getValue() == cost) {
                return true
            }
        }

        return false
    }

}
