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

    int[] getAdjacentEdges(int vertex);

    void validateVertex(int vertex) throws IllegalArgumentException;

    Iterator<Integer> getAdjacentEdgesIterator(int vertex);

    Graph reversed();
}
