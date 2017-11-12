package ru.ildar.algorithm.graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class Cycle {

    private int v1;
    private int v2;

    private boolean cyclical = false;

    public void isCyclical(Graph graph) {
        GraphTraversal traversal = new DepthFirstTraversal(graph);
        traversal.setEdgeProcessor(this::checkEdge);

        traversal.traverse(0);
    }

    private void checkEdge(GraphTraversal traversal, int v1, int v2) {
        if (!cyclical && traversal.parentOf(v2) != v1) {
            cyclical = true;
            this.v1 = v1;
            this.v2 = v2;
        }
    }

    public int getV1() {
        return v1;
    }

    public int getV2() {
        return v2;
    }

    public boolean isCyclical() {
        return cyclical;
    }
}
