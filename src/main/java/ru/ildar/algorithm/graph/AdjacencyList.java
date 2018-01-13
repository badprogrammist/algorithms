package ru.ildar.algorithm.graph;

import java.util.Iterator;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class AdjacencyList extends AbstractGraph {

    private Edges edges;

    public AdjacencyList(int verticesCount, boolean directed) {
        super(verticesCount, directed);
        initEdges();
    }

    private void initEdges() {
        edges = new Edges();
        edges.initEdges(getVerticesCount());
    }

    @Override
    protected void createEdge(int parent, int child, double weight) {
        validateVertex(parent);
        validateVertex(child);
        edges.createEdge(parent, child, weight);
        incrementDegree(parent);
    }

    @Override
    public Iterator<Integer> getAdjacentVerticesIterator(int vertex) {
        validateVertex(vertex);
        return edges.getAdjacentEdgesIterator(vertex);
    }

    @Override
    public int[] getAdjacentVertices(int vertex) {
        validateVertex(vertex);
        int[] adjacentEdges = new int[getDegree(vertex)];
        int idx = 0;
        Iterator<Integer> iter = edges.getAdjacentEdgesIterator(vertex);
        while (iter.hasNext()) {
            adjacentEdges[idx] = iter.next();
            idx++;
        }
        return adjacentEdges;
    }

    @Override
    public boolean isAdjacent(int v1, int v2) {
        Iterator<Integer> iter = edges.getAdjacentEdgesIterator(v1);
        while (iter.hasNext()) {
            int adjacentVertex = iter.next();
            if (adjacentVertex == v2) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected Graph copyEmpty() {
        return new AdjacencyList(getVerticesCount(), isDirected());
    }

    private class Edges {
        private EdgeNode[] edges;

        void initEdges(int verticesCount) {
            edges = new EdgeNode[verticesCount];
            for (int i = 0; i < getVerticesCount(); i++) {
                edges[i] = new EdgeNode(i, 0);
            }
        }

        void createEdge(int parent, int child, double weight) {
            EdgeNode parentVertex = edges[parent];
            EdgeNode childVertex = new EdgeNode(child, weight);
            if (parentVertex.next != null) {
                childVertex.next = parentVertex.next;
            }
            parentVertex.next = childVertex;
        }

        Iterator<Integer> getAdjacentEdgesIterator(int vertex) {
            return edges[vertex].iterator();
        }

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
