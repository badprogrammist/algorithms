package ru.ildar.algorithm.heuristic;

import ru.ildar.algorithm.graph.Graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *         <p>
 *         The implementation of Monte-Carlo method for solving travelling salesman problem
 */
public class MonteCarloMethod implements TSPSolver {

    private TSPSolution solution;
    private int stepsNumber;

    public MonteCarloMethod(int stepsNumber) {
        this.stepsNumber = stepsNumber;
    }

    @Override
    public void solve(Graph tsp, int start) {
        TSPRandomSolutionGenerator randomGenerator = new TSPRandomSolutionGenerator(tsp);
        solution = randomGenerator.getRandomSolution(start);
        double solutionCost = solution.getCost();

        for (int step = 0; step < stepsNumber; step++) {
            TSPSolution s = randomGenerator.getRandomSolution(start);
            double cost = s.getCost();

            if (cost < solutionCost) {
                solution = s;
                solutionCost = cost;
            }
        }
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
