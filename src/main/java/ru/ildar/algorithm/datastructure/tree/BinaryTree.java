package ru.ildar.algorithm.datastructure.tree;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class BinaryTree<E extends Comparable<E>> implements Tree<E> {

    private BinaryNode<E> root = new BinaryNode<>(null, null, null);
    private int size = 0;

    @Override
    public void add(E e) {
        add(e, root);
        size++;
    }

    private void add(E e, BinaryNode<E> parent) {
        if (parent.getData() == null) {
            parent.setData(e);
        } else if (e.compareTo(parent.getData()) < 0) {
            if (parent.getLeft() == null) {
                parent.setLeft(new BinaryNode<>(null, null, null));
            }
            add(e, parent.getLeft());
        } else {
            if (parent.getRight() == null) {
                parent.setRight(new BinaryNode<>(null, null, null));
            }
            add(e, parent.getRight());
        }
    }

    @Override
    public boolean remove(E e) {
        return false;
    }

    @Override
    public TreeIterator<E> iterator() {
        return new BinaryTreeIterator<>(root, size());
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(E e) {
        return search(e, root) != null;
    }

    private BinaryNode<E> search(E e, BinaryNode<E> parent) {
        if (parent == null) {
            return null;
        }
        if (e.compareTo(parent.getData()) == 0) {
            return parent;
        }
        if (e.compareTo(parent.getData()) < 0) {
            return search(e, parent.getLeft());
        } else {
            return search(e, parent.getRight());
        }
    }

}
