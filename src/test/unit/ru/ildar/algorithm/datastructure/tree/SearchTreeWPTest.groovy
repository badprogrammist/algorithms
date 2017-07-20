package ru.ildar.algorithm.datastructure.tree

import ru.ildar.algorithm.datastructure.Iterator
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class SearchTreeWPTest extends Specification {

    def "Test of adding and searching elements"() {
        given: "A SearchTreeWP instance"
        SearchTreeWP<Integer> tree = new SearchTreeWP()

        and: "Elements"
        int[] elements = [2, 1, 7, 5, 8, 3, 6]

        when: "Add elements"
        elements.each { e ->
            tree.add(e)
        }

        then: "Each element should contain in tree"
        areAllElementsContainInTree(tree, elements)

        and: "Size should be equaled 7"
        tree.size() == 7

        and: "Trying to traverse through each element"
        traverseThroughTree(tree, elements)
    }

    def "Test of removing element"() {
        given: "Tree"
        SearchTreeWP<Integer> tree = new SearchTreeWP()
        elements.each { e ->
            tree.add(e)
        }

        when: "Try to remove element"
        tree.remove(toRemove)

        then: "There is no removed element"
        !tree.contains(toRemove)

        and: "Tree size is expected"
        tree.size() == expected.length

        and: "Trying to traverse through each element"
        traverseThroughTree(tree, expected)

        where:
        elements                 | toRemove | expected
        [2, 1, 7, 4, 8, 3, 6, 5] | 3        | [2, 1, 7, 4, 8, 6, 5] as int[]
        [2, 1, 7, 4, 8, 3, 6, 5] | 6        | [2, 1, 7, 4, 8, 3, 5] as int[]
        [2, 1, 7, 4, 8, 3, 6, 5] | 4        | [2, 1, 7, 5, 8, 3, 6] as int[]
    }

    def areAllElementsContainInTree(SearchTreeWP<Integer> tree, int[] elements) {
        for (int i = 0; i < elements.length; i++) {
            if (!tree.contains(elements[i])) {
                return false
            }
        }
        return true
    }

    def traverseThroughTree(SearchTreeWP<Integer> tree, int[] elements) {
        List<Integer> control = new ArrayList(Arrays.asList(elements))
        Iterator<Integer> iter = tree.iterator()
        while (iter.hasNext()) {
            int e = iter.next()
            control.removeAll(Arrays.asList(e))
        }
        return control.size() == 0
    }

}
