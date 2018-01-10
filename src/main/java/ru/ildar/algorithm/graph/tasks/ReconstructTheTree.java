package ru.ildar.algorithm.graph.tasks;

import ru.ildar.algorithm.graph.Graph;
import ru.ildar.algorithm.graph.GraphBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *         <p>
 *         Given pre-order and in-order traversals of a binary tree, is it possible to reconstruct the tree?
 *         If so, sketch an algorithm to do it. If not, give a counterexample.
 */
public class ReconstructTheTree {

    private int preOrderCursor;
    private GraphBuilder graphBuilder;
    private int[] inOrder;
    private int[] preOrder;

    private boolean[] discovered;

    private int[] inOrderIndex;

    public Graph reconstruct(int[] inOrder, int[] preOrder) {
        init(inOrder, preOrder);
        process(0, inOrder.length - 1, -1);
        return graphBuilder.create();
    }

    private void process(int start, int end, int lastRoot) {
        int root = getRoot();
        int rootIndex = inOrderIndex[root];
        incrementPreOrderCursor();

        if (lastRoot != -1) {
            graphBuilder.edge(lastRoot, root);
        }

        if (!discovered[rootIndex]) {
            discovered[rootIndex] = true;

            int lengthOfLeftSubTree = rootIndex - start;
            int lengthOfRightSubTree = end - rootIndex;
            int leftIndex = rootIndex - 1;
            int rightIndex = rootIndex + 1;

            processSubTree(lengthOfLeftSubTree, start, rootIndex - 1, root, leftIndex);
            processSubTree(lengthOfRightSubTree, rootIndex + 1, end, root, rightIndex);

        }

    }

    private void processSubTree(int length, int start, int end, int root, int adjacencyVertexIndex) {
        if (length >= 3) {
            process(start, end, root);
        } else if (length == 2) {
            process(start, end, root);
        } else if (length == 1 && !discovered[adjacencyVertexIndex]) {
            int adjacencyVertex = inOrder[adjacencyVertexIndex];
            graphBuilder.edge(root, adjacencyVertex);
            discovered[adjacencyVertexIndex] = true;
            incrementPreOrderCursor();
        }
    }

    private int getRoot() {
        int root = preOrder[preOrderCursor];
        return root;
    }

    private void incrementPreOrderCursor() {
        preOrderCursor++;
    }

    private void init(int[] inOrder, int[] preOrder) {
        preOrderCursor = 0;
        graphBuilder = GraphBuilder.adjacencyList(false);
        this.inOrder = inOrder;
        this.preOrder = preOrder;

        this.discovered = new boolean[inOrder.length];

        inOrderIndex = new int[inOrder.length];
        for (int i = 0; i < inOrder.length; i++) {
            inOrderIndex[inOrder[i]] = i;
        }

    }


}
