package ru.ildar.algorithm.graph;

import java.util.Iterator;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public abstract class AbstractGraph implements Graph {

    private int verticesCount;
    private int edgesCount;
    private boolean directed;

    private int[] degrees;

    public AbstractGraph(int verticesCount,  boolean directed) {
        this.verticesCount = verticesCount;
        this.edgesCount = 0;
        this.directed = directed;
        initDegrees();
    }

    private void initDegrees() {
        degrees = new int[verticesCount];
        for (int i = 0; i < verticesCount; i++) {
            degrees[i] = 0;
        }
    }

    protected void incrementDegree(int vertex) {
        validateVertex(vertex);
        degrees[vertex]++;
    }

    @Override
    public void insertEdge(int v1, int v2, double weight) {
        validateVertex(v1);
        validateVertex(v2);

        createEdge(v1, v2, weight);
        if (!isDirected()) {
            createEdge(v2, v1, weight);
        }

        edgesCount++;
    }

    @Override
    public Graph reversed() {
        Graph reversed = copyEmpty();

        for (int v = 0; v < getVerticesCount(); v++) {
            Iterator<Integer> iter = getAdjacentEdgesIterator(v);

            while (iter.hasNext()) {
                int adjacent = iter.next();
                reversed.insertEdge(adjacent, v, 0.0);  // TODO copy weight too
            }
        }

        return reversed;
    }

    protected abstract Graph copyEmpty();
    protected abstract void createEdge(int parent, int child, double weight);

    @Override
    public void validateVertex(int vertex) throws IllegalArgumentException {
        if (vertex < 0 || vertex >= verticesCount) {
            throw new IllegalArgumentException("The vertex index is incorrect");
        }
    }

    @Override
    public int getDegree(int vertex) {
        validateVertex(vertex);
        return degrees[vertex];
    }

    @Override
    public int getVerticesCount() {
        return verticesCount;
    }


    @Override
    public boolean isDirected() {
        return directed;
    }

    @Override
    public int getEdgesCount() {
        return edgesCount;
    }

    protected void setEdgesCount(int edgesCount) {
        this.edgesCount = edgesCount;
    }
}
