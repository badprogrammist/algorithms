package ru.ildar.algorithm.datastructure.list;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public interface List<E> {

    void add(E e);
    E get(int index);
    void remove(int index);
    int indexOf(E e);
    int size();

}
