package ru.ildar.algorithm.graph.tasks;

import ru.ildar.algorithm.graph.AbstractDepthFirstTraversal;
import ru.ildar.algorithm.graph.DepthFirstRecursiveTraversal;
import ru.ildar.algorithm.graph.Graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *         <p>
 *         5-13. [5] A vertex cover of a graph G = (V, E) is a subset of vertices V′ such that each
 *         edge in E is incident on at least one vertex of V′.
 *         (a) Give an efficient algorithm to find a minimum-size vertex cover if G is a tree.
 *         (b) Let G = (V,E) be a tree such that the weight of each vertex is equal to the degree of that vertex.
 *         Give an efficient algorithm to find a minimum-weight vertex cover of G.
 *         (c) Let G = (V, E) be a tree with arbitrary weights associated with the vertices.
 *         Give an efficient algorithm to find a minimum-weight vertex cover of G.
 *         </p>
 */
public class VertexCover {

    private int[] weights; // the amount of covered edges
    private int coverSize;

    /**
     * (a) Give an efficient algorithm to find a minimum-size vertex cover if G is a tree.
     *
     * @param graph
     */
    public void findMinVerticesCover(Graph graph) {
        if (graph.isDirected()) {
            throw new IllegalArgumentException("The graph should be undirected");
        }

        initWeights(graph);
        DepthFirstRecursiveTraversal traversal = new DepthFirstRecursiveTraversal(graph);

        traversal.setEdgeProcessor((tr, v1, v2) -> {
            AbstractDepthFirstTraversal.EdgeClassification edgeClass = traversal.getEdgeClassification(v1, v2);

            if (edgeClass == AbstractDepthFirstTraversal.EdgeClassification.BACK) {
                if (weights[v1] == 0 && graph.getDegree(v1) == 1) {
                    if (weights[v2] == 0) {
                        coverSize++;
                        weights[v2] = 1;
                    }

                    weights[v2]++;
                }
            }
        });

        traversal.traverse(0);
    }

    /**
     * (b) Let G = (V,E) be a tree such that the weight of each vertex is equal to the degree of that vertex.
     * Give an efficient algorithm to find a minimum-weight vertex cover of G.
     *
     * @param graph
     */
    public void findMinWeightedVerticesCover(Graph graph) {
        if (graph.isDirected()) {
            throw new IllegalArgumentException("The graph should be undirected");
        }

        initWeights(graph);

        DepthFirstRecursiveTraversal traversal = new DepthFirstRecursiveTraversal(graph);

        traversal.setEdgeProcessor((tr, v1, v2) -> {
            AbstractDepthFirstTraversal.EdgeClassification edgeClass = traversal.getEdgeClassification(v1, v2);

            if (edgeClass == AbstractDepthFirstTraversal.EdgeClassification.TREE) {
                if (weights[v1] == 0) {
                    weights[v1] = graph.getDegree(v1);
                    coverSize++;
                    weights[v2] = -1;
                } else if (weights[v1] < 0) {
                    weights[v2] = graph.getDegree(v2);
                    coverSize++;
                } else if (weights[v1] > 0) {
                    weights[v2] = -1;
                }
            }
        });

        traversal.traverse(0);

    }


    private void initWeights(Graph graph) {
        weights = new int[graph.getVerticesCount()];
        coverSize = 0;
    }

    public int[] getVerticesCover() {
        int[] cover = new int[getVerticesCoverSize()];

        int idx = 0;
        for (int i = 0; i < weights.length; i++) {
            if (weights[i] > 0) {
                cover[idx] = i;
                idx++;
            }
        }

        return cover;
    }

    public int getVertexWeight(int vertex) {
        return weights[vertex];
    }

    public int getVerticesCoverSize() {
        return coverSize;
    }

}
