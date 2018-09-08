package ru.ildar.algorithm.heuristic.tasks

import ru.ildar.algorithm.graph.Graph
import ru.ildar.algorithm.graph.GraphBuilder
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class BandwidthReductionTest extends Specification {

    def "Test of Simulated Annealing method for solving Bandwidth minimization problem"() {
        given: "Some graph that represents bandwidth problem"
        GraphBuilder gb = GraphBuilder.adjacencyMatrix(false)
        edges.each { edge -> gb.edge(edge[0], edge[1]) }
        Graph graph = gb.create()

        when: "Trying to solve problem"
        BandwidthReduction alg = new BandwidthReduction(temperature, stepsNumber, boltzmannConstant)
        alg.solve(graph)

        then: "The solution's cost should be close to perfect solution"
        Math.abs(alg.getMaxDistance() - perfectSolutionCost) <= delta

        where:
        edges                                                        | temperature | stepsNumber | boltzmannConstant | perfectSolutionCost | delta
        [[0, 7], [1, 6], [1, 7],
         [2, 5], [2, 6], [3, 4], [3, 5]]                             | 1           | 1000        | 0.1               | 1                   | 0

        [[20, 0], [20, 1], [20, 2], [20, 3],
         [19, 2], [19, 3], [19, 4], [19, 5],
         [18, 4], [18, 5], [18, 6], [18, 7],
         [17, 6], [17, 7], [17, 8], [17, 9],
         [16, 8], [16, 9], [16, 10], [16, 11],
         [15, 10], [15, 11], [15, 12], [15, 13], [14, 12], [14, 13]] | 1           | 1000        | 0.07              | 3                   | 2

    }


}
