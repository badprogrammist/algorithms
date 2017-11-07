package ru.ildar.algorithm.graph;

import java.util.Iterator;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class AdjacencyList extends AbstractGraph {

    private EdgeNode[] edges;

    public AdjacencyList(int verticesCount, int edgesCount, boolean directed) {
        super(verticesCount, edgesCount, directed);
        initEdges();
    }

    private void initEdges() {
        edges = new EdgeNode[getEdgesCount()];
        for (int i = 0; i < getEdgesCount(); i++) {
            edges[i] = new EdgeNode(i, 0);
        }
    }

    @Override
    public void insertEdge(int v1, int v2, double weight) {
        validateVertex(v1);
        validateVertex(v2);

        createEdge(v1, v2, weight);

        if (!isDirected()) {
            createEdge(v2, v1, weight);
        }
    }

    private void createEdge(int parent, int child, double weight) {
        EdgeNode parentVertex = edges[parent];
        EdgeNode childVertex = new EdgeNode(child, weight);
        if (parentVertex.next != null) {
            childVertex.next = parentVertex.next;
        }
        parentVertex.next = childVertex;
        incrementDegree(parent);
    }

    @Override
    public Iterator<Integer> getAdjacentEdgesIterator(int vertex) {
        validateVertex(vertex);
        return edges[vertex].iterator();
    }

    @Override
    public int[] getAdjacentEdges(int vertex) {
        validateVertex(vertex);
        int[] adjacentEdges = new int[getDegree(vertex)];
        int idx = 0;
        EdgeNode edge = edges[vertex];
        while (edge.next != null) {
            adjacentEdges[idx] = edge.next.vertex;
            edge = edge.next;
            idx++;
        }
        return adjacentEdges;
    }

    private class EdgeNode implements Iterable<Integer> {

        double weight;
        int vertex;

        EdgeNode next;

        EdgeNode(int vertex, double weight) {
            this.weight = weight;
            this.vertex = vertex;
        }

        @Override
        public Iterator<Integer> iterator() {
            return new EdgeNodeIterator(this);
        }
    }

    private class EdgeNodeIterator implements Iterator<Integer> {

        private EdgeNode node;

        EdgeNodeIterator(EdgeNode node) {
            this.node = node;
        }

        @Override
        public boolean hasNext() {
            return node.next != null;
        }

        @Override
        public Integer next() {
            node = node.next;
            return node.vertex;
        }

    }
}
