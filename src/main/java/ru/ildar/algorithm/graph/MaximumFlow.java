package ru.ildar.algorithm.graph;

import ru.ildar.algorithm.datastructure.Iterator;
import ru.ildar.algorithm.datastructure.list.LinkedList;
import ru.ildar.algorithm.datastructure.queue.LinkedQueue;
import ru.ildar.algorithm.datastructure.queue.Queue;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class MaximumFlow {

    public static interface Algorithm {
        void find(Graph graph, int source, int sink);

        int getMaxFlow();
    }

    public static class FordFulkersonAlgorithm implements Algorithm {

        private Graph graph;
        private int maxFlow = 0;
        private int source;
        private int sink;

        private Edge augmentingPath[];
        private boolean inCut[]; // is a vertex reachable from source in residual network

        private FlowNetwork flowNetwork;

        @Override
        public void find(Graph graph, int source, int sink) {
            this.graph = graph;
            this.source = source;
            this.sink = sink;

            initFlowNetwork();

            while (calcAugmentingPath()) {
                calcBottleneck();
            }

        }

        private boolean calcAugmentingPath() {
            augmentingPath = new Edge[graph.getVerticesCount()];
            inCut = new boolean[graph.getVerticesCount()];

            Queue<Integer> queue = new LinkedQueue<>();
            queue.add(source);
            inCut[source] = true;

            while (queue.size() != 0) {
                int vertex = queue.poll();
                Iterator<Edge> iter = flowNetwork.getAdjacencyEdges(vertex);

                while (iter.hasNext()) {
                    Edge edge = iter.next();
                    int otherVertex = edge.other(vertex);

                    if (edge.getResidualFlow(otherVertex) > 0 && !inCut[otherVertex]) {
                        augmentingPath[otherVertex] = edge;
                        inCut[otherVertex] = true;
                        queue.add(otherVertex);
                    }
                }
            }

            return inCut[sink];
        }

        private void calcBottleneck() {
            double bottleneck = Double.MAX_VALUE;

            for (int vertex = sink; vertex != source; vertex = augmentingPath[vertex].other(vertex)) {
                bottleneck = Math.min(bottleneck, augmentingPath[vertex].getResidualFlow(vertex));
            }

            for (int vertex = sink; vertex != source; vertex = augmentingPath[vertex].other(vertex)) {
                augmentingPath[vertex].addResidualFlowTo(vertex, bottleneck);
            }

            maxFlow += bottleneck;
        }

        private void initFlowNetwork() {
            flowNetwork = new FlowNetwork(graph.getVerticesCount());
            Queue<Integer> queue = new LinkedQueue<>();
            boolean visited[] = new boolean[graph.getVerticesCount()];

            queue.add(source);
            visited[source] = true;

            while (queue.size() != 0) {
                int vertex = queue.poll();

                for (int adjacent : graph.getAdjacentVertices(vertex)) {
                    double weight = graph.getEdgeWeight(vertex, adjacent);
                    Edge edge = new Edge(vertex, adjacent, weight);

                    flowNetwork.addEdge(edge);

                    if (!visited[adjacent]) {
                        queue.add(adjacent);
                        visited[adjacent] = true;
                    }
                }
            }
        }

        @Override
        public int getMaxFlow() {
            return maxFlow;
        }

        private class FlowNetwork {
            private LinkedList<Edge>[] edges;

            FlowNetwork(int verticesCount) {
                edges = (LinkedList<Edge>[]) new LinkedList[verticesCount];

                for (int v = 0; v < graph.getVerticesCount(); v++) {
                    edges[v] = new LinkedList<>();
                }
            }

            void addEdge(Edge edge) {
                edges[edge.from].add(edge);
                edges[edge.to].add(edge);
            }

            Iterator<Edge> getAdjacencyEdges(int vertex) {
                return edges[vertex].iterator();
            }

        }

        private class Edge {
            final int from;
            final int to;
            private double flow = 0;
            final double capacity;

            Edge(int from, int to, double capacity) {
                this.from = from;
                this.to = to;
                this.capacity = capacity;
            }

            void addResidualFlowTo(int vertex, double delta) {
                if (vertex == to) {
                    flow += delta;
                } else if (vertex == from) {
                    flow -= delta;
                } else {
                    throw new IllegalArgumentException("It attempts to add flow from the invalid edge");
                }
            }

            double getResidualFlow(int vertex) {
                if (vertex == to) {
                    return capacity - flow;
                } else if (vertex == from) {
                    return flow;
                } else {
                    throw new IllegalArgumentException("It attempts to get flow from the invalid edge");
                }
            }

            int other(int vertex) {
                if (vertex == to) {
                    return from;
                } else if (vertex == from) {
                    return to;
                } else {
                    throw new IllegalArgumentException("It attempts to get the other vertex from the invalid edge");
                }
            }
        }

    }

}
