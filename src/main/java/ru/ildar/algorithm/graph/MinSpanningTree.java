package ru.ildar.algorithm.graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class MinSpanningTree {

    public static interface Algorithm {
        void find(Graph graph);

        Graph getTree();

    }

    public static class PrimsAlgorithm implements Algorithm {

        private Graph graph;
        private boolean[] inTree;
        private int parentOf[];

        private double[] distance;


        @Override
        public void find(Graph graph) {
            this.graph = graph;
            inTree = new boolean[graph.getVerticesCount()];
            parentOf = new int[graph.getVerticesCount()];
            distance = new double[graph.getVerticesCount()];

            for (int v = 0; v < graph.getVerticesCount(); v++) {
                parentOf[v] = -1;
                distance[v] = Double.MAX_VALUE;
            }

            traverse(0);
        }

        private void traverse(int vertex) {
            distance[vertex] = 0;

            while (!inTree[vertex]) {
                inTree[vertex] = true;

                for (int adjacent : graph.getAdjacentVertices(vertex)) {
                    double weight = graph.getEdgeWeight(vertex, adjacent);

                    if (!inTree[adjacent] && weight < distance[adjacent]) {
                        distance[adjacent] = weight;
                        parentOf[adjacent] = vertex;
                    }
                }


                vertex = findNextMinVertex();
            }

        }

        private int findNextMinVertex() {
            double dist = Double.MAX_VALUE;
            int nextVertex = 0;

            for (int v = 0; v < graph.getVerticesCount(); v++) {
                if (!inTree[v] && distance[v] < dist) {
                    dist = distance[v];
                    nextVertex = v;
                }
            }

            return nextVertex;
        }


        @Override
        public Graph getTree() {
            Graph tree = new AdjacencyList(graph.getVerticesCount(), false);

            for (int i = 0; i < graph.getVerticesCount(); i++) {
                if (parentOf[i] != -1 && !tree.isAdjacent(parentOf[i], i)) {
                    tree.insertEdge(parentOf[i], i, distance[i]);
                }
            }

            return tree;
        }

    }


}
