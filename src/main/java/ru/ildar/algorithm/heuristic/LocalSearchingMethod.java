package ru.ildar.algorithm.heuristic;

import ru.ildar.algorithm.graph.Graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class LocalSearchingMethod implements TSPSolver {

    private Graph graph;

    private TSPSolution solution;

    @Override
    public void solve(Graph tsp, int start) {
        this.graph = tsp;

        TSPRandomSolutionGenerator randomGenerator = new TSPRandomSolutionGenerator(tsp);
        solution = randomGenerator.getRandomSolution(start);

        boolean oneTime = false;

        do {
            oneTime = false;

            for (int i = 0; i < tsp.getVerticesCount() - 3; i++) {
                for (int j = i + 3; j < tsp.getVerticesCount(); j++) {
                    double cost = solution.getCost(i, j);

                    if (solution.swap(i + 1, j - 1)) {
                        if (cost < solution.getCost(i, j)) {
                            solution.swap(i + 1, j - 1);
                        } else {
                            oneTime = true;
                        }
                    }
                }
            }

        } while (oneTime);
    }

    @Override
    public int[] getSolution() {
        return solution.getSolution();
    }

    @Override
    public double getSolutionCost() {
        return solution.getCost();
    }
}
