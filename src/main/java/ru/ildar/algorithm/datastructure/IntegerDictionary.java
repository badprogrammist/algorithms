package ru.ildar.algorithm.datastructure;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class IntegerDictionary {

    private boolean[] arr;
    private static final int DEFAULT_SIZE = 256;

    public IntegerDictionary() {
        arr = new boolean[DEFAULT_SIZE];
    }

    public IntegerDictionary(int size) {
        arr = new boolean[size];
    }

    public void put(int index) {
        if(index < arr.length) {
            arr[index] = true;
        }
    }

    public void remove(int index) {
        if(index < arr.length) {
            arr[index] = false;
        }
    }

    /*
     * Returns true if the i'th index is set.
     */
    public boolean search(int index) {
        return index < arr.length && arr[index];
    }


}
