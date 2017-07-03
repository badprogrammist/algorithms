package ru.ildar.algorithm.datastructure.stack;

import java.util.NoSuchElementException;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public interface Stack<E> {

    void put(E e);
    E poll() throws NoSuchElementException;
    int size();

}
