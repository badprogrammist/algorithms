package ru.ildar.algorithm.datastructure;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public interface Structure<E> {

    void add(E e);

    boolean contains(E e);

    boolean remove(E e);

    Iterator<E> iterator();

    int size();

}
