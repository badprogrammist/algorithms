package ru.ildar.algorithm.datastructure.tree;

import ru.ildar.algorithm.datastructure.stack.LinkedStack;
import ru.ildar.algorithm.datastructure.stack.Stack;

import java.util.NoSuchElementException;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class BinaryTreeIterator<E extends Comparable<E>> implements TreeIterator<E> {

    private Stack<BinaryNode<E>> path = new LinkedStack<>();
    private int traversedSize = 0;
    private int treeSize;
    private BinaryNode<E> root;

    public BinaryTreeIterator(BinaryNode<E> root, int treeSize) {
        this.root = root;
        this.treeSize = treeSize;
    }

    @Override
    public boolean hasNext() {
        return traversedSize < treeSize;
    }

    @Override
    public E next() throws NoSuchElementException {
        E next = getNext(null);
        traversedSize++;
        return next;
    }

    private E getNext(BinaryNode<E> last) throws NoSuchElementException {
        if (path.size() == 0) {
            path.put(root);
            return root.getData();
        } else if ((last != null && path.getLast().getLeft() != last && path.getLast().getRight() != last)
                || (last == null && path.getLast().getLeft() != null)) {
            E data = path.getLast().getLeft().getData();
            path.put(path.getLast().getLeft());
            return data;
        } else if (path.getLast().getRight() != null && path.getLast().getRight() != last) {
            E data = path.getLast().getRight().getData();
            path.put(path.getLast().getRight());
            return data;
        } else {
            return getNext(path.poll());
        }
    }


}
