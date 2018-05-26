package ru.ildar.algorithm.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class IncidenceMatrix extends AbstractGraph {

    private Edge[] edges;
    private int edgeCursor;

    public IncidenceMatrix(int verticesCount, int edgesCount, boolean directed) {
        super(verticesCount, directed);
        setEdgesCount(edgesCount);
        initEdges();
        edgeCursor = 0;
    }

    private void initEdges() {
        edges = new Edge[getEdgesCount()];
    }


    @Override
    public void insertEdge(int v1, int v2, double weight) {
        validateVertex(v1);
        validateVertex(v2);
        validateEdgeCursor();

        createEdge(v1, v2, weight);
        edgeCursor++;
    }

    @Override
    protected void createEdge(int parent, int child, double weight) {
        incrementDegree(parent);
        edges[edgeCursor] = new Edge(parent, child, weight);

        if (!isDirected()) {
            incrementDegree(child);
        }
    }

    @Override
    public void removeEdge(int v1, int v2) {
        int index = findEdge(v1, v2);

        if (index != -1) {
            Edge[] edges = new Edge[getEdgesCount() - 1];
            int cursor = 0;

            while (cursor < index) {
                edges[cursor] = this.edges[cursor];
                cursor++;
            }
            while (cursor < getEdgesCount() - 1) {
                edges[cursor] = this.edges[cursor + 1];
                cursor++;
            }

            this.edges = edges;
            decrementDegree(v1);

            if (!isDirected()) {
                decrementDegree(v2);
            }

            setEdgesCount(getEdgesCount() - 1);
        }
    }

    @Override
    public double getEdgeWeight(int v1, int v2) {
        int index = findEdge(v1, v2);

        if (index != -1) {
            return edges[index].getWeight();
        }

        return -1;
    }

    private int findEdge(int v1, int v2) {
        for (int j = 0; j < getEdgesCount(); j++) {
            Edge edge = edges[j];

            if ((!isDirected()
                    && ((edge.getParent() == v1 && edge.getChild() == v2)
                    || (edge.getParent() == v2 && edge.getChild() == v1))
            ) || (edge.getParent() == v1 && edge.getChild() == v2)) {
                return j;
            }
        }

        return -1;
    }

    @Override
    public Graph square() {
        throw new UnsupportedOperationException("There is no implementation for that operation");
    }

    @Override
    public int[] getAdjacentVertices(int vertex) {
        validateVertex(vertex);

        int[] adjacentEdges = new int[getDegree(vertex)];
        int idx = 0;

        for (int j = 0; j < getEdgesCount(); j++) {
            Edge edge = edges[j];

            if (edge.getParent() == vertex) {
                adjacentEdges[idx] = edge.getChild();
                idx++;
            } else if (!isDirected() && edge.getChild() == vertex) {
                adjacentEdges[idx] = edge.getParent();
                idx++;
            }
        }
        return adjacentEdges;
    }


    @Override
    public boolean isAdjacent(int v1, int v2) {
        for (int j = 0; j < getEdgesCount(); j++) {
            Edge edge = edges[j];

            if (edge.getParent() == v1 && edge.getChild() == v2) {
                return true;
            }
            if (edge.getParent() == v2 && edge.getChild() == v1) {
                return true;
            }
        }
        return false;
    }

    public Edge getEdge(int edgeIndex) {
        return edges[edgeIndex];
    }

    @Override
    public Iterator<Integer> getAdjacentVerticesIterator(int vertex) {
        int[] adjacentEdges = getAdjacentVertices(vertex);
        List<Integer> list = new ArrayList<>();
        for (int adjacentEdge : adjacentEdges) {
            list.add(adjacentEdge);
        }
        return list.iterator();
    }

    @Override
    protected Graph copyEmpty() {
        return new IncidenceMatrix(getVerticesCount(), getEdgesCount(), isDirected());
    }

    private void validateEdgeCursor() throws IllegalArgumentException {
        if (edgeCursor < 0 || edgeCursor >= getEdgesCount()) {
            throw new IllegalArgumentException("The count of edges has increased");
        }
    }

    public class Edge {
        private int parent;
        private int child;
        private double weight;

        public Edge(int parent, int child, double weight) {
            this.parent = parent;
            this.child = child;
            this.weight = weight;
        }

        public int getParent() {
            return parent;
        }

        public int getChild() {
            return child;
        }

        public double getWeight() {
            return weight;
        }
    }

}
