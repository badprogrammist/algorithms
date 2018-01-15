package ru.ildar.algorithm.graph.tasks

import ru.ildar.algorithm.graph.Graph
import ru.ildar.algorithm.graph.GraphBuilder
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class EvaluationExpressionTest extends Specification {

    def "Test of evaluating expression that is represented as graph"() {
        given: "Expression as graph"
        // Using Adjacency matrix instead Adjacency List because
        // Adjacency List gets adjacency vertices in incorrect order
        GraphBuilder gb = GraphBuilder.adjacencyMatrix(true)
        edges.each { edge -> gb.edge(edge[0], edge[1]) }
        Graph graph = gb.create()

        when: "Trying to evaluate expression"
        EvaluatingExpression e = new EvaluatingExpression()
        double result = e.eval(graph, verticesData as String[])

        then: "Result should equals expected"
        result == expectedResult

        where:
        edges                             | verticesData                                            | expectedResult
        [[0, 1], [0, 2], [1, 3],
         [1, 4], [2, 5], [2, 6],
         [3, 7], [3, 8], [6, 9], [6, 10]] | ["+", "/", "+", "*", "5", "2", "*", "3", "5", "3", "4"] | 17

        [[0, 1], [0, 2], [1, 3],
         [1, 4], [2, 4], [2, 5],
         [4, 6], [4, 7]]                  | ["+", "+", "/", "2", "*", "5", "3", "5"]                | 20
    }

}
