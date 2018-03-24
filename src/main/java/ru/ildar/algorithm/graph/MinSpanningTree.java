package ru.ildar.algorithm.graph;

import ru.ildar.algorithm.datastructure.queue.PriorityQueue;
import ru.ildar.algorithm.datastructure.queue.TreePriorityQueue;

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

    public static class KruskalsAlgorithm implements Algorithm {

        private Graph graph;
        private Graph tree;
        private int[] components;

        private PriorityQueue<Edge> queue;



        @Override
        public void find(Graph graph) {
            init(graph);
            buildQueue();

            while (queue.size() != 0) {
                Edge edge = queue.pollMin();

                if(!isSameComponent(edge.v1, edge. v2)) {
                    merge(edge.v1, edge.v2);
                    tree(edge);
                }
            }

        }

        private void tree(Edge edge) {
            tree.insertEdge(edge.v1, edge.v2, edge.weight);
        }

        private void merge(int v1, int v2) {
            int changeComponent = components[v2];
            int toComponent = components[v1];

            for (int v = 0; v < components.length; v++) {
                if (components[v] == changeComponent) {
                    components[v] = toComponent;
                }
            }
        }

        private boolean isSameComponent(int v1, int v2) {
            return components[v1] == components[v2];
        }

        private void buildQueue() {
            queue = new TreePriorityQueue<>();
            BreadthFirstTraversal traversal = new BreadthFirstTraversal(graph);

            traversal.setEdgeProcessor((tr, v1, v2) -> {
                double weight = graph.getEdgeWeight(v1, v2);
                Edge edge = new Edge(v1, v2, weight);

                queue.add(edge);
            });

            traversal.traverse(0);
        }

        private void init(Graph graph) {
            this.graph = graph;
            this.tree = new AdjacencyList(graph.getVerticesCount(), false);
            components = new int[graph.getVerticesCount()];

            for(int v = 0; v < graph.getVerticesCount(); v++) {
                components[v] = v;
            }
        }

        @Override
        public Graph getTree() {
            return tree;
        }

        private class Edge implements Comparable<Edge> {
            int v1;
            int v2;
            double weight;

            public Edge(int v1, int v2, double weight) {
                this.v1 = v1;
                this.v2 = v2;
                this.weight = weight;
            }

            @Override
            public int compareTo(Edge o) {
                return Double.compare(this.weight, o.weight);
            }
        }

    }


}
