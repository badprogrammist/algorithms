package ru.ildar.algorithm.dynamic

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class TriangulationTest extends Specification {

    def "Test of triangulation of a polygon"() {
        when: "Trying to triangulate a polygon"
        Triangulation alg = new Triangulation()
        alg.triangulate(points as double[][])

        then: "The diagonals should equals expected"
        alg.getDiagonals() == diagonals as int[][]

        and: "The weight should equals expected"
        alg.getWeight() == weight as double

        where:
        points   | diagonals | weight
        [[1, 3],
         [3, 5],
         [6, 5],
         [8, 4],
         [8, 2],
         [6, 1],
         [3, 1]] | [[2, 4],
                    [2, 5],
                    [1, 5],
                    [1, 6]]  | 16.605551275463988

        [[0, 0],
         [0, 2],
         [1, 2],
         [2, 1],
         [1, 0]] | [[2, 4],
                    [0, 2]]  | 4.23606797749979
    }

}
