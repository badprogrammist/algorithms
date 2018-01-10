package ru.ildar.algorithm.datastructure.tasks;

import ru.ildar.algorithm.datastructure.Iterator;
import ru.ildar.algorithm.datastructure.list.ArrayList;
import ru.ildar.algorithm.datastructure.list.List;
import ru.ildar.algorithm.datastructure.queue.PriorityQueue;
import ru.ildar.algorithm.datastructure.queue.TreePriorityQueue;

/**
 * In the bin-packing problem, we are given n metal objects, each weighing between zero and one kilogram.
 * Our goal is to find the smallest number of bins that will hold the n objects, with each bin holding one kilogram at most.
 * 1. The best-fit heuristic for bin packing is as follows. Consider the objects in the order in which they are given.
 * For each object, place it into the partially filled bin with the smallest amount of extra room after the object is inserted..
 * If no such bin exists, start a new bin. Design an algorithm that implements the best-fit heuristic
 * (taking as input the n weights w1,w2,...,wn and outputting the number of bins used) in O(nlogn) time.
 * 2. Repeat the above using the worst-fit heuristic, where we add the next object in the partially filled bin with
 * the largest amount of extra room after the object is inserted.
 *
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public abstract class ContainerDecomposition {

    static final double MAX_WEIGHT = 1.0;

    private double[] elements;

    private PriorityQueue<NumberContainer> queue = new TreePriorityQueue<>();

    ContainerDecomposition(double[] elements) {
        this.elements = elements;
        decompose();
    }

    protected abstract void decompose();

    public int containersCount() {
        return queue.size();
    }

    public List<NumberContainer> getContainers() {
        return queue.toList();
    }

    void putElementToContainer(double element, Iterator<NumberContainer> iterator) {
        NumberContainer container = null;
        while (iterator.hasNext() && container == null) {
            NumberContainer candidate = iterator.next();
            if (candidate.isPossibleToAdd(element)) {
                container = candidate;
            }
        }
        if(container != null) {
            getQueue().remove(container);
            container.put(element);
            getQueue().add(container);
        } else {
            putElementToNewContainer(element);
        }
    }

    private void putElementToNewContainer(double element) {
        NumberContainer container = new NumberContainer(MAX_WEIGHT);
        container.put(element);
        queue.add(container);
    }

    double[] getElements() {
        return elements;
    }

    PriorityQueue<NumberContainer> getQueue() {
        return queue;
    }

    public static ContainerDecomposition decomposeByBetterFirst(double[] elements) {
        return new BetterFirstContainerDecomposition(elements);
    }

    public static ContainerDecomposition decomposeByWorseFirst(double[] elements) {
        return new WorseFirstContainerDecomposition(elements);
    }

    private static class BetterFirstContainerDecomposition extends ContainerDecomposition {

        BetterFirstContainerDecomposition(double[] elements) {
            super(elements);
        }

        @Override
        protected void decompose() {
            for (double element : getElements()) {
                Iterator<NumberContainer> iterator = getQueue().iterator();
                putElementToContainer(element, iterator);
            }
        }

    }

    private static class WorseFirstContainerDecomposition extends ContainerDecomposition {

        WorseFirstContainerDecomposition(double[] elements) {
            super(elements);
        }

        @Override
        protected void decompose() {
            for (double element : getElements()) {
                Iterator<NumberContainer> iterator = getQueue().ascIterator();
                putElementToContainer(element, iterator);
            }
        }
    }

    private static class NumberContainer implements Comparable<NumberContainer> {

        private static int INITIAL_SIZE = 1;

        private double maxWeight;

        private List<Double> elements = new ArrayList<>(INITIAL_SIZE);

        NumberContainer(double maxWeight) {
            this.maxWeight = maxWeight;
        }

        void put(double element) {
            if (isPossibleToAdd(element)) {
                elements.add(element);
            }
        }

        boolean isPossibleToAdd(double element) {
            return getWeight() + element <= maxWeight;
        }

        double getWeight() {
            double weight = 0.0;
            for (int i = 0; i < elements.size(); i++) {
                weight += elements.get(i);
            }
            return weight;
        }

        @Override
        public int compareTo(NumberContainer o) {
            return Double.compare(this.getWeight(), o.getWeight());
        }
    }

}
