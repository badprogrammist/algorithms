package ru.ildar.algorithm.graph.tasks;

import ru.ildar.algorithm.datastructure.stack.LinkedStack;
import ru.ildar.algorithm.datastructure.stack.Stack;
import ru.ildar.algorithm.graph.Graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *
 * In certain graph problems, vertices have can have weights instead of or in addition to the weights of edges.
 * Let Cv be the cost of vertex v, and C(x,y) the cost of the edge (x,y). This problem is concerned with finding
 * the cheapest path between vertices a and b in a graph G. The cost of a path is the sum of the costs of the edges
 * and vertices encountered on the path.
 *   - Suppose that each edge in the graph has a weight of zero (while non-edges have a cost of ∞).
 *     Assume that Cv=1 for all vertices 1≤v≤n (i.e., all vertices have the same cost).
 *     Give an efficient algorithm to find the cheapest path from a to b and its time complexity.
 *   - Now suppose that the vertex costs are not constant (but are all positive) and the edge costs remain as above.
 *     Give an efficient algorithm to find the cheapest path from a to b and its time complexity.
 *   - Now suppose that both the edge and vertex costs are not constant (but are all positive).
 *     Give an efficient algorithm to find the cheapest path from a to b and its time complexity.
 *
 */
public class ShortestPathWithVertexWeight {

    private ShortestPathWithVertexWeight() {

    }

    public abstract static class Algorithm {
        private Graph graph;
        private int[] parent;
        private double[] distance;
        private int from;
        private int to;

        public void find(Graph graph, int from, int to) {
            this.graph = graph;
            this.from = from;
            this.to = to;

            parent = new int[graph.getVerticesCount()];
            distance = new double[getGraph().getVerticesCount()];
            for (int v = 0; v < getGraph().getVerticesCount(); v++) {
                distance[v] = Double.MAX_VALUE;
            }
        }

        public int[] getPath() {
            int size = 1;

            int vertex = to;
            while (vertex != from) {
                size++;
                vertex = parent[vertex];
            }

            int[] path = new int[size];
            int idx = size - 1;
            vertex = to;

            while (vertex != from) {
                path[idx] = vertex;
                idx--;
                vertex = parent[vertex];
            }
            path[idx] = from;

            return path;
        }

        protected Graph getGraph() {
            return graph;
        }

        protected int getParent(int vertex) {
            return parent[vertex];
        }

        protected void setParent(int v, int p) {
            parent[v] = p;
        }

        protected double getDistance(int vertex) {
            return distance[vertex];
        }

        protected void setDistance(int v, double value) {
            distance[v] = value;
        }

        protected int getFrom() {
            return from;
        }

        protected int getTo() {
            return to;
        }
    }

    public static class ZeroEdgesConstantVerticesCost extends Algorithm {

        @Override
        public void find(Graph graph, int from, int to) {
            super.find(graph, from, to);
            boolean[] visited = new boolean[getGraph().getVerticesCount()];

            Stack<Integer> stack = new LinkedStack<>();
            stack.push(from);
            setDistance(from, 0);

            while (stack.size() != 0) {
                int v = stack.pop();
                visited[v] = true;

                for (int adj : getGraph().getAdjacentVertices(v)) {
                    if (getDistance(v) + 1 < getDistance(adj)) {
                        setParent(adj, v);
                        setDistance(adj, getDistance(v) + 1);
                    }

                    if (!visited[adj]) {
                        stack.push(adj);
                    }
                }
            }
        }
    }

    public static class VerticesOnlyWeighted extends Algorithm {
        @Override
        public void find(Graph graph, int from, int to) {
            super.find(graph, from, to);

            boolean[] visited = new boolean[getGraph().getVerticesCount()];

            Stack<Integer> stack = new LinkedStack<>();
            stack.push(from);
            setDistance(from, getGraph().getVertexWeight(from));

            while (stack.size() != 0) {
                int v = stack.pop();
                visited[v] = true;

                for (int adj : getGraph().getAdjacentVertices(v)) {
                    double dist = getDistance(v) + getGraph().getVertexWeight(adj);

                    if (dist < getDistance(adj)) {
                        setParent(adj, v);
                        setDistance(adj, dist);
                    }

                    if (!visited[adj]) {
                        stack.push(adj);
                    }
                }
            }
        }
    }

    public static class EdgesAndVerticesWeighted extends Algorithm {

        @Override
        public void find(Graph graph, int from, int to) {
            super.find(graph, from, to);

            boolean[] visited = new boolean[graph.getVerticesCount()];
            int vertex = from;
            setDistance(vertex, graph.getVertexWeight(from));

            do {
                visited[vertex] = true;

                for (int adj : graph.getAdjacentVertices(vertex)) {
                    double dist = getDistance(vertex)
                            + graph.getVertexWeight(adj)
                            + graph.getEdgeWeight(vertex, adj);

                    if (dist < getDistance(adj)) {
                        setDistance(adj, dist);
                        setParent(adj, vertex);
                    }
                }

                double min = Double.MAX_VALUE;
                for(int v = 0; v < graph.getVerticesCount(); v++) {
                    if (!visited[v] && getDistance(v) < min) {
                        vertex = v;
                        min = getDistance(v);
                    }
                }

            } while (vertex != to);
        }

    }

}
