package ru.ildar.algorithm.graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public abstract class AbstractGraph implements Graph {

    private int verticesCount;
    private int edgesCount;
    private boolean directed;

    private int[] degrees;

    public AbstractGraph(int verticesCount, int edgesCount, boolean directed) {
        this.verticesCount = verticesCount;
        this.edgesCount = edgesCount;
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
    public int getEdgesCount() {
        return edgesCount;
    }

    @Override
    public boolean isDirected() {
        return directed;
    }
}
