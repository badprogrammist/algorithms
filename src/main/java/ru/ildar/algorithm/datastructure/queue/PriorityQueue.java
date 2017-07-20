package ru.ildar.algorithm.datastructure.queue;

import ru.ildar.algorithm.datastructure.Iterator;
import ru.ildar.algorithm.datastructure.Structure;
import ru.ildar.algorithm.datastructure.list.List;

import java.util.NoSuchElementException;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public interface PriorityQueue<E extends Comparable<E>> extends Structure<E> {

    E getMin() throws NoSuchElementException;

    E getMax() throws NoSuchElementException;

    E pollMin() throws NoSuchElementException;

    E pollMax() throws NoSuchElementException;

    Iterator<E> ascIterator();

    List<E> toList();

}
