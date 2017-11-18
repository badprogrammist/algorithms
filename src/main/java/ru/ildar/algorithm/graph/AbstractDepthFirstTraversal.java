package ru.ildar.algorithm.graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public abstract class AbstractDepthFirstTraversal extends AbstractGraphTraversal {

    private int time;
    private int[] entryTime;
    private int[] exitTime;

    public AbstractDepthFirstTraversal(Graph graph) {
        super(graph);
    }

    @Override
    protected void init() {
        super.init();
        initTime();
    }

    private void initTime() {
        time = 0;
        entryTime = new int[getGraph().getVerticesCount()];
        exitTime = new int[getGraph().getVerticesCount()];
    }

    protected void iterateEntryTime(int vertex) {
        validateVertex(vertex);
        time += 1;
        entryTime[vertex] = time;
    }

    protected void iterateExitTime(int vertex) {
        validateVertex(vertex);
        time += 1;
        exitTime[vertex] = time;
    }

    public int getEntryTime(int vertex) {
        validateVertex(vertex);
        return entryTime[vertex];
    }

    public int getExitTime(int vertex) {
        validateVertex(vertex);
        return exitTime[vertex];
    }

    public EdgeClassification getEdgeClassification(int v1, int v2) {
        if (parentOf(v2) == v1) return EdgeClassification.TREE;
        if (isDiscovered(v2) && !isProcessed(v2)) return EdgeClassification.BACK;
        if (isProcessed(v2) && (getEntryTime(v2) > getEntryTime(v1))) return EdgeClassification.FORWARD;
        if (isProcessed(v2) && (getEntryTime(v2) < getEntryTime(v1))) return EdgeClassification.CROSS;

        return EdgeClassification.UNCLASSIFIED;
    }

    public enum EdgeClassification {
        TREE, BACK, FORWARD, CROSS, UNCLASSIFIED
    }

}
