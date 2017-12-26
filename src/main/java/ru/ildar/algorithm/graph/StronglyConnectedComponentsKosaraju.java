package ru.ildar.algorithm.graph;

import ru.ildar.algorithm.datastructure.stack.LinkedStack;
import ru.ildar.algorithm.datastructure.stack.Stack;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *
 * Implements Kosaraju's strongly connected components algorithm (https://en.wikipedia.org/wiki/Kosaraju%27s_algorithm)
 *
 */
public class StronglyConnectedComponentsKosaraju extends AbstractStronglyConnectedComponents {

    private Stack<Integer> vertices;

    public void search(Graph graph) {
        traverseReversedGraph(graph);
        searchComponents(graph);
    }

    private void traverseReversedGraph(Graph graph) {
        vertices = new LinkedStack<>();
        Graph reversed = graph.reversed();
        DepthFirstRecursiveTraversal traversal = new DepthFirstRecursiveTraversal(reversed);

        traversal.setVertexPostProcessor((GraphTraversal tr, int v) -> vertices.push(v));

        for (int v = 0; v < reversed.getVerticesCount(); v++) {
            if (!traversal.isDiscovered(v)) {
                traversal.traverse(v);
            }
        }
    }

    private void searchComponents(Graph graph) {
        init(graph);
        DepthFirstRecursiveTraversal traversal = new DepthFirstRecursiveTraversal(graph);

        traversal.setVertexPreProcessor((GraphTraversal t, int v) -> assign(v));

        while (vertices.size() != 0) {
            int v = vertices.pop();
            if (!traversal.isDiscovered(v)) {
                traversal.traverse(v);
                incrementCurrentComponent();
            }
        }

    }


}
