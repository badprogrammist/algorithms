package ru.ildar.algorithm.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class IncidenceMatrix extends AbstractGraph {

    private double[] weights;
    private int[][] matrix;
    private int edgeCursor;

    public IncidenceMatrix(int verticesCount, int edgesCount, boolean directed) {
        super(verticesCount, directed);
        setEdgesCount(edgesCount);
        initMatrix();
        initWeights();
        edgeCursor = 0;
    }

    private void initMatrix() {
        matrix = new int[getVerticesCount()][getEdgesCount()];
        for (int i = 0; i < getVerticesCount(); i++) {
            matrix[i] = new int[getEdgesCount()];
        }
    }

    private void initWeights() {
        weights = new double[getEdgesCount()];
    }

    @Override
    public void insertEdge(int v1, int v2, double weight) {
        validateVertex(v1);
        validateVertex(v2);
        validateEdgeCursor();

        createEdge(v1, v2, weight);
        edgeCursor++;
    }

    @Override
    protected void createEdge(int parent, int child, double weight) {
        incrementDegree(parent);
        if (isDirected()) {
            matrix[parent][edgeCursor] = -1;
        } else {
            matrix[parent][edgeCursor] = 1;
            incrementDegree(child);
        }
        matrix[child][edgeCursor] = 1;
        weights[edgeCursor] = weight;
    }

    @Override
    public int[] getAdjacentVertices(int vertex) {
        validateVertex(vertex);

        int[] adjacentEdges = new int[getDegree(vertex)];
        int idx = 0;

        for (int j = 0; j < getEdgesCount(); j++) {
            int incidence = matrix[vertex][j];
            if ((isDirected() && incidence == -1) || (!isDirected() && incidence == 1)) {
                for (int i = 0; i < getVerticesCount(); i++) {
                    if (i != vertex && matrix[i][j] == 1) {
                        adjacentEdges[idx] = i;
                        idx++;
                    }
                }
            }
        }
        return adjacentEdges;
    }

    @Override
    public boolean isAdjacent(int v1, int v2) {
        return false;
    }

    @Override
    public Iterator<Integer> getAdjacentEdgesIterator(int vertex) {
        int[] adjacentEdges = getAdjacentVertices(vertex);
        List<Integer> list = new ArrayList<>();
        for (int adjacentEdge : adjacentEdges) {
            list.add(adjacentEdge);
        }
        return list.iterator();
    }

    @Override
    protected Graph copyEmpty() {
        return new IncidenceMatrix(getVerticesCount(), getEdgesCount(), isDirected());
    }

    private void validateEdgeCursor() throws IllegalArgumentException {
        if (edgeCursor < 0 || edgeCursor >= getEdgesCount()) {
            throw new IllegalArgumentException("The count of edges has increased");
        }
    }
}
