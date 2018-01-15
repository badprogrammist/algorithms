package ru.ildar.algorithm.graph;


import java.util.Iterator;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public interface Graph {

    boolean isDirected();

    int getVerticesCount();

    int getEdgesCount();

    int getDegree(int vertex);

    void insertEdge(int v1, int v2, double weight);

    int[] getAdjacentVertices(int vertex);

    boolean isAdjacent(int v1, int v2);

    void validateVertex(int vertex) throws IllegalArgumentException;

    Iterator<Integer> getAdjacentVerticesIterator(int vertex);

    Graph reversed();

    Graph square();
}
