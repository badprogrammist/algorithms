package ru.ildar.algorithm.datastructure.stack;

import java.util.NoSuchElementException;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public interface Stack<E> {

    void push(E e);

    E pop() throws NoSuchElementException;

    E peek();

    int size();

    void reverse();

    E[] toArray();

}
