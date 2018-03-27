package ru.ildar.algorithm.datastructure;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class UnionFind {

    private int[] parentOf;
    private int[] subTreeSize;
    private int components;

    public UnionFind(int size) {
        parentOf = new int[size];
        subTreeSize = new int[size];
        components = size;

        for (int i = 0; i < size; i++) {
            parentOf[i] = i;
            subTreeSize[i] = 1;
        }
    }

    private int getComponent(int e) {
        int parent = parentOf[e];

        while (parentOf[parent] != parent) {
            parent = parentOf[parent];
        }

        return parent;
    }

    public boolean union(int e1, int e2) {
        int p1 = getComponent(e1);
        int p2 = getComponent(e2);

        if (p1 == p2) {
            return false;
        }

        if (subTreeSize[p1] <= subTreeSize[p2]) {
            parentOf[p1] = p2;
            subTreeSize[p2] += subTreeSize[p1];
        } else {
            parentOf[p2] = p1;
            subTreeSize[p1] += subTreeSize[p2];
        }

        components--;

        return true;
    }

    public boolean isConnect(int e1, int e2) {
        return getComponent(e1) == getComponent(e2);
    }

    public int getComponents() {
        return components;
    }

}
