package ru.ildar.algorithm.graph;

import ru.ildar.algorithm.datastructure.stack.LinkedStack;
import ru.ildar.algorithm.datastructure.stack.Stack;

import java.util.Iterator;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class BreadthFirstTraversal extends AbstractGraphTraversal {

    private boolean[] processed;
    private boolean[] discovered;
    private int[] parents;
    private Stack<Integer> stack;

    public BreadthFirstTraversal() {
    }

    private void init(Graph graph) {
        processed = new boolean[graph.getVerticesCount()];
        discovered = new boolean[graph.getVerticesCount()];
        parents = new int[graph.getVerticesCount()];
        stack = new LinkedStack<>();
    }

    @Override
    public void run(Graph graph, int start) {
        init(graph);

        stack.put(start);
        discovered[start] = true;

        while (stack.size() != 0) {
            int vertex = stack.poll();

            if (getVertexPreProcessor() != null) {
                getVertexPreProcessor().accept(graph, vertex);
            }

            processed[vertex] = true;

            Iterator<Integer> edges = graph.getAdjacentEdgesIterator(vertex);

            while (edges.hasNext()) {
                int adjacencyVertex = edges.next();

                if (!processed[adjacencyVertex]) {

                    if (getEdgeProcessor() != null) {
                        getEdgeProcessor().accept(graph, vertex, adjacencyVertex);
                    }

                }
                if (!discovered[adjacencyVertex]) {
                    stack.put(adjacencyVertex);
                    discovered[adjacencyVertex] = true;
                    parents[adjacencyVertex] = vertex;
                }
            }

            if (getVertexPostProcessor() != null) {
                getVertexPostProcessor().accept(graph, vertex);
            }


        }
    }

    @Override
    public int parentOf(int vertex) throws IllegalArgumentException, IllegalStateException {
        if (parents == null) {
            throw new IllegalStateException("You need to run traversing first for getting inheritance info");
        }

        if (vertex < 0 || vertex >= parents.length) {
            throw new IllegalArgumentException("The vertex index is incorrect");
        }

        return parents[vertex];
    }
}
