package ru.ildar.algorithm.heuristic;

import ru.ildar.algorithm.graph.Graph;

import java.util.Calendar;
import java.util.Random;
import java.util.function.Function;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class SimulatedAnnealingMethod implements TSPSolver {

    public static final Function<Double, Double> EXPONENTIAL_TEMPERATURE_DECREMENT_FUNC = t -> 0.9 * t;

    private final double temperature;
    private final double boltzmannConstant;
    private final Function<Double, Double> temperatureDecrementFunc;
    private final int stepsNumber;

    private TSPSolution solution;
    private Random random;

    public SimulatedAnnealingMethod(double temperature, int stepsNumber, double boltzmannConstant) {
        this.temperature = temperature;
        this.boltzmannConstant = boltzmannConstant;
        this.temperatureDecrementFunc = EXPONENTIAL_TEMPERATURE_DECREMENT_FUNC;
        this.stepsNumber = stepsNumber;
        this.random = new Random(Calendar.getInstance().getTimeInMillis());
    }

    @Override
    public void solve(Graph tsp, int start) {
        TSPRandomSolutionGenerator solutionGenerator = new TSPRandomSolutionGenerator(tsp);
        solution = solutionGenerator.getRandomSolution(start);

        double t = temperature;
        boolean oneTime;

        do {
            double cost = solution.getCost();
            oneTime = false;

            for (int i = 0; i < stepsNumber; i++) {
                double beforeSwapCost = solution.getCost();
                TSPSolution.SwappingElements swappedResult = solution.randomSwap();
                double afterSwapCost = solution.getCost();
                double probability = getTransitionProbability(beforeSwapCost, afterSwapCost, t);

                if (beforeSwapCost < afterSwapCost
                        && probability <= random.nextDouble()) {
                    solution.swap(swappedResult);
                }
            }

            t = reduceTemperature(t);

            if (cost != solution.getCost()) {
                oneTime = true;
            }
        } while (oneTime);

    }

    private double reduceTemperature(double t) {
        return temperatureDecrementFunc.apply(t);
    }

    private double getTransitionProbability(double beforeSwapCost, double afterSwapCost, double t) {
        return Math.exp((beforeSwapCost - afterSwapCost) / (boltzmannConstant * t));
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
