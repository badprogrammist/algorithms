package ru.ildar.algorithm.datastructure.stack;

import java.util.NoSuchElementException;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class LinkedStack<E> implements Stack<E> {

    private Node head;
    private int size;

    public LinkedStack() {
        head = null;
        size = 0;
    }

    @Override
    public void push(E data) {
        if (head == null) {
            head = new Node(data, null);
        } else {
            head = new Node(data, head);
        }
        size++;
    }

    @Override
    public E pop() throws NoSuchElementException {
        if (head == null) {
            throw new NoSuchElementException("Stack is empty");
        }
        E data = head.getData();
        head = head.getPrev();
        size--;
        return data;
    }

    @Override
    public E peek() {
        if (head != null) {
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
                sb.append("->");
            }

            node = node.prev;
        }
        return sb.toString();
    }

    private class Node {
        private E data;
        private Node prev;

        Node(E data, Node prev) {
            this.data = data;
            this.prev = prev;
        }

        E getData() {
            return data;
        }

        Node getPrev() {
            return prev;
        }
    }
}
