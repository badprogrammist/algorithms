package ru.ildar.algorithm.graph.tasks

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class ArrangeIllBehavedChildrenTest extends Specification {

    def "Test of arrangement ill-behaved children"() {
        given: "The implementation of algorithm"
        def arrangement = new ArrangeIllBehavedChildren.StraightLineArrangement()

        when: "Trying to arrange children"
        arrangement.arrange(children as int[][])

        then: "The result should equals expected"
        arrangement.getLine() == expected as int[]

        where:
        children                 | expected
        [[0, 1], [1, 2]]         | [2, 1, 0]
        [[0, 1], [1, 2], [0, 2]] | [2, 1, 0]
        [[0, 1], [1, 2], [2, 3],
         [1, 3], [0, 3]]         | [3, 2, 1, 0]

    }

    def "Test of arrangement ill-behaved children of bad case"() {
        given: "The implementation of algorithm"
        def arrangement = new ArrangeIllBehavedChildren.StraightLineArrangement()

        when: "Trying to arrange children of bad case"
        arrangement.arrange(children as int[][])

        then: "An exception should be thrown"
        thrown(ArrangeIllBehavedChildren.CouldNotArrangeException.class)

        where:
        children                 | _
        [[0, 1], [1, 2], [2, 1]] | _
    }


}
