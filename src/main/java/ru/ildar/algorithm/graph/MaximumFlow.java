package ru.ildar.algorithm.graph;

import ru.ildar.algorithm.datastructure.stack.LinkedStack;
import ru.ildar.algorithm.datastructure.stack.Stack;

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
        private double flows[][][];
        private int source;
        private int sink;

        @Override
        public void find(Graph graph, int source, int sink) {
            this.graph = graph;
            this.source = source;
            this.sink = sink;
            this.flows = new double[graph.getVerticesCount()][graph.getVerticesCount()][2];
            initFlows(source);

            findAugmentingPath();
        }

        private void findAugmentingPath() {
            double bottleneck = Double.MAX_VALUE;

            int[] parent = new int[graph.getVerticesCount()];
            parent[sink] = -1;

            Stack<Integer> stack = new LinkedStack<>();
            stack.push(source);

            while (stack.size() != 0) {
                int vertex = stack.pop();
                boolean found = false;

                if (vertex == sink) {
                    stack.clear();
                    break;
                }

                for (int adjacent = 0; adjacent < graph.getVerticesCount(); adjacent++) {
                    double flow = getFlow(vertex, adjacent);
                    double capacity = getCapacity(vertex, adjacent);

                    if (capacity > 0) {
                        double delta = capacity - flow;

                        if (delta != 0 && delta < bottleneck) {
                            bottleneck = delta;
                        }
                        if (delta > 0) {
                            parent[adjacent] = vertex;
                            stack.push(adjacent);

                            found = true;

                            break;
                        }

                    }

                }

                if (!found) {
                    for (int adjacent = 0; adjacent < graph.getVerticesCount(); adjacent++) {
                        double flow = getFlow(vertex, adjacent);
                        double capacity = getCapacity(vertex, adjacent);

                        if (capacity < 0) {
                            if (flow > 0 && bottleneck > 0 && adjacent != source) {

                                if (flow < bottleneck) {
                                    bottleneck = flow;
                                }

                                parent[adjacent] = vertex;
                                stack.push(adjacent);

                                break;
                            }
                        }
                    }

                }

            }

            if (parent[sink] != -1) {
                int vertex = sink;

                while (vertex != source) {
                    addFlow(parent[vertex], vertex, bottleneck);
                    vertex = parent[vertex];
                }

                maxFlow += bottleneck;
                System.out.println("maxFlow = " + maxFlow);

                findAugmentingPath();
            }
        }

        private void initFlows(int vertex) {
            for (int adjacent : graph.getAdjacentVertices(vertex)) {
                double weight = graph.getEdgeWeight(vertex, adjacent);

                setCapacity(vertex, adjacent, weight);
                initFlows(adjacent);
            }
        }

        private void addFlow(int v1, int v2, double flow) {
            double capacity = getCapacity(v1, v2);

            if (capacity > 0) {
                flows[v1][v2][0] += flow;
                flows[v2][v1][0] += flow;
                System.out.println("flow " + v1 + "->" + v2 + " = " + (flows[v1][v2][0] - flow) + "+" + flow + " = " + flows[v1][v2][0]);

            } else {
                flows[v1][v2][0] -= flow;
                flows[v2][v1][0] -= flow;
                System.out.println("flow " + v1 + "->" + v2 + " = " + (flows[v1][v2][0] + flow) + "-" + flow + " = " + flows[v1][v2][0]);
            }
        }

        private void setCapacity(int v1, int v2, double capacity) {
            flows[v1][v2][1] = capacity;
            flows[v2][v1][1] = -capacity;
        }


        private double getFlow(int v1, int v2) {
            return flows[v1][v2][0];
        }

        private double getCapacity(int v1, int v2) {
            return flows[v1][v2][1];
        }

        @Override
        public int getMaxFlow() {
            return maxFlow;
        }
    }

}
