package ru.ildar.algorithm.backtracking;

import ru.ildar.algorithm.datastructure.list.ArrayList;
import ru.ildar.algorithm.datastructure.list.List;
import ru.ildar.algorithm.graph.Graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class AllPathsInGraph {

    private Graph graph;
    private int to;

    private List<int[]> solutions = new ArrayList<>();

    public void find(Graph graph, int from, int to) {
        this.graph = graph;
        this.to = to;

        int[] path = new int[graph.getVerticesCount()];

        for (int i = 0; i < graph.getVerticesCount(); i++) {
            path[i] = -1;
        }

        path[0] = from;

        traverse(path, 0);
    }

    private void traverse(int[] path, int k) {
        addSolution(path, k);

        if (k < graph.getVerticesCount()) {
            for (int adj : graph.getAdjacentVertices(path[k])) {
                if (!isVisited(path, adj)) {
                    int[] branch = new int[graph.getVerticesCount()];
                    System.arraycopy(path, 0, branch, 0, graph.getVerticesCount());

                    branch[k + 1] = adj;
                    traverse(branch, k + 1);
                }
            }
        }
    }

    private boolean isVisited(int[] path, int vertex) {
        for (int v : path) {
            if (vertex == v) {
                return true;
            }
        }

        return false;
    }

    private void addSolution(int[] path, int k) {
        if (path[k] == to) {
            int[] solution = new int[k + 1];
            System.arraycopy(path, 0, solution, 0, k + 1);

            solutions.add(solution);
        }
    }

    public List<int[]> getSolutions() {
        return solutions;
    }

}
