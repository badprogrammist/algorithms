package ru.ildar.algorithm.datastructure.list;

import ru.ildar.algorithm.datastructure.Iterator;

import java.util.NoSuchElementException;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class LinkedList<E> implements List<E> {

    private Node head;
    private int size = 0;

    @Override
    public void add(E element) {
        Node node = new Node(element);

        if (head == null) {
            head = node;
        } else {
            Node tail = head;

            while (tail.next != null) {
                tail = tail.next;
            }

            tail.next = node;
        }

        size++;
    }

    @Override
    public E get(int index) {
        return getNode(index).element;
    }

    @Override
    public void set(E element, int index) {
        Node node = getNode(index);
        node.element = element;
    }

    @Override
    public E remove(int index) {
        if (size() == 0) {
            throw new NoSuchElementException("There are no elements to remove");
        }

        E element = null;

        if (index == 0) {
            element = head.element;
            head = head.next;
        } else  {
            Node prev = getNode(index - 1);
            element = prev.next.element;
            prev.next = prev.next.next;
        }
        size--;

        return element;
    }

    private Node getNode(int index) {
        if (index >= size || index < 0) {
            throw new NoSuchElementException("Index is outside of bounds");
        }

        int idx = 0;
        Node node = head;

        while (idx != index) {
            node = node.next;
            idx++;
        }

        return node;
    }

    @Override
    public int indexOf(E element) {
        if (size() == 0) {
            throw new NoSuchElementException("The list is empty");
        }

        int idx = 0;
        Node node = head;

        while (node.element != element && idx < size()) {
            node = node.next;
            idx++;
        }

        return node.element == element ? idx : -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E[] toArray() {
        Object[] a = new Object[size()];
        int idx = 0;

        Node node = head;

        while (node.next != null) {
            a[idx] = node;
            node = node.next;
            idx++;
        }
        return (E[]) a;
    }

    public Iterator<E> iterator() {
        return new LinkedListIterator(head);
    }

    private class Node {
        E element;
        Node next;

        public Node(E element) {
            this.element = element;
        }
    }

    private class LinkedListIterator implements Iterator<E> {

        private Node node;

        LinkedListIterator(Node node) {
            this.node = node;
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public E next() {
            E element = node.element;
            node = node.next;

            return element;
        }

    }
}
