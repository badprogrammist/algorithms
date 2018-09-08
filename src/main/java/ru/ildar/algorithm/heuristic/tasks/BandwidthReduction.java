package ru.ildar.algorithm.heuristic.tasks;

import ru.ildar.algorithm.graph.Graph;
import ru.ildar.algorithm.heuristic.TSPRandomSolutionGenerator;
import ru.ildar.algorithm.heuristic.TSPSolution;
import ru.ildar.algorithm.heuristic.TSPSolver;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;
import java.util.function.Function;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *
 * Design and implement an algorithm for solving the bandwidth minimization problem.
 *
 * Heuristic implementation using Simulated Annealing
 */
public class BandwidthReduction {

    public static final Function<Double, Double> EXPONENTIAL_TEMPERATURE_DECREMENT_FUNC = t -> 0.9 * t;

    private final double temperature;
    private final double boltzmannConstant;
    private final Function<Double, Double> temperatureDecrementFunc;
    private final int stepsNumber;
    private final Random random;

    private int[] solution;
    private Graph graph;


    public BandwidthReduction(double temperature, int stepsNumber, double boltzmannConstant) {
        this.temperature = temperature;
        this.boltzmannConstant = boltzmannConstant;
        this.temperatureDecrementFunc = EXPONENTIAL_TEMPERATURE_DECREMENT_FUNC;
        this.stepsNumber = stepsNumber;
        this.random = new Random(Calendar.getInstance().getTimeInMillis());
    }

    public void solve(Graph graph) {
        this.graph = graph;
        this.solution = new int[graph.getVerticesCount()];
        for (int i = 0; i < graph.getVerticesCount(); i++) {
            solution[i] = i;
        }

        double t = temperature;
        boolean oneTime;

        do {
            int cost = calcMaxDistance();
            oneTime = false;

            for (int i = 0; i < stepsNumber; i++) {
                double beforeSwapCost = calcMaxDistance();
                SwappingElements swappedResult = randomSwap();
                swap(swappedResult);
                double afterSwapCost = calcMaxDistance();

                double probability = getTransitionProbability(beforeSwapCost, afterSwapCost, t);

                if (beforeSwapCost < afterSwapCost
                        && probability <= random.nextDouble()) {
                    swap(swappedResult);
                }
            }

            t = reduceTemperature(t);

            if (cost != calcMaxDistance()) {
                oneTime = true;
            }
        } while (oneTime);

        System.out.printf("%s, %d%n", Arrays.toString(solution), getMaxDistance());
    }


    private void swap(SwappingElements elements) {
        int item = solution[elements.i];
        solution[elements.i] = solution[elements.j];
        solution[elements.j] = item;
    }

    private int calcMaxDistance() {
        int distance = Integer.MIN_VALUE;

        for (int i = 0; i < solution.length; i++) {
            for (int j = i; j < solution.length; j++) {
                if (i != j
                        && graph.isAdjacent(solution[i], solution[j])
                        && distance < (j - i)) {
                    distance = j - i;
                }
            }
        }

        return distance;
    }

    private double reduceTemperature(double t) {
        return temperatureDecrementFunc.apply(t);
    }

    private double getTransitionProbability(double beforeSwapCost, double afterSwapCost, double t) {
        return Math.exp((beforeSwapCost - afterSwapCost) / (boltzmannConstant * t));
    }

    private SwappingElements randomSwap() {
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
        } while (i == j);

        return new SwappingElements(i, j);
    }

    private static class SwappingElements {
        public final int i;
        public final int j;

        SwappingElements(int i, int j) {
            this.i = i;
            this.j = j;
        }

    }

    public int[] getSolution() {
        return solution;
    }

    public int getMaxDistance() {
        return calcMaxDistance();
    }

}
