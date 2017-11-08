package ru.ildar.algorithm.graph;

import ru.ildar.algorithm.datastructure.queue.LinkedQueue;
import ru.ildar.algorithm.datastructure.queue.Queue;

import java.util.Iterator;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class BreadthFirstTraversal extends AbstractGraphTraversal {

    private boolean[] processed;
    private boolean[] discovered;
    private int[] parents;
    private Queue<Integer> queue;

    public BreadthFirstTraversal(Graph graph) {
        super(graph);
        init();
    }

    private void init() {
        processed = new boolean[getGraph().getVerticesCount()];
        discovered = new boolean[getGraph().getVerticesCount()];
        parents = new int[getGraph().getVerticesCount()];
        queue = new LinkedQueue<>();
    }

    @Override
    public void traverse(int start) {
        queue.add(start);
        discovered[start] = true;

        while (queue.size() != 0) {
            int vertex = queue.poll();

            if (getVertexPreProcessor() != null) {
                getVertexPreProcessor().accept(getGraph(), vertex);
            }

            processed[vertex] = true;

            Iterator<Integer> edges = getGraph().getAdjacentEdgesIterator(vertex);

            while (edges.hasNext()) {
                int adjacencyVertex = edges.next();

                if (!processed[adjacencyVertex]) {

                    if (getEdgeProcessor() != null) {
                        getEdgeProcessor().accept(getGraph(), vertex, adjacencyVertex);
                    }

                }
                if (!discovered[adjacencyVertex]) {
                    queue.add(adjacencyVertex);
                    discovered[adjacencyVertex] = true;
                    parents[adjacencyVertex] = vertex;
                }
            }

            if (getVertexPostProcessor() != null) {
                getVertexPostProcessor().accept(getGraph(), vertex);
            }

        }
    }

    @Override
    public int parentOf(int vertex) throws IllegalArgumentException {
        if (vertex < 0 || vertex >= parents.length) {
            throw new IllegalArgumentException("The vertex index is incorrect");
        }

        return parents[vertex];
    }

    @Override
    public boolean isDiscovered(int vertex) {
        if (vertex < 0 || vertex >= discovered.length) {
            throw new IllegalArgumentException("The vertex index is incorrect");
        }

        return discovered[vertex];
    }

    @Override
    public boolean isProcessed(int vertex) {
        if (vertex < 0 || vertex >= processed.length) {
            throw new IllegalArgumentException("The vertex index is incorrect");
        }

        return processed[vertex];
    }
}
