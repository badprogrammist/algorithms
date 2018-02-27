package ru.ildar.algorithm.graph.tasks;

import ru.ildar.algorithm.graph.DepthFirstRecursiveTraversal;
import ru.ildar.algorithm.graph.Graph;

import java.util.Iterator;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *         <p>
 *         5-16. [5] An independent set of an undirected graph G = (V,E) is a set of vertices U such that no edge in E is incident on two vertices of U.
 *         (a) Give an efficient algorithm to find a maximum-size independent set if G is a tree.
 *         (b) Let G = (V, E) be a tree with weights associated with the vertices such that the weight of each vertex is equal to
 *         the degree of that vertex. Give an efficient algorithm to find a maximum independent set of G.
 *         (c) Let G = (V, E) be a tree with arbitrary weights associated with the vertices. Give an efficient algorithm to find
 *         a maximum independent set of G.
 */
public class IndependentSetAlgorithm {

    public static IndependentSet findMaxSetOfTree(Graph graph) {
        if (graph.isDirected()) {
            throw new IllegalArgumentException("The graph should be indirected");
        }

        IndependentSet set = new MaxIndependentSetOfTree();
        set.find(graph);

        return set;
    }

    public interface IndependentSet {
        void find(Graph graph);

        int size();

        int[] getSet();
    }

    private static class MaxIndependentSetOfTree implements IndependentSet {

        private int[] a;
        private int[] b;

        private int size;

        @Override
        public void find(Graph graph) {
            init(graph);

            DepthFirstRecursiveTraversal traversal = new DepthFirstRecursiveTraversal(graph);
            traversal.setVertexPostProcessor((tr, v) -> {
                int bSum = 0;
                int aSum = 1;
                Iterator<Integer> adjacentIter = graph.getAdjacentVerticesIterator(v);

                while (adjacentIter.hasNext()) {
                    int adjacent = adjacentIter.next();

                    if (tr.parentOf(v) != adjacent) {
                        bSum += Math.max(a[adjacent], b[adjacent]);
                        aSum += b[adjacent];
                    }
                }

                a[v] = aSum;
                b[v] = bSum;
            });

            traversal.traverse(0);

            size = Math.max(a[0], b[0]);
        }

        private void init(Graph graph) {
            a = new int[graph.getVerticesCount()];
            b = new int[graph.getVerticesCount()];
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public int[] getSet() {
            int[] set = new int[size()];
            int idx = 0;

            for (int i = 0; i < a.length; i++) {
                if (a[i] > b[i]) {
                    set[idx] = i;
                    idx++;
                }
            }
            return set;
        }
    }

}
