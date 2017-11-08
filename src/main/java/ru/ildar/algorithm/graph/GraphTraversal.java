package ru.ildar.algorithm.graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */

public interface GraphTraversal {

    void setVertexPreProcessor(VertexProcessorConsumer vertexProcessor);

    void setVertexPostProcessor(VertexProcessorConsumer vertexProcessor);

    void setEdgeProcessor(EdgeProcessorConsumer edgeProcessor);

    void traverse(int start);

    int parentOf(int vertex) throws IllegalArgumentException;

    boolean isDiscovered(int vertex);

    boolean isProcessed(int vertex);

    Graph getGraph();

    @FunctionalInterface
    interface VertexProcessorConsumer {

        void accept(Graph graph, int vertex);

    }

    @FunctionalInterface
    interface EdgeProcessorConsumer {

        void accept(Graph graph, int v1, int v2);

    }
}
