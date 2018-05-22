package ru.ildar.algorithm.graph.tasks;

import ru.ildar.algorithm.graph.AdjacencyList;
import ru.ildar.algorithm.graph.Graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *
 *         6-6. Suppose we are given the minimum spanning tree T of a given graph G (with n vertices and m edges)
 *         and a new edge e=(u,v) of weight w that we will add to G.
 *         Give an efficient algorithm to find the minimum spanning tree of the graph G+e.
 *         Your algorithm should run in O(n) time to receive full credit.
 */
public class FindMinSpanningTreeAfterEdge {

    private Graph graph;
    private double distance[];
    private int parent[];

    public void init(Graph graph) {
        this.graph = graph;
        parent = new int[graph.getVerticesCount()];
        distance = new double[graph.getVerticesCount()];

        for (int v = 0; v < graph.getVerticesCount(); v++) {
            distance[v] = Double.MAX_VALUE;
            parent[v] = -1;
        }

        findMinSpanningTree();
    }

    private void findMinSpanningTree() {
        int vertex = 0;
        boolean inTree[] = new boolean[graph.getVerticesCount()];

        while (!inTree[vertex]) {
            inTree[vertex] = true;

            for (int adj : graph.getAdjacentVertices(vertex)) {
                double weight = graph.getEdgeWeight(vertex, adj);

                if (!inTree[adj] && weight < distance[adj]) {
                    distance[adj] = weight;
                    parent[adj] = vertex;
                }
            }

            double dist = Double.MAX_VALUE;

            for (int v = 0; v < graph.getVerticesCount(); v++) {
                if (!inTree[v] && distance[v] < dist) {
                    vertex = v;
                    dist = distance[v];
                }
            }
        }
    }

    public void addEdge(int v1, int v2, double weight) {
        if (distance[v1] < distance[v2]) {
            parent[v2] = v1;
            distance[v2] = weight;
        } else {
            parent[v1] = v2;
            distance[v1] = weight;
        }
    }

    public Graph getTree() {
        Graph tree = new AdjacencyList(graph.getVerticesCount(), false);

        for (int i = 0; i < graph.getVerticesCount(); i++) {
            if (parent[i] != -1 && !tree.isAdjacent(parent[i], i)) {
                tree.insertEdge(parent[i], i, distance[i]);
            }
        }

        return tree;
    }

}
