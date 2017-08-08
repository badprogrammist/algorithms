package ru.ildar.algorithm.datastructure.list;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class ArrayList<E> implements List<E> {

    private final static int INITIAL_CAPACITY = 1;
    private Object[] array;
    private int size = 0;

    public ArrayList() {
        array = new Object[INITIAL_CAPACITY];
    }

    public ArrayList(int initialCapacity) {
        array = new Object[initialCapacity];
    }

    @Override
    public void add(E e) {
        if (size == array.length) {
            increaseCapacity();
        }
        array[size] = e;
        size++;
    }

    @Override
    public int indexOf(E e) {
        for (int i = 0; i < size; i++) {
            if (e == array[i]) {
                return i;
            }
        }
        return -1;
    }

    protected void increaseCapacity() {
        int capacity = 2 * size;
        Object[] newArray = new Object[capacity];
        System.arraycopy(array, 0, newArray, 0, array.length);
        array = newArray;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        return (E) array[index];
    }

    @Override
    public void set(E e, int index) throws IndexOutOfBoundsException  {
        array[index] = e;
    }

    @Override
    public E remove(int index) {
        E element = (E) array[index];
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        size--;

        if (array.length / 4 == size) {
            decreaseCapacity();
        }
        return element;

    }

    protected void decreaseCapacity() {
        int newSize = array.length / 2;
        Object[] newArray = new Object[newSize];
        System.arraycopy(array, 0, newArray, 0, newSize);
        array = newArray;
    }

    @Override
    public int size() {
        return size;
    }
}
