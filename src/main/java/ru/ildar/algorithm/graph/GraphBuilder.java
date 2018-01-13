package ru.ildar.algorithm.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class GraphBuilder {

    public static GraphBuilder adjacencyList(boolean directed) {
        return new GraphBuilder(AdjacencyType.LIST, directed);
    }

    public static GraphBuilder adjacencyMatrix(boolean directed) {
        return new GraphBuilder(AdjacencyType.MATRIX, directed);
    }

    public static GraphBuilder incidenceMatrix(boolean directed) {
        return new GraphBuilder(AdjacencyType.INCIDENCE, directed);
    }

    private AdjacencyType adjacencyType;
    private boolean directed;
    private List<Edge> edges = new LinkedList<>();
    private Set<Integer> vertices = new HashSet<>();

    public GraphBuilder(AdjacencyType adjacencyType, boolean directed) {
        this.adjacencyType = adjacencyType;
        this.directed = directed;
    }

    public GraphBuilder edge(int v1, int v2) {
        return edge(v1, v2, 0);
    }

    public GraphBuilder edge(int v1, int v2, double weight) {
        edges.add(new Edge(v1, v2, weight));
        vertices.add(v1);
        vertices.add(v2);
        return this;
    }

    public Graph create() {
        Graph graph = null;

        if (adjacencyType == null) {
            throw new IllegalStateException("The adjacency type of graph have not been set");
        }

        if (adjacencyType == AdjacencyType.LIST) {
            graph = new AdjacencyList(vertices.size(), directed);
        }
        if (adjacencyType == AdjacencyType.MATRIX) {
            graph = new AdjacencyMatrix(vertices.size(), directed);
        }
        if (adjacencyType == AdjacencyType.INCIDENCE) {
            graph = new IncidenceMatrix(vertices.size(), edges.size(), directed);
        }

        if (graph != null) {
            for (Edge edge : edges) {
                graph.insertEdge(edge.v1, edge.v2, edge.weight);
            }
        }
        return graph;
    }

    enum AdjacencyType {
        LIST, MATRIX, INCIDENCE
    }

    class Edge {
        int v1;
        int v2;
        double weight;

        Edge(int v1, int v2, double weight) {
            this.v1 = v1;
            this.v2 = v2;
            this.weight = weight;
        }
    }

}
