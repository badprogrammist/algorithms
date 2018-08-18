package ru.ildar.algorithm.heuristic;

import ru.ildar.algorithm.graph.Graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public interface TSPSolver {

    void solve(Graph tsp, int start);

    int[] getSolution();

    double getSolutionCost();

}
