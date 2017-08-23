package ru.ildar.algorithm.sort

import ru.ildar.algorithm.datastructure.list.List
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class SetsUnionTest extends Specification {

    def "Test of sets union algorithm"() {
        given: "Sets s1 and s2"
        Integer[] s1 = [12, 15, 10, 7, 9, 6, 3]
        Integer[] s2 = [5, 8, 6, 4, 1, 11, 14, 13, 9]

        when: "Trying to union sets"
        List<Integer> r = SetsUnion.union(s1, s2)

        then: "Result of union should equals expected"
        equals(r, [1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15] as Integer[])
    }

    boolean equals(List<Integer> res, Integer[] exp) {
        if(res.size() != exp.length) {
            return false
        }
        for(int i = 0; i < exp.length; i++) {
            if(res.get(i) != exp[i]) {
                return false
            }
        }
        return true
    }

}
