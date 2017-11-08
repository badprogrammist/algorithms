package ru.ildar.algorithm.datastructure.queue;

import java.util.NoSuchElementException;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public interface Queue<E> {


    void add(E e);

    E poll() throws NoSuchElementException;

    E peek();

    int size();

}
