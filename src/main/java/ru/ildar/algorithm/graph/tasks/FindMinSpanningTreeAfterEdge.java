package ru.ildar.algorithm.graph.tasks;

import ru.ildar.algorithm.datastructure.stack.LinkedStack;
import ru.ildar.algorithm.datastructure.stack.Stack;
import ru.ildar.algorithm.graph.AdjacencyList;
import ru.ildar.algorithm.graph.Graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *         <p>
 *         6-6. Suppose we are given the minimum spanning tree T of a given graph G (with n vertices and m edges)
 *         and a new edge e=(u,v) of weight w that we will add to G.
 *         Give an efficient algorithm to find the minimum spanning tree of the graph G+e.
 *         Your algorithm should run in O(n) time to receive full credit.
 */
public class FindMinSpanningTreeAfterEdge {

    private Graph graph;
    private Graph tree;

    public void init(Graph graph) {
        this.graph = graph;
        findMinSpanningTree();
    }

    private void findMinSpanningTree() {
        int vertex = 0;
        boolean inTree[] = new boolean[graph.getVerticesCount()];
        double distance[] = new double[graph.getVerticesCount()];
        int parent[] = new int[graph.getVerticesCount()];

        for (int v = 0; v < graph.getVerticesCount(); v++) {
            distance[v] = Double.MAX_VALUE;
            parent[v] = -1;
        }

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

        this.tree = genTree(parent, distance);
    }

    public void addEdge(int v1, int v2, double weight) {
        int[] path = getPath(tree, v1, v2);
        replaceEdge(path, v1, v2, weight);
    }

    private int[] getPath(Graph tree, int v1, int v2) {
        int[] path = new int[tree.getVerticesCount()];
        boolean[] visited = new boolean[tree.getVerticesCount()];
        Stack<Integer> stack = new LinkedStack<>();

        stack.push(v1);
        while (stack.size() != 0) {
            int vertex = stack.pop();
            visited[vertex] = true;

            for (int adj : tree.getAdjacentVertices(vertex)) {
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

    private void replaceEdge(int[] path, int v1, int v2, double weight) {
        double maxWeight = Double.MIN_VALUE;
        int[] maxEdge = new int[2];
        int vertex = v2;

        while (vertex != v1) {
            double edgeWeight = tree.getEdgeWeight(vertex, path[vertex]);

            if (edgeWeight > maxWeight) {
                maxWeight = edgeWeight;
                maxEdge[0] = vertex;
                maxEdge[1] = path[vertex];
            }

            vertex = path[vertex];
        }

        if (maxWeight > weight) {
            tree.removeEdge(maxEdge[0], maxEdge[1]);
            tree.insertEdge(v1, v2, weight);
        }
    }


    private Graph genTree(int[] parent, double[] distance) {
        Graph tree = new AdjacencyList(graph.getVerticesCount(), false);

        for (int i = 0; i < graph.getVerticesCount(); i++) {
            if (parent[i] != -1 && !tree.isAdjacent(parent[i], i)) {
                tree.insertEdge(parent[i], i, distance[i]);
            }
        }

        return tree;
    }

    public Graph getTree() {
        return tree;
    }

}
