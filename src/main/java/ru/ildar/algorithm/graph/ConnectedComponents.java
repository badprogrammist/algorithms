package ru.ildar.algorithm.graph;

import ru.ildar.algorithm.datastructure.list.ArrayList;
import ru.ildar.algorithm.datastructure.list.List;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class ConnectedComponents {

    private ComponentProcessorConsumer componentProcessor;


    public void search(Graph graph) {
        GraphTraversal traversal = new BreadthFirstTraversal(graph);

        int components = 0;
        for (int v = 0; v < graph.getVerticesCount(); v++) {
            List<Integer> vertices = new ArrayList<>(graph.getVerticesCount());

            traversal.setVertexPreProcessor((Graph g, int vertex) -> vertices.add(vertex));

            if (!traversal.isDiscovered(v)) {
                traversal.traverse(v);

                if (componentProcessor != null) {
                    componentProcessor.accept(components, vertices);
                }
                components++;
            }
        }
    }

    public void setComponentProcessor(ComponentProcessorConsumer componentProcessor) {
        this.componentProcessor = componentProcessor;
    }

    @FunctionalInterface
    public interface ComponentProcessorConsumer {

        void accept(int component, List<Integer> vertices);

    }

}
