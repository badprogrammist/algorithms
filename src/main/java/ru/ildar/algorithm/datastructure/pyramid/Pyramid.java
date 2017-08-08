package ru.ildar.algorithm.datastructure.pyramid;

import ru.ildar.algorithm.datastructure.list.ArrayList;
import ru.ildar.algorithm.datastructure.list.List;

import java.util.NoSuchElementException;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class Pyramid<E extends Comparable<E>> {

    private List<E> array;

    public Pyramid() {
        array = new ArrayList<>();
    }

    public Pyramid(int n) {
        array = new ArrayList<>(n);
    }

    public Pyramid(E[] a) {
        array = new ArrayList<>(a.length);
        for(E e : a) {
            array.add(e);
        }
        for(int i = array.size(); i >= 0; i--) {
            bubble_down(i);
        }
    }

    public void add(E element) {
        array.add(element);
        bubble_up(array.size() - 1);
    }

    public E pollMin() throws NoSuchElementException {
        if(array.size() == 0) {
            throw new NoSuchElementException("The structure is empty");
        }
        E min = array.get(0);
        swap(array.size() - 1, 0);
        array.remove(array.size() - 1);
        bubble_down(0);
        return min;
    }

    private void bubble_up(int k) {
        if(parent(k) != -1) {
            if(array.get(parent(k)).compareTo(array.get(k)) > 0) {
                swap(k, parent(k));
                bubble_up(parent(k));
            }
        }
    }

    private void bubble_down(int p) {
        int largest;
        int l = left(p);
        int r = right(p);
        if(l < array.size() && array.get(p).compareTo(array.get(l)) > 0) {
            largest = l;
        } else {
            largest = p;
        }
        if(r < array.size() && array.get(largest).compareTo(array.get(r)) > 0) {
            largest = r;
        }
        if(largest != p) {
            swap(p, largest);
            bubble_down(largest);
        }
    }

    private void swap(int k1, int k2) {
        E element = array.get(k1);
        array.set(array.get(k2), k1);
        array.set(element, k2);
    }

    public int size() {
        return array.size();
    }

    private int left(int k) {
        return k == 0 ? 1 : 2 * k;
    }

    private int right(int k) {
        return k == 0 ? 2 : 2 * k + 1;
    }

    private int parent(int k) {
        return k == 0 ? -1 : k / 2;
    }
}
