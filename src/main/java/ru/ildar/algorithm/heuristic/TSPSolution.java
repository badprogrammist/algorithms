package ru.ildar.algorithm.heuristic;

import ru.ildar.algorithm.graph.Graph;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class TSPSolution {

    private int[] solution;
    private Graph graph;
    private Random random;

    public TSPSolution(Graph graph, int[] solution) {
        this.graph = graph;
        this.solution = solution;
        random = new Random(Calendar.getInstance().getTimeInMillis());
    }

    public SwappingElements randomSwap() {
        int i;
        int j;

        do {
            i = random.nextInt(solution.length);
            j = random.nextInt(solution.length);

            if (i > j) {
                int t = i;
                i = j;
                j = t;
            }
        } while (i == j
                || i == 0
                || j == solution.length - 1
                || !swap(i, j));

        return new SwappingElements(i, j);
    }

    public boolean swap(SwappingElements elements) {
        return swap(elements.i, elements.j);
    }

    public boolean swap(int i, int j) {
        if (isPossibleToSwap(i, j)) {
            int v = solution[i];
            solution[i] = solution[j];
            solution[j] = v;

            return true;
        }

        return false;
    }

    private boolean isPossibleToSwap(int i, int j) {
        boolean result = solution[i] != solution[j]
                && graph.isAdjacent(solution[i - 1], solution[j])
                && graph.isAdjacent(solution[i], solution[j + 1]);

        if (result && j - i >= 2) {
            result = graph.isAdjacent(solution[j], solution[i + 1])
                    && graph.isAdjacent(solution[j - 1], solution[i]);
        }

        return result;
    }

    public double getCost(int i, int j) {
        check();
        double cost = 0;

        for (int v = i; v < j; v++) {
            cost += graph.getEdgeWeight(solution[v], solution[v + 1]);
        }

        return cost;
    }

    public double getCost() {
        check();
        double cost = 0;

        for (int i = 0; i < solution.length - 1; i++) {
            cost += graph.getEdgeWeight(solution[i], solution[i + 1]);
        }

        return cost;
    }

    private void check() {
        if (solution == null) {
            throw new IllegalStateException("There is no TSP problem solution");
        }
    }

    public int[] getSolution() {
        return solution;
    }

    public static class SwappingElements {
        public final int i;
        public final int j;

        public SwappingElements(int i, int j) {
            this.i = i;
            this.j = j;
        }

    }
}
