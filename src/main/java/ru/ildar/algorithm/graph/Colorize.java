package ru.ildar.algorithm.graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class Colorize {

    private Color[] verticesColors;
    private boolean success = false;

    public void twoColor(Graph graph, int start, Color color) {
        init(graph, start, color);
        success = true;

        GraphTraversal traversal = new BreadthFirstTraversal(graph);
        traversal.setEdgeProcessor((Graph g, int v1, int v2) -> {
            if (verticesColors[v1] == Color.NONE) {
                success = false;
            } else if (verticesColors[v2] != Color.NONE && verticesColors[v1] == verticesColors[v2]) {
                success = false;
            } else {
                if (verticesColors[v1] == Color.WHITE) {
                    verticesColors[v2] = Color.BLACK;
                } else {
                    verticesColors[v2] = Color.WHITE;
                }
            }
        });

        traversal.traverse(start);
    }

    private void init(Graph graph, int start, Color color) {
        if (start < 0 || start >= graph.getVerticesCount()) {
            throw new IllegalArgumentException("The number of start vertex is invalid");
        }

        verticesColors = new Color[graph.getVerticesCount()];
        for (int i = 0; i < graph.getVerticesCount(); i++) {
            verticesColors[i] = Color.NONE;
        }

        verticesColors[start] = color;
    }

    public Color getColor(int vertex) {
        if (vertex < 0 || vertex >= verticesColors.length) {
            throw new IllegalArgumentException("The vertex index is incorrect");
        }

        return verticesColors[vertex];
    }

    public boolean isSuccess() {
        return success;
    }

    public enum Color {
        NONE, WHITE, BLACK
    }

}
