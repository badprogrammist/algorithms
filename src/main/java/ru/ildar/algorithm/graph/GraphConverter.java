package ru.ildar.algorithm.graph;

import java.util.Iterator;

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

    public static IncidenceMatrix toIncidenceMatrix(Graph list) {
        IncidenceMatrix matrix = new IncidenceMatrix(
                list.getVerticesCount(),
                list.getEdgesCount(),
                list.isDirected());
        boolean[] discovered = new boolean[list.getVerticesCount()];

        for (int i = 0; i < list.getVerticesCount(); i++) {
            discovered[i] = true;
            Iterator<Integer> iter = list.getAdjacentVerticesIterator(i);

            while(iter.hasNext()) {
                int adjacent = iter.next();
                if ((!list.isDirected() && !discovered[adjacent]) || list.isDirected()) {
                    matrix.insertEdge(i, adjacent, 0); // TODO copy weight
                }
            }
        }

        return matrix;
    }

}
