package ru.ildar.algorithm.datastructure.tree

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class BinaryTreeTest extends Specification {

    def "Test of adding and searching elements"() {
        given: "A BinaryTree instance"
        BinaryTree<Integer> tree = new BinaryTree()

        and: "Elements"
        int[] elements = [2, 1, 7, 5, 8, 3, 6]

        when: "Add elements"
        elements.each { e->
            tree.add(e)
        }

        then: "Each element should contain in tree"
        isAllElementsContainInTree(tree, elements)

        and: "Size should be equaled 7"
        tree.size() == 7

        when: "Trying to traverse through each element"
        List<Integer> control = new ArrayList(Arrays.asList(elements))
        TreeIterator<Integer> iter = tree.iterator()
        while(iter.hasNext()) {
            int e = iter.next()
            control.removeAll(Arrays.asList(e))
        }

        then: "Traversed through all elements"
        control.size() == 0
    }

    def isAllElementsContainInTree(BinaryTree<Integer> tree, int[] elements) {
        for(int i = 0; i < elements.length; i++) {
            if(!tree.contains(elements[i])) {
                return false
            }
        }
        return true
    }

}
