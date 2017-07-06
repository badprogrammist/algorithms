package ru.ildar.algorithm.datastructure.tree;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public interface Tree<E extends Comparable<E>> {

    void add(E e);
    boolean contains(E e);
    boolean remove(E e);
    TreeIterator<E> iterator();
    int size();

}
