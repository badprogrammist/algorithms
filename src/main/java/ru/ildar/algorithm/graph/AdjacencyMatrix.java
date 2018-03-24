package ru.ildar.algorithm.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class AdjacencyMatrix extends AbstractGraph {

    private Edge[][] matrix;

    public AdjacencyMatrix(int verticesCount, boolean directed) {
        super(verticesCount, directed);
        initMatrix();
    }

    private void initMatrix() {
        int verticesCount = getVerticesCount();
        matrix = new Edge[verticesCount][verticesCount];
        for (int i = 0; i < verticesCount; i++) {
            matrix[i] = new Edge[verticesCount];
            for (int j = 0; j < verticesCount; j++) {
                matrix[i][j] = new Edge();
            }
        }
    }

    @Override
    protected void createEdge(int parent, int child, double weight) {
        Edge edge = matrix[parent][child];
        edge.adjacent = true;
        edge.weight = weight;
        incrementDegree(parent);
    }

    @Override
    public double getEdgeWeight(int v1, int v2) {
        Edge edge = matrix[v1][v2];

        if (edge != null) {
            return edge.weight;
        } else {
            return -1;
        }
    }

    @Override
    public Graph square() {
        AdjacencyMatrix squared = new AdjacencyMatrix(getVerticesCount(), isDirected());
        for (int i = 0; i < getVerticesCount(); i++) {
            if (getDegree(i) == 0) {
                continue;
            }
            for (int j = 0; j < getVerticesCount(); j++) {
                if (isAdjacent(i, j)) {
                    if (getDegree(j) == 0) {
                        continue;
                    }

                    for (int k = 0; k < getVerticesCount(); k++) {
                        if (isAdjacent(j, k) && !isAdjacent(i, k)) {
                            squared.insertEdge(i, k, 0.0); // TODO calculate weight
                        }
                    }
                }
            }
        }

        return squared;
    }

    @Override
    public int[] getAdjacentVertices(int vertex) {
        validateVertex(vertex);

        int[] adjacentEdges = new int[getDegree(vertex)];
        int idx = 0;

        for (int j = 0; j < getVerticesCount(); j++) {
            Edge edge = matrix[vertex][j];
            if (edge.adjacent) {
                adjacentEdges[idx] = j;
                idx++;
            }
        }
        return adjacentEdges;
    }

    @Override
    public boolean isAdjacent(int v1, int v2) {
        return matrix[v1][v2].adjacent;
    }

    @Override
    public Iterator<Integer> getAdjacentVerticesIterator(int vertex) {
        int[] adjacentEdges = getAdjacentVertices(vertex);
        List<Integer> list = new ArrayList<>();
        for (int adjacentEdge : adjacentEdges) {
            list.add(adjacentEdge);
        }
        return list.iterator();
    }

    @Override
    protected Graph copyEmpty() {
        return new AdjacencyMatrix(getVerticesCount(), isDirected());
    }

    private class Edge {
        boolean adjacent = false;
        double weight = 0;
    }
}
