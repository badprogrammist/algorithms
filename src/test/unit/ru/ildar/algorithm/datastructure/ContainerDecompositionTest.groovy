package ru.ildar.algorithm.datastructure

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class ContainerDecompositionTest extends Specification {


    def "Test of better first algorithm"() {
        given: "An array of test elements"
        double[] elements = [0.6, 0.5, 0.4, 0.1, 0.3, 0.4, 0.9, 0.4, 0.1, 0.3, 0.2, 0.8, 0.5, 0.5]

        when: "Try to decompose elements by containers"
        ContainerDecomposition decomposer = ContainerDecomposition.decomposeByBetterFirst(elements)

        then: "The count of containers should be equaled expected"
        decomposer.containersCount() == 7
    }

    def "Test of worse first algorithm"() {
        given: "An array of test elements"
        double[] elements = [0.6, 0.5, 0.4, 0.1, 0.3, 0.4, 0.9, 0.4, 0.1, 0.3, 0.2, 0.8, 0.5, 0.5]

        when: "Try to decompose elements by containers"
        ContainerDecomposition decomposer = ContainerDecomposition.decomposeByWorseFirst(elements)

        then: "The count of containers should be equaled expected"
        decomposer.containersCount() == 7
    }

}
