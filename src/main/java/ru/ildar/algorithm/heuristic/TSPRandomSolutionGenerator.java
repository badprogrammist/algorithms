package ru.ildar.algorithm.heuristic;

import ru.ildar.algorithm.graph.Graph;

import java.util.Calendar;
import java.util.Random;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class TSPRandomSolutionGenerator {
    private Graph graph;
    private int start;

    private Random random = new Random(Calendar.getInstance().getTimeInMillis());

    public TSPRandomSolutionGenerator(Graph tsp, int start) {
        this.graph = tsp;
        this.start = start;
    }

    public int[] getRandomSolution() {
        int n = graph.getVerticesCount();
        boolean[] visited = new boolean[n];
        int[] path = new int[n + 1];

        path[0] = start;
        path[n] = start;
        visited[0] = true;

        for (int i = 1; i < n; i++) {

            if (isStuck(visited, path[i - 1])) {
                return getRandomSolution();
            }

            do {
                path[i] = random.nextInt(n);
            } while (path[i - 1] == path[i]
                    || visited[path[i]]
                    || !graph.isAdjacent(path[i - 1], path[i])
                    || (i == n - 1 && !graph.isAdjacent(path[i], start)));

            if (i == n - 2 && !isPossibleReturnBack(visited, path[i])) {
                return getRandomSolution();
            }

            visited[path[i]] = true;
        }

        return path;
    }

    private boolean isStuck(boolean[] visited, int vertex) {
        int[] adjacent = graph.getAdjacentVertices(vertex);
        int visitedNumber = 0;

        for (int adj : adjacent) {
            if (visited[adj]) {
                visitedNumber++;
            }
        }

        return adjacent.length == visitedNumber;
    }


    private boolean isPossibleReturnBack(boolean[] visited, int grandParent) {
        for (int adj : graph.getAdjacentVertices(grandParent)) {
            if (adj != start && !visited[adj] && graph.isAdjacent(adj, start) ) {
                return true;
            }
        }

        return false;
    }

}
