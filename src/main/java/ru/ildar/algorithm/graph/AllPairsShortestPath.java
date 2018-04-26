package ru.ildar.algorithm.graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class AllPairsShortestPath {

    public static interface Algorithm {
        double[][] find(Graph graph);
    }

    public static class FloydWarshallAlgorithm implements Algorithm {

        @Override
        public double[][] find(Graph graph) {
            double[][] weight = init(graph);

            for (int k = 0; k < graph.getVerticesCount(); k++) {
                for (int v1 = 0; v1 < graph.getVerticesCount(); v1++) {
                    for (int v2 = 0; v2 < graph.getVerticesCount(); v2++) {
                        double throughK = weight[v1][k] + weight[k][v2];

                        if (throughK < weight[v1][v2]) {
                            weight[v1][v2] = throughK;
                        }
                    }
                }
            }

            return weight;
        }

        private double[][] init(Graph graph) {
            double[][] matrix = new double[graph.getVerticesCount()][graph.getVerticesCount()];

            for (int v1 = 0; v1 < graph.getVerticesCount(); v1++) {
                for (int v2 = 0; v2 < graph.getVerticesCount(); v2++) {
                    if (graph.isAdjacent(v1, v2)) {
                        matrix[v1][v2] = graph.getEdgeWeight(v1, v2);
                    } else {
                        matrix[v1][v2] = Double.MAX_VALUE;
                    }
                }
            }

            return matrix;
        }

    }

}
