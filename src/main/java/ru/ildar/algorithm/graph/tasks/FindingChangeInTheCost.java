package ru.ildar.algorithm.graph.tasks;

import ru.ildar.algorithm.datastructure.stack.LinkedStack;
import ru.ildar.algorithm.datastructure.stack.Stack;
import ru.ildar.algorithm.graph.Graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *
 * 6-8. Devise and analyze an algorithm that takes a weighted graph G and finds the smallest change in the cost
 * to a non-MST edge that would cause a change in the minimum spanning tree of G.
 * Your algorithm must be correct and run in polynomial time.
 *
 */
public class FindingChangeInTheCost {

    private Graph mst;
    private int[] edge;

    public void find(Graph graph, Graph mst) {
        this.mst = mst;
        this.edge = new int[2];

        Stack<Integer> stack = new LinkedStack<>();
        boolean[] visited = new boolean[graph.getVerticesCount()];

        stack.push(0);
        while (stack.size() != 0) {
            int vertex = stack.pop();
            visited[vertex] = true;

            for (int adj : graph.getAdjacentVertices(vertex)) {
                if (!visited[adj] && !mst.isAdjacent(vertex, adj)) {
                    double weight = graph.getEdgeWeight(vertex, adj);

                    if (isWeightLowerThanInMST(vertex, adj, weight)) {
                        edge[0] = vertex;
                        edge[1] = adj;
                        stack.clear();
                        break;
                    } else {
                        stack.push(adj);
                    }
                }
            }
        }


    }

    private boolean isWeightLowerThanInMST(int v1, int v2, double weight) {
        int[] path = getPath(v1, v2);
        double maxWeight = Double.MIN_VALUE;
        int vertex = v2;

        while (vertex != v1) {
            double edgeWeight = mst.getEdgeWeight(vertex, path[vertex]);

            if (edgeWeight > maxWeight) {
                maxWeight = edgeWeight;
            }

            vertex = path[vertex];
        }

        return weight < maxWeight;
    }

    private int[] getPath(int v1, int v2) {
        int[] path = new int[mst.getVerticesCount()];
        boolean[] visited = new boolean[mst.getVerticesCount()];
        Stack<Integer> stack = new LinkedStack<>();

        stack.push(v1);
        while (stack.size() != 0) {
            int vertex = stack.pop();
            visited[vertex] = true;

            for (int adj : mst.getAdjacentVertices(vertex)) {
                if (!visited[adj]) {
                    path[adj] = vertex;

                    if (adj == v2) {
                        stack.clear();
                    } else {
                        stack.push(adj);
                    }
                }
            }
        }

        return path;
    }


    public int[] getEdge() {
        return edge;
    }

}
