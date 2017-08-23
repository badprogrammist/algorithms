package ru.ildar.algorithm.datastructure.list;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public interface List<E> {

    void add(E e);
    E get(int index);
    void set(E e, int index);
    E remove(int index);
    int indexOf(E e);
    int size();
    E[] toArray();

}
