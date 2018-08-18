package ru.ildar.algorithm.heuristic;

import ru.ildar.algorithm.graph.Graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class LocalSearchingMethod implements TSPSolver {

    private Graph graph;

    private int[] s;
    private double solutionCost;

    @Override
    public void solve(Graph tsp, int start) {
        this.graph = tsp;

        TSPRandomSolutionGenerator randomGenerator = new TSPRandomSolutionGenerator(tsp, start);
        s = randomGenerator.getRandomSolution();

        boolean oneTime = false;

        do {
            oneTime = false;

            for (int i = 0; i < s.length - 3; i++) {
                for (int j = i + 3; j < s.length; j++) {
                    if (s[i] != s[j]) {
                        double partialCost = getCost(i, j);

                        if (isAdjacent(i, j)) {
                            swap(i + 1, j - 1);
                            double swappedPartialCost = getCost(i, j);

                            if (swappedPartialCost >= partialCost) {
                                swap(i + 1, j - 1);
                            } else {
                                oneTime = true;
                            }
                        }
                    }
                }
            }

        } while (oneTime);

        solutionCost = getCost();
    }

    private boolean isAdjacent(int i, int j) {
        boolean adj = graph.isAdjacent(s[i], s[j - 1]) && graph.isAdjacent(s[i + 1], s[j]);
        if (adj && j - i > 3) {
            adj = graph.isAdjacent(s[j - 1], s[i + 2]) && graph.isAdjacent(s[j - 2], s[i + 1]);
        }

        return adj;
    }

    private void swap(int i, int j) {
        int v = s[i];
        s[i] = s[j];
        s[j] = v;
    }

    private double getCost(int i, int j) {
        double cost = 0;

        for (int v = i; v < j; v++) {
            cost += graph.getEdgeWeight(s[v], s[v + 1]);
        }

        return cost;
    }

    private double getCost() {
        double cost = 0;

        for (int i = 0; i < s.length - 1; i++) {
            cost += graph.getEdgeWeight(s[i], s[i + 1]);
        }

        return cost;
    }


    @Override
    public int[] getSolution() {
        return s;
    }

    @Override
    public double getSolutionCost() {
        return solutionCost;
    }
}
