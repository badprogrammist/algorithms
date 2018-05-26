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
    public void removeEdge(int parent, int child) {
        edges.removeEdge(parent, child);
        decrementDegree(parent);

        if (!isDirected()) {
            edges.removeEdge(child, parent);
            decrementDegree(child);
        }

        setEdgesCount(getEdgesCount() - 1);
    }

    @Override
    public double getEdgeWeight(int v1, int v2) {
        return edges.getWeight(v1, v2);
    }

    @Override
    public Graph square() {
        AdjacencyList squared = new AdjacencyList(getVerticesCount(), isDirected());
        for (int i = 0; i < getVerticesCount(); i++) {
            if (getDegree(i) == 0) {
                continue;
            }

            Iterator<Integer> iAdjacentIter = getAdjacentVerticesIterator(i);
            while(iAdjacentIter.hasNext()) {
                int j = iAdjacentIter.next();

                if (getDegree(j) != 0) {
                    Iterator<Integer> jAdjacentIter = getAdjacentVerticesIterator(j);

                    while(jAdjacentIter.hasNext()) {
                        int k = jAdjacentIter.next();

                        if (isAdjacent(j, k) && !isAdjacent(i, k)) {
                            squared.insertEdge(i, k, 0.0); // TODO calculate weight
                        }
                    }
                }
            }
        }
        return squared;
    }

    @Override
    public Iterator<Integer> getAdjacentVerticesIterator(int vertex) {
        validateVertex(vertex);
        return edges.getAdjacentVerticesIterator(vertex);
    }

    @Override
    public int[] getAdjacentVertices(int vertex) {
        validateVertex(vertex);
        int[] adjacentEdges = new int[getDegree(vertex)];
        int idx = 0;
        Iterator<Integer> iter = edges.getAdjacentVerticesIterator(vertex);
        while (iter.hasNext()) {
            adjacentEdges[idx] = iter.next();
            idx++;
        }
        return adjacentEdges;
    }

    @Override
    public boolean isAdjacent(int v1, int v2) {
        Iterator<Integer> iter = edges.getAdjacentVerticesIterator(v1);
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

        void removeEdge(int parent, int child) {
            Iterator<EdgeNode> iter = getAdjacentNodesIterator(parent);

            while (iter.hasNext()) {
                EdgeNode vertex = iter.next();

                if (vertex.next != null && vertex.next.vertex == child) {
                    vertex.next = vertex.next.next;
                    break;
                }
            }
        }

        Iterator<Integer> getAdjacentVerticesIterator(int vertex) {
            return edges[vertex].vertexIterator();
        }

        Iterator<EdgeNode> getAdjacentNodesIterator(int vertex) {
            return edges[vertex].nodeIterator();
        }

        double getWeight(int v1, int v2) {
            Iterator<EdgeNode> iter = getAdjacentNodesIterator(v1);

            while (iter.hasNext()) {
                EdgeNode adjacentNode = iter.next();

                if (adjacentNode.vertex == v2) {
                    return adjacentNode.weight;
                }
            }

            return -1;
        }

    }

    private class EdgeNode {

        double weight;
        int vertex;

        EdgeNode next;

        EdgeNode(int vertex, double weight) {
            this.weight = weight;
            this.vertex = vertex;
        }

        public Iterator<Integer> vertexIterator() {
            return new VertexIterator(this);
        }

        public Iterator<EdgeNode> nodeIterator() {
            return new NodeIterator(this);
        }
    }

    private class NodeIterator implements Iterator<EdgeNode> {

        private EdgeNode node;

        NodeIterator(EdgeNode node) {
            this.node = node;
        }

        @Override
        public boolean hasNext() {
            return node.next != null;
        }

        @Override
        public EdgeNode next() {
            node = node.next;
            return node;
        }

    }

    private class VertexIterator implements Iterator<Integer> {

        private EdgeNode node;

        VertexIterator(EdgeNode node) {
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
