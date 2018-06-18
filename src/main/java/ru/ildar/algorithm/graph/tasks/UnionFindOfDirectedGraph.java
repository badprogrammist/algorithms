package ru.ildar.algorithm.graph.tasks;

import ru.ildar.algorithm.datastructure.queue.LinkedQueue;
import ru.ildar.algorithm.datastructure.queue.Queue;
import ru.ildar.algorithm.graph.Graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *
 * 6-12. Devise an efficient data structure to handle the following operations on a weighted directed graph:
 * - Merge two given components.
 * - Locate which component contains a given vertex v.
 * - Retrieve a minimum edge from a given component.
 *
 */
public class UnionFindOfDirectedGraph {
    private Graph graph;

    private int[] parent;
    private int[] compSize;
    private int[][] compMinEdge;

    public UnionFindOfDirectedGraph(Graph graph) {
        if (!graph.isDirected()) {
            throw new IllegalArgumentException("A Graph should be directed");
        }

        this.graph = graph;
        parent = new int[graph.getVerticesCount()];
        compSize = new int[graph.getVerticesCount()];
        compMinEdge = new int[graph.getVerticesCount()][2];

        for(int i = 0; i < graph.getVerticesCount(); i++) {
            parent[i] = i;
            compSize[i] = 1;
            compMinEdge[i][0] = -1;
            compMinEdge[i][1] = -1;
        }
    }

    public int find(int vertex) {
        if (parent[vertex] != vertex) {
            return find(parent[vertex]);
        }

        return vertex;
    }

    public boolean union(int v1, int v2) {
        int v1Comp = find(v1);
        int v2Comp = find(v2);

        if (v1Comp == v2Comp) {
            return false;
        }

        if (compSize[v1Comp] < compSize[v2Comp]) {
            parent[v1Comp] = v2Comp;
            compSize[v2Comp] += compSize[v1Comp];
            calcMinEdge(v2Comp);
        } else {
            parent[v2Comp] = v1Comp;
            compSize[v1Comp] += compSize[v2Comp];
            calcMinEdge(v1Comp);
        }

        return true;
    }

    private void calcMinEdge(int component) {
        Queue<Integer> queue = new LinkedQueue<>();
        boolean[] visited = new boolean[graph.getVerticesCount()];

        queue.add(component);

        while (queue.size() != 0) {
            int v = queue.poll();
            visited[v] = true;

            for (int adj: graph.getAdjacentVertices(v)) {
                if (find(adj) == component) {
                    double weight = graph.getEdgeWeight(v, adj);

                    if (weight < getMinEdgeWeight(component)) {
                        setMinEdge(component, v, adj);
                    }

                    if (!visited[adj]) {
                        queue.add(adj);
                    }
                }
            }
        }
    }

    private void setMinEdge(int component, int v1, int v2) {
        compMinEdge[component][0] = v1;
        compMinEdge[component][1] = v2;
    }

    public int[] getMinEdge(int component) {
        return compMinEdge[component];
    }

    public double getMinEdgeWeight(int component) {
        int[] minEdge = getMinEdge(component);

        if (minEdge[0] != -1 && minEdge[1] != -1) {
            return graph.getEdgeWeight(minEdge[0], minEdge[1]);
        }

        return Double.MAX_VALUE;
    }
}
