package ru.ildar.algorithm.datastructure

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class UnionFindTest extends Specification {

    def "Test of union-find structure"() {
        given: "Some points the number 10 and union-find structure"
        UnionFind uf = new UnionFind(size)

        when: "Set connections"
        connections.each { points -> uf.union(points[0], points[1]) }

        then: "Check the connectivity of connected points"
        checkConnectivity(uf, connections)

        and: "Check the number of components"
        components.length == uf.getComponents()

        and: "There are should not connection between different components"
        notConnectionBetweenComponents(uf, components)

        where:
        size | connections                         | components
        10   | [[4, 3], [3, 8], [6, 5], [9, 4],
                [2, 1], [8, 9], [5, 0], [7, 2],
                [6, 1], [1, 0], [6, 7]] as int[][] | [[1, 2, 7, 5, 0, 6], [3, 8, 9, 4]] as int[][]
    }

    boolean checkConnectivity(UnionFind uf, int[][] connections) {
        for (int[] points in connections) {
            if (!uf.isConnect(points[0], points[1])) {
                return false
            }
        }
        return true
    }

    boolean notConnectionBetweenComponents(UnionFind uf, int[][] components) {
        int comp1 = 0
        int comp2 = 1

        while (comp2 < components.length) {
            for (int point1 in components[comp1]) {
                for (int point2 in components[comp2]) {
                    if (uf.isConnect(point1, point2)) {
                        return false
                    }
                }
            }
            comp1++
            comp2++
        }

        return true
    }

}
