package ru.ildar.algorithm.graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public abstract class AbstractGraphTraversal implements GraphTraversal {

    private boolean[] processed;
    private boolean[] discovered;
    private int[] parents;
    private VertexProcessorConsumer vertexPreProcessor;
    private VertexProcessorConsumer vertexPostProcessor;
    private EdgeProcessorConsumer edgeProcessor;
    private Graph graph;

    public AbstractGraphTraversal(Graph graph) {
        this.graph = graph;
        init();
    }

    protected void init() {
        processed = new boolean[getGraph().getVerticesCount()];
        discovered = new boolean[getGraph().getVerticesCount()];
        parents = new int[getGraph().getVerticesCount()];
    }

    protected void preProcessVertex(int vertex) {
        if (vertexPreProcessor != null) {
            vertexPreProcessor.accept(this, vertex);
        }
    }

    protected void postProcessVertex(int vertex) {
        if (vertexPostProcessor != null) {
            vertexPostProcessor.accept(this, vertex);
        }
    }

    protected void processEdge(int vertex, int adjacencyVertex) {
        if (edgeProcessor != null) {
            edgeProcessor.accept(this, vertex, adjacencyVertex);
        }
    }

    protected void setParent(int vertex, int parent) {
        if (vertex < 0 || vertex >= parents.length) {
            throw new IllegalArgumentException("The vertex index is incorrect");
        }

        parents[vertex] = parent;
    }

    @Override
    public int parentOf(int vertex) throws IllegalArgumentException {
        if (vertex < 0 || vertex >= parents.length) {
            throw new IllegalArgumentException("The vertex index is incorrect");
        }

        return parents[vertex];
    }

    protected void discover(int vertex) {
        if (vertex < 0 || vertex >= discovered.length) {
            throw new IllegalArgumentException("The vertex index is incorrect");
        }

        discovered[vertex] = true;
    }

    protected void process(int vertex) {
        if (vertex < 0 || vertex >= processed.length) {
            throw new IllegalArgumentException("The vertex index is incorrect");
        }

        processed[vertex] = true;
    }

    @Override
    public boolean isDiscovered(int vertex) {
        if (vertex < 0 || vertex >= discovered.length) {
            throw new IllegalArgumentException("The vertex index is incorrect");
        }

        return discovered[vertex];
    }

    @Override
    public boolean isProcessed(int vertex) {
        if (vertex < 0 || vertex >= processed.length) {
            throw new IllegalArgumentException("The vertex index is incorrect");
        }

        return processed[vertex];
    }

    @Override
    public void setVertexPreProcessor(VertexProcessorConsumer vertexProcessor) {
        this.vertexPreProcessor = vertexProcessor;
    }

    @Override
    public void setVertexPostProcessor(VertexProcessorConsumer vertexProcessor) {
        this.vertexPostProcessor = vertexProcessor;
    }

    @Override
    public void setEdgeProcessor(EdgeProcessorConsumer edgeProcessor) {
        this.edgeProcessor = edgeProcessor;
    }

    @Override
    public Graph getGraph() {
        return graph;
    }
}
