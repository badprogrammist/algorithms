package ru.ildar.algorithm.graph;

import ru.ildar.algorithm.datastructure.queue.LinkedQueue;
import ru.ildar.algorithm.datastructure.queue.Queue;

import java.util.Iterator;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class BreadthFirstTraversal extends AbstractGraphTraversal {

    private Queue<Integer> queue;

    public BreadthFirstTraversal(Graph graph) {
        super(graph);
    }

    @Override
    protected void init() {
        super.init();
        queue = new LinkedQueue<>();
    }

    @Override
    public void traverse(int start) {
        queue.add(start);
        discover(start);

        while (queue.size() != 0) {
            int vertex = queue.poll();

            preProcessVertex(vertex);
            process(vertex);

            Iterator<Integer> edges = getGraph().getAdjacentVerticesIterator(vertex);
            while (edges.hasNext()) {
                int adjacencyVertex = edges.next();

                if (!isProcessed(adjacencyVertex) || getGraph().isDirected()) {

                    processEdge(vertex, adjacencyVertex);
                }

                if (!isDiscovered(adjacencyVertex)) {
                    queue.add(adjacencyVertex);

                    discover(adjacencyVertex);
                    setParent(adjacencyVertex, vertex);
                }
            }

            postProcessVertex(vertex);
        }
    }

}
