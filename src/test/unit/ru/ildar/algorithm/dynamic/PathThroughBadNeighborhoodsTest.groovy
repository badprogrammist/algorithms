package ru.ildar.algorithm.dynamic

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class PathThroughBadNeighborhoodsTest extends Specification {

    def "Test of finding shortest path through bad neighborhoods"() {
        when:
        PathThroughBadNeighborhoods alg = new PathThroughBadNeighborhoods()
        alg.find(neighborhoods as int[][])

        then:
        alg.getPath() == path as int[][]

        where:
        neighborhoods | path
        [[0, 0, 1],
         [0, 1, 0],
         [0, 0, 0]]   | [[0, 0], [1, 0],
                         [2, 0], [2, 1]]
    }

}
