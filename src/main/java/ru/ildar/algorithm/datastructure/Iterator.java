package ru.ildar.algorithm.datastructure;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public interface Iterator<E> {
    boolean hasNext();
    E next();
}
