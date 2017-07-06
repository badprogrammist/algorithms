package ru.ildar.algorithm.datastructure.tree;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public interface TreeIterator<E extends Comparable<E>> {
    boolean hasNext();
    E next();
}
