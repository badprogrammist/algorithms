package ru.ildar.algorithm.heuristic;

import ru.ildar.algorithm.graph.Graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *         <p>
 *         The implementation of Monte-Carlo method for solving travelling salesman problem
 */
public class MonteCarloMethod implements TSPSolver {

    private Graph graph;
    private int[] solution;
    private double solutionCost = 0;
    private int stepsNumber;

    public MonteCarloMethod(int stepsNumber) {
        this.stepsNumber = stepsNumber;
    }

    @Override
    public void solve(Graph tsp, int start) {
        this.graph = tsp;
        TSPRandomSolutionGenerator randomGenerator = new TSPRandomSolutionGenerator(tsp, start);

        for (int step = 0; step < stepsNumber; step++) {
            int[] s = randomGenerator.getRandomSolution();
            double cost = getCost(s);

            if (solution == null || cost < solutionCost) {
                solution = s;
                solutionCost = cost;
            }
        }
    }

    private double getCost(int[] s) {
        double cost = 0;

        for (int i = 0; i < s.length - 1; i++) {
            cost += graph.getEdgeWeight(s[i], s[i + 1]);
        }

        return cost;
    }

    @Override
    public int[] getSolution() {
        return solution;
    }

    @Override
    public double getSolutionCost() {
        return solutionCost;
    }

}
