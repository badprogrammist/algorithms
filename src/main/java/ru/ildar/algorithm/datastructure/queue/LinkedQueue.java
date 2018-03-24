package ru.ildar.algorithm.datastructure.queue;

import java.util.NoSuchElementException;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class LinkedQueue<E> implements Queue<E> {

    private Node head;
    private Node tail;
    private int size;

    public LinkedQueue() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(E e) {
        if (size() == 0) {
            head = new Node(e);
            tail = head;
        } else {
            Node node = new Node(e);
            tail.setPrev(node);
            tail = node;
        }
        size++;
    }

    @Override
    public E poll() throws NoSuchElementException {
        if(head == null) {
            throw new NoSuchElementException("Queue is empty");
        }

        E data = head.getData();

        if (size() == 1) {
            head = null;
            tail = null;
        } else {
            head = head.getPrev();
        }

        size--;

        return data;
    }

    @Override
    public E peek() {
        if(head != null) {
            return head.getData();
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node node = head;

        while (node != null) {
            sb.append(node.data);

            if (node.prev != null) {
                sb.append("<-");
            }

            node = node.prev;
        }

        return sb.toString();
    }

    private class Node {
        private E data;
        private Node prev;

        Node(E data) {
            this.data = data;
        }

        E getData() {
            return data;
        }

        Node getPrev() {
            return prev;
        }

        void setPrev(Node prev) {
            this.prev = prev;
        }
    }

}
