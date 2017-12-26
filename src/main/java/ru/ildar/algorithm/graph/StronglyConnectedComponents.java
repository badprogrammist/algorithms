package ru.ildar.algorithm.graph;

import ru.ildar.algorithm.datastructure.list.ArrayList;
import ru.ildar.algorithm.datastructure.list.List;
import ru.ildar.algorithm.datastructure.stack.LinkedStack;
import ru.ildar.algorithm.datastructure.stack.Stack;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *
 * Implements Tarjan's strongly connected components algorithm (https://en.wikipedia.org/wiki/Tarjan%27s_strongly_connected_components_algorithm)
 *
 */
public class StronglyConnectedComponents {

    private final static int NOT_ASSIGNED_COMPONENT = -1;

    private int[] low; // oldest vertex surely in component of v
    private int[] scc; // strong component number for each vertex
    private int componentsFound;
    private Stack<Integer> active;
    private DepthFirstRecursiveTraversal traversal;

    public void search(Graph graph) {
        init(graph);

        for (int i = 0; i < graph.getVerticesCount(); i++) {
            if (!traversal.isDiscovered(i)) {
                traversal.traverse(i);
            }
        }
    }

    private void init(Graph graph) {
        componentsFound = 0;
        low = new int[graph.getVerticesCount()];
        scc = new int[graph.getVerticesCount()];
        active = new LinkedStack<>();

        for (int i = 0; i < graph.getVerticesCount(); i++) {
            low[i] = i;
            scc[i] = NOT_ASSIGNED_COMPONENT;
        }

        traversal = new DepthFirstRecursiveTraversal(graph);
        traversal.setVertexPreProcessor(this::preProcessVertex);
        traversal.setEdgeProcessor(this::processEdge);
        traversal.setVertexPostProcessor(this::postProcessVertex);
    }

    private void preProcessVertex(GraphTraversal tr, int v) {
        active.push(v);
    }

    private void processEdge(GraphTraversal tr, int v1, int v2) {
        AbstractDepthFirstTraversal.EdgeClassification c = traversal.getEdgeClassification(v1, v2);

        if (c == AbstractDepthFirstTraversal.EdgeClassification.BACK) {
            if (traversal.getEntryTime(v2) < traversal.getEntryTime(low[v1])) {
                low[v1] = v2;
            }
        }
        if (c == AbstractDepthFirstTraversal.EdgeClassification.CROSS) {
            // component not yet assigned
            if (scc[v2] == NOT_ASSIGNED_COMPONENT) {
                if (traversal.getEntryTime(v2) < traversal.getEntryTime(low[v1])) {
                    low[v1] = v2;
                }
            }
        }
    }

    private void postProcessVertex(GraphTraversal tr, int v) {
        // edge (parent[v],v) cuts off scc
        if (low[v] == v) {
            popComponent(v);
        }

        int parent = traversal.parentOf(v);
        if (traversal.getEntryTime(low[v]) < traversal.getEntryTime(low[parent])) {
            low[parent] = low[v];
        }
    }

    private void popComponent(int v) {
        componentsFound++;
        scc[v] = componentsFound;

        int t = active.pop(); // vertex placeholder
        while (t != v) {
            scc[t] = componentsFound;
            t = active.pop();
        }
    }

    public int getComponents() {
        return componentsFound;
    }

    public List<Integer> getComponent(int c) {
        List<Integer> component = new ArrayList<>();

        for (int i = 0; i < scc.length; i++) {
            if (scc[i] == c + 1) {
                component.add(i);
            }
        }

        return component;
    }

}


