package ru.ildar.algorithm.datastructure.queue;

import ru.ildar.algorithm.datastructure.Iterator;
import ru.ildar.algorithm.datastructure.list.ArrayList;
import ru.ildar.algorithm.datastructure.list.List;
import ru.ildar.algorithm.datastructure.tree.SearchTree;

import java.util.NoSuchElementException;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class TreePriorityQueue<E extends Comparable<E>> extends SearchTree<E> implements PriorityQueue<E> {

    @Override
    public E getMin() throws NoSuchElementException {
        if (size() == 0) {
            throw new NoSuchElementException("The queue is empty");
        }
        return getMin(getRoot()).data;
    }

    @Override
    public E getMax() throws NoSuchElementException {
        if (size() == 0) {
            throw new NoSuchElementException("The queue is empty");
        }
        return getMax(getRoot()).data;
    }

    @Override
    public E pollMin() throws NoSuchElementException {
        if (size() == 0) {
            throw new NoSuchElementException("The queue is empty");
        }
        Node<E> node = getMin(getRoot());
        remove(node);
        return node.data;
    }

    @Override
    public E pollMax() throws NoSuchElementException {
        if (size() == 0) {
            throw new NoSuchElementException("The queue is empty");
        }
        Node<E> node = getMax(getRoot());
        remove(node);
        return node.data;
    }

    @Override
    public Iterator<E> ascIterator() {
        return new AscentOrderIterator<E>(this);
    }

    @Override
    public List<E> toList() {
        List<E> list = new ArrayList<>(size());
        fillList(list, getRoot());
        return list;
    }

    private void fillList(List<E> list, Node<E> node) {
        if(node != null) {
            fillList(list, node.left);
            list.add(node.data);
            fillList(list, node.right);
        }
    }

    /**
     * Traverse through elements by descent order
     */
    protected static class AscentOrderIterator<E extends Comparable<E>> implements Iterator<E> {

        private TreePriorityQueue<E> tree;
        private Node<E> next;

        AscentOrderIterator(TreePriorityQueue<E> tree) {
            this.tree = tree;
            this.next = tree.getMin(tree.getRoot());
        }

        @Override
        public boolean hasNext() {
            return next != null && next.data != null;
        }

        @Override
        public E next() {
            Node<E> prev = next;
            if(prev != null) {
                next = tree.successor(next);
                return prev.data;
            }
            return null;
        }

    }

}
