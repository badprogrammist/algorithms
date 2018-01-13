package ru.ildar.algorithm.graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class GraphConverter {

    public static AdjacencyList toAdjacencyList(Graph matrix) {
        AdjacencyList list = new AdjacencyList(matrix.getVerticesCount(), matrix.isDirected());

        for (int i = 0; i < matrix.getVerticesCount(); i++) {
            int count = matrix.isDirected() ? matrix.getVerticesCount() : i;
            for (int j = 0; j < count; j++) {
                if (i != j && matrix.isAdjacent(i, j)) {
                    list.insertEdge(i, j, 0); // TODO copy weight
                }
            }
        }

        return list;
    }

}
