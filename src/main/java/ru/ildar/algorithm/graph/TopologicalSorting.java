package ru.ildar.algorithm.graph;

import ru.ildar.algorithm.datastructure.stack.LinkedStack;
import ru.ildar.algorithm.datastructure.stack.Stack;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class TopologicalSorting {

    private Stack<Integer> sorted;

    public void sort(Graph graph) {
        DepthFirstRecursiveTraversal traversal = new DepthFirstRecursiveTraversal(graph);
        sorted = new LinkedStack<>();

        for (int v = 0; v < graph.getVerticesCount(); v++) {
            traversal.setVertexPostProcessor((GraphTraversal tr, int vertex) -> sorted.push(vertex));
            traversal.setEdgeProcessor((GraphTraversal tr, int v1, int v2) -> {
                if (traversal.getEdgeClassification(v1, v2) == AbstractDepthFirstTraversal.EdgeClassification.BACK) {
                    throw new Error("Warning: directed cycle found, not a DAG.");
                }
            });

            if (!traversal.isDiscovered(v)) {
                traversal.traverse(v);
            }
        }
    }

    public Stack<Integer> getSortedVertices() {
        return sorted;
    }


}
