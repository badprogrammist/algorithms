package ru.ildar.algorithm.heuristic

import ru.ildar.algorithm.graph.Graph
import ru.ildar.algorithm.graph.GraphBuilder
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class SimulatedAnnealingMethodTest extends Specification {

    def "Test of Simulated Annealing method for solving Travelling Salesman Problem"() {
        when: "Trying to solve problem"
        SimulatedAnnealingMethod alg = new SimulatedAnnealingMethod(temperature, stepsNumber, boltzmannConstant)
        alg.solve(tsp, start)

        then: "The solution's cost should be close to perfect solution"
        Math.abs(alg.getSolutionCost() - perfectSolutionCost) < 5000

        where:
        tsp                                | start | temperature | stepsNumber | boltzmannConstant                                    | perfectSolutionCost
        TSPInstances.getCapitalsOfUSATSP() | 0     | 1           | 500         | TSPInstances.MEDIUM_DISTANCE_BETWEEN_CAPITALS_OF_USA | TSPInstances.getCapitalsOfUSATSPSalvationCost()

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
