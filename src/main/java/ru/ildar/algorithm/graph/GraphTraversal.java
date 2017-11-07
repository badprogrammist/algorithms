package ru.ildar.algorithm.graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */

public interface GraphTraversal {

    void setVertexPreProcessor(VertexProcessorConsumer vertexProcessor);

    void setVertexPostProcessor(VertexProcessorConsumer vertexProcessor);

    void setEdgeProcessor(EdgeProcessorConsumer edgeProcessor);

    void run(Graph graph, int start);

    int parentOf(int vertex) throws IllegalArgumentException, IllegalStateException;

    @FunctionalInterface
    interface VertexProcessorConsumer {

        void accept(Graph graph, int vertex);

    }

    @FunctionalInterface
    interface EdgeProcessorConsumer {

        void accept(Graph graph, int v1, int v2);

    }
}
