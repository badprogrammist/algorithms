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

    void removeEdge(int v1, int v2);

    int[] getAdjacentVertices(int vertex);

    boolean isAdjacent(int v1, int v2);

    void validateVertex(int vertex);

    Iterator<Integer> getAdjacentVerticesIterator(int vertex);

    void setVertexWeight(int vertex, double weight);

    double getVertexWeight(int vertex);

    double getEdgeWeight(int v1, int v2);

    void setEdgeWeight(int v1, int v2, double weight);

    Graph reversed();

    Graph square();
}
