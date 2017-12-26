package ru.ildar.algorithm.graph;

import ru.ildar.algorithm.datastructure.list.ArrayList;
import ru.ildar.algorithm.datastructure.list.List;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public abstract class AbstractStronglyConnectedComponents {

    protected final static int NOT_ASSIGNED_COMPONENT = -1;

    private int currentComponent;
    private int[] scc; // strong component number for each vertex

    public abstract void search(Graph graph);

    protected void init(Graph graph) {
        currentComponent = 0;
        scc = new int[graph.getVerticesCount()];
        for (int i = 0; i < graph.getVerticesCount(); i++) {
            scc[i] = NOT_ASSIGNED_COMPONENT;
        }
    }

    protected void assign(int vertex) {
        scc[vertex] = currentComponent;
    }

    protected void incrementCurrentComponent() {
        currentComponent++;
    }

    public int getComponent(int vertex) {
        return scc[vertex];
    }

    public int getComponents() {
        return currentComponent;
    }

    public List<Integer> getComponentVertices(int c) {
        List<Integer> component = new ArrayList<>();

        for (int i = 0; i < scc.length; i++) {
            if (scc[i] == c) {
                component.add(i);
            }
        }

        return component;
    }

}
