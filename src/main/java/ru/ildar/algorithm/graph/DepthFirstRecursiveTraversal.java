package ru.ildar.algorithm.graph;

import java.util.Iterator;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class DepthFirstRecursiveTraversal extends AbstractDepthFirstTraversal {

    public DepthFirstRecursiveTraversal(Graph graph) {
        super(graph);
    }

    @Override
    public void traverse(int start) {
        discover(start);

        preProcessVertex(start);
        iterateEntryTime(start);

        Iterator<Integer> adjacencyEdgesIterator = getGraph().getAdjacentEdgesIterator(start);
        while (adjacencyEdgesIterator.hasNext()) {
            int adjacencyVertex = adjacencyEdgesIterator.next();

            if (!isDiscovered(adjacencyVertex)) {
                setParent(adjacencyVertex, start);
                processEdge(start, adjacencyVertex);
                traverse(adjacencyVertex);

            } else if (!isProcessed(adjacencyVertex) || getGraph().isDirected()) {
                processEdge(start, adjacencyVertex);
            }
        }

        postProcessVertex(start);
        iterateExitTime(start);
        process(start);
    }

}
