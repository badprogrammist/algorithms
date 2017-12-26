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
    public int[] getAdjacentEdges(int vertex) {
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
    public Iterator<Integer> getAdjacentEdgesIterator(int vertex) {
        int[] adjacentEdges = getAdjacentEdges(vertex);
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
