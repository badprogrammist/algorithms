package ru.ildar.algorithm.datastructure.tree

import ru.ildar.algorithm.datastructure.Iterator
import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class SearchTreeTest extends Specification {

    def "Test of adding and searching elements"() {
        given: "A SearchTree instance"
        SearchTree<Integer> tree = new SearchTree()

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

        and: "Trying to traverse through tree in descent order"
        traverseThroughTreeInPropertyOrder(tree, [8, 7, 6, 5, 3, 2, 1] as int[])
    }

    def "Test of removing element"() {
        given: "Tree"
        SearchTree<Integer> tree = new SearchTree()
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
        traverseThroughTreeInPropertyOrder(tree, expected)

        where:
        elements                 | toRemove | expected
        [2, 1, 7, 4, 8, 3, 6, 5] | 3        | [8, 7, 6, 5, 4, 2, 1] as int[]
        [2, 1, 7, 4, 8, 3, 6, 5] | 6        | [8, 7, 5, 4, 3, 2, 1] as int[]
        [2, 1, 7, 4, 8, 3, 6, 5] | 4        | [8, 7, 6, 5, 3, 2, 1] as int[]
    }

    def areAllElementsContainInTree(SearchTree<Integer> tree, int[] elements) {
        for (int i = 0; i < elements.length; i++) {
            if (!tree.contains(elements[i])) {
                return false
            }
        }
        return true
    }

    def traverseThroughTreeInPropertyOrder(SearchTree<Integer> tree, int[] elements) {
        int i = 0;
        Iterator<Integer> iter = tree.iterator()
        while (iter.hasNext()) {
            int e = iter.next()
            if(e != elements[i]) {
                return false;
            }
            i++
        }
        return true;
    }

}
