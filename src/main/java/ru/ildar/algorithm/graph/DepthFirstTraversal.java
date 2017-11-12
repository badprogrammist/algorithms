package ru.ildar.algorithm.graph;

import ru.ildar.algorithm.datastructure.stack.LinkedStack;
import ru.ildar.algorithm.datastructure.stack.Stack;

import java.util.Iterator;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class DepthFirstTraversal extends AbstractGraphTraversal {

    private Stack<Integer> stack;

    public DepthFirstTraversal(Graph graph) {
        super(graph);
    }

    @Override
    protected void init() {
        super.init();
        stack = new LinkedStack<>();
    }

    @Override
    public void traverse(int start) {
        stack.push(start);
        discover(start);

        while (stack.size() != 0) {
            int vertex = stack.pop();

            preProcessVertex(vertex);
            process(vertex);

            Iterator<Integer> edges = getGraph().getAdjacentEdgesIterator(vertex);
            while (edges.hasNext()) {
                int adjacencyVertex = edges.next();

                if (!isDiscovered(adjacencyVertex)) {
                    stack.push(adjacencyVertex);

                    discover(adjacencyVertex);
                    setParent(adjacencyVertex, vertex);
                    processEdge(vertex, adjacencyVertex);
                } else if (!isProcessed(adjacencyVertex) || getGraph().isDirected()) {
                    processEdge(vertex, adjacencyVertex);
                }

            }

            postProcessVertex(vertex);
        }
    }


}
