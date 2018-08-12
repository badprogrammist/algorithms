package ru.ildar.algorithm.heuristic;

import ru.ildar.algorithm.graph.Graph;

import java.util.Calendar;
import java.util.Random;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *         <p>
 *         The implementation of Monte-Carlo method for solving travelling salesman problem
 */
public class MonteCarloMethod {

    private Graph graph;
    private int[] solution;
    private double solutionCost = 0;
    private int stepsNumber;
    private int attemptsNumber;
    private int start;
    private Random random;

    public MonteCarloMethod(int stepsNumber) {
        this.stepsNumber = stepsNumber;
        random = new Random(Calendar.getInstance().getTimeInMillis());
    }

    public void solve(Graph tsp, int start) {
        this.graph = tsp;
        this.start = start;
        this.attemptsNumber = 10 * graph.getVerticesCount();

        for (int step = 0; step < stepsNumber; step++) {
            int[] s = generateRandomSolution();
            double cost = calcCost(s);

            if (solution == null || cost < solutionCost) {
                solution = s;
                solutionCost = cost;
            }
        }
    }

    private int[] generateRandomSolution() {
        int[] s = new int[graph.getVerticesCount()];
        s[0] = start;

        for (int i = 1; i < s.length; i++) {
            int attempt = 0;

            do {
                s[i] = random.nextInt(graph.getVerticesCount());
                attempt++;
            } while (attempt < attemptsNumber
                    && (s[i - 1] == s[i]
                        || contains(s, s[i], i - 1)
                        || !graph.isAdjacent(s[i - 1], s[i])));

            if (attempt >= attemptsNumber) {
                return generateRandomSolution();
            }
        }

        return s;
    }

    private boolean contains(int[] s, int vertex, int lastIndex) {
        for (int i = 0; i <= lastIndex; i++) {
            if (s[i] == vertex) {
                return true;
            }
        }

        return false;
    }

    private double calcCost(int[] s) {
        double cost = 0;

        for (int i = 0; i < s.length - 1; i++) {
            cost += graph.getEdgeWeight(s[i], s[i + 1]);
        }

        return cost;
    }

    public int[] getSolution() {
        return solution;
    }

    public double getSolutionCost() {
        return solutionCost;
    }

}
