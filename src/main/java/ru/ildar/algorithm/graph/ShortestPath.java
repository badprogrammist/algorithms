package ru.ildar.algorithm.graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class ShortestPath {

    public static interface Algorithm {
        void find(Graph graph, int from, int to);

        int[] getPath();
    }

    public class DijkstrasAlgoritm implements Algorithm {

        private Graph graph;

        private int[] parentOf;
        private double[] distance;
        private boolean[] visited;

        private int from;
        private int to;

        @Override
        public void find(Graph graph, int from, int to) {
            this.graph = graph;
            this.from = from;
            this.to = to;

            parentOf = new int[graph.getVerticesCount()];
            visited = new boolean[graph.getVerticesCount()];

            distance = new double[graph.getVerticesCount()];
            for (int i = 0; i < graph.getVerticesCount(); i++) {
                distance[i] = Double.MAX_VALUE;
            }

            traverse();
        }

        private void traverse() {
            distance[from] = 0;
            int vertex = from;

            do {
                visited[vertex] = true;
                int next = -1;
                double nextWeight = Double.MAX_VALUE;

                for (int adjacent : graph.getAdjacentVertices(vertex)) {
                    if (!visited[adjacent]) {
                        double weight = graph.getEdgeWeight(vertex, adjacent);

                        if (distance[vertex] + weight < distance[adjacent]) {
                            distance[adjacent] = distance[vertex] + weight;
                            parentOf[adjacent] = vertex;
                        }

                        if (distance[adjacent] < nextWeight) {
                            next = adjacent;
                            nextWeight = distance[adjacent];
                        }
                    }
                }

                if (next != -1) {
                    vertex = next;
                }
            } while (vertex != to);

        }

        @Override
        public int[] getPath() {
            int size = 1;

            int vertex = to;
            while (vertex != from) {
                size++;
                vertex = parentOf[vertex];
            }

            int[] path = new int[size];
            int idx = size - 1;
            vertex = to;

            while (vertex != from) {
                path[idx] = vertex;
                idx--;
                vertex = parentOf[vertex];
            }
            path[idx] = from;

            return path;
        }
    }

}
