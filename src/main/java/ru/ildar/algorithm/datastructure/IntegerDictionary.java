package ru.ildar.algorithm.datastructure;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class IntegerDictionary {

    private boolean[] arr;
    private static final int DEFAULT_SIZE = 256;
    private int valuesSize = 0;

    public IntegerDictionary() {
        arr = new boolean[DEFAULT_SIZE];
    }

    public IntegerDictionary(int size) {
        arr = new boolean[size];
    }

    public void put(int index) throws IndexOutOfBoundsException {
        if(index < arr.length) {
            arr[index] = true;
            valuesSize++;
        } else {
            throw new IndexOutOfBoundsException("index: " + index + ", max: " + arr.length);
        }
    }

    public void remove(int index) throws IndexOutOfBoundsException {
        if(index < arr.length) {
            arr[index] = false;
            valuesSize--;
        } else {
            throw new IndexOutOfBoundsException("index: " + index + ", max: " + arr.length);
        }
    }

    /*
     * Returns true if the i'th index is set.
     */
    public boolean search(int index) {
        return index < arr.length && arr[index];
    }

    public int[] values() {
        int[] values = new int[valuesSize];
        int valueIndex = 0;
        for(int i = 0; i < arr.length; i++) {
            if(arr[i]) {
                values[valueIndex] = i;
                valueIndex++;
            }
        }
        return values;
    }

}
