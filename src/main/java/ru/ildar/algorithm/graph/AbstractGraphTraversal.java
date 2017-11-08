package ru.ildar.algorithm.graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public abstract class AbstractGraphTraversal implements GraphTraversal {

    private VertexProcessorConsumer vertexPreProcessor;
    private VertexProcessorConsumer vertexPostProcessor;
    private EdgeProcessorConsumer edgeProcessor;
    private Graph graph;

    public AbstractGraphTraversal(Graph graph) {
        this.graph = graph;
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

    protected VertexProcessorConsumer getVertexPreProcessor() {
        return vertexPreProcessor;
    }

    protected VertexProcessorConsumer getVertexPostProcessor() {
        return vertexPostProcessor;
    }

    protected EdgeProcessorConsumer getEdgeProcessor() {
        return edgeProcessor;
    }

    @Override
    public Graph getGraph() {
        return graph;
    }
}
