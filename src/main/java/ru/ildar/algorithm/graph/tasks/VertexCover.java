package ru.ildar.algorithm.graph.tasks;

import ru.ildar.algorithm.graph.AbstractDepthFirstTraversal;
import ru.ildar.algorithm.graph.DepthFirstRecursiveTraversal;
import ru.ildar.algorithm.graph.Graph;
import ru.ildar.algorithm.graph.GraphTraversal;

import java.util.Iterator;

public class VertexCover {
    private static VertexCoverAlgorithm vertexCoverOfNotWeightedTree = new VertexCoverOfNotWeightedTree();
    private static VertexCoverAlgorithm vertexCoverOfDegreeWeightedTree = new VertexCoverOfDegreeWeightedTree();
    private static VertexCoverAlgorithm vertexCoverOfRandomWeightedTree = new VertexCoverOfRandomWeightedTree();

    public static VertexCoverAlgorithm findVertexCoverOfNotWeightedTree(Graph graph) {
        vertexCoverOfNotWeightedTree.find(graph);
        return vertexCoverOfNotWeightedTree;
    }

    public static VertexCoverAlgorithm findVertexCoverOfDegreeWeightedTree(Graph graph) {
        vertexCoverOfDegreeWeightedTree.find(graph);
        return vertexCoverOfDegreeWeightedTree;
    }

    public static VertexCoverAlgorithm findVertexCoverOfRandomWeightedTree(Graph graph) {
        vertexCoverOfRandomWeightedTree.find(graph);
        return vertexCoverOfRandomWeightedTree;
    }


    public interface VertexCoverAlgorithm {
        void find(Graph graph);

        int getVerticesCoverSize();

        int getCoveredEdgesAmount(int vertex);

        int[] getVerticesCover();

        double getVerticesCoverWeight();
    }


    private static abstract class VertexCoverOfTree implements VertexCoverAlgorithm {
        private int[] coveredEdges; // the amount of covered edges
        private int coverSize;

        protected void setCoveredEdgesAmount(int vertex, int amount) {
            coveredEdges[vertex] = amount;
        }

        protected void incrementCoverSize() {
            coverSize++;
        }

        protected void initCoveredEdges(Graph graph) {
            coveredEdges = new int[graph.getVerticesCount()];
            coverSize = 0;
        }

        public int[] getVerticesCover() {
            int[] cover = new int[getVerticesCoverSize()];

            int idx = 0;
            for (int i = 0; i < coveredEdges.length; i++) {
                if (coveredEdges[i] > 0) {
                    cover[idx] = i;
                    idx++;
                }
            }

            return cover;
        }

        public int getCoveredEdgesAmount(int vertex) {
            return coveredEdges[vertex];
        }

        public int getVerticesCoverSize() {
            return coverSize;
        }
    }

    /**
     * <p>
     * (a) Give an efficient algorithm to find a minimum-size vertex cover if G is a tree.
     * </p>
     */
    private static class VertexCoverOfNotWeightedTree extends VertexCoverOfTree {

        @Override
        public void find(Graph graph) {
            if (graph.isDirected()) {
                throw new IllegalArgumentException("The graph should be undirected");
            }

            initCoveredEdges(graph);
            DepthFirstRecursiveTraversal traversal = new DepthFirstRecursiveTraversal(graph);

            traversal.setEdgeProcessor((tr, v1, v2) -> {
                AbstractDepthFirstTraversal.EdgeClassification edgeClass = traversal.getEdgeClassification(v1, v2);

                if (edgeClass == AbstractDepthFirstTraversal.EdgeClassification.BACK) {
                    if (getCoveredEdgesAmount(v1) == 0 && graph.getDegree(v1) == 1) {
                        if (getCoveredEdgesAmount(v2) == 0) {
                            setCoveredEdgesAmount(v2, 1);
                            incrementCoverSize();
                        }
                        setCoveredEdgesAmount(v2, getCoveredEdgesAmount(v2) + 1);
                    }
                }
            });

            traversal.traverse(0);
        }

        @Override
        public double getVerticesCoverWeight() {
            return 0;
        }
    }

    /**
     * <p>
     * (b) Let G = (V,E) be a tree such that the weight of each vertex is equal to the degree of that vertex.
     * Give an efficient algorithm to find a minimum-weight vertex cover of G.
     * </p>
     */
    private static class VertexCoverOfDegreeWeightedTree extends VertexCoverOfTree {

        @Override
        public void find(Graph graph) {
            if (graph.isDirected()) {
                throw new IllegalArgumentException("The graph should be undirected");
            }

            initCoveredEdges(graph);

            DepthFirstRecursiveTraversal traversal = new DepthFirstRecursiveTraversal(graph);

            traversal.setEdgeProcessor((tr, v1, v2) -> {
                AbstractDepthFirstTraversal.EdgeClassification edgeClass = traversal.getEdgeClassification(v1, v2);

                if (edgeClass == AbstractDepthFirstTraversal.EdgeClassification.TREE) {
                    int v1CoveredEdges = getCoveredEdgesAmount(v1);

                    if (v1CoveredEdges == 0) {
                        setCoveredEdgesAmount(v1, graph.getDegree(v1));
                        setCoveredEdgesAmount(v2, -1);
                        incrementCoverSize();
                    } else if (v1CoveredEdges < 0) {
                        setCoveredEdgesAmount(v2, graph.getDegree(v2));
                        incrementCoverSize();
                    } else if (v1CoveredEdges > 0) {
                        setCoveredEdgesAmount(v2, -1);
                    }
                }
            });

            traversal.traverse(0);
        }

        @Override
        public double getVerticesCoverWeight() {
            double weight = 0;

            for (int vertex : getVerticesCover()) {
                weight += getCoveredEdgesAmount(vertex);
            }

            return weight;
        }
    }

    /**
     * <p>
     * (c) Let G = (V, E) be a tree with arbitrary weights associated with the vertices.
     * Give an efficient algorithm to find a minimum-weight vertex cover of G.
     * </p>
     * <p>
     * Solution: We know we will be able to remove at most one every other node,
     * so we use a two-coloring technique (Red/Black) and perform a post-order traversal.
     * Let's assume we will remove all the Black node. When we process a node,
     * we also store with each node the sum over its immediate children of
     * the respective Red and Black weight for the subtree. If not all of the children are Red,
     * we need to mark the current node as Red. But we also have the option to reverse
     * the coloring of all the Red-children's subtree. So we look at the sum over
     * the red-children for Red and Black, and compare the difference of these sum to the current node's weight.
     * If the current node's weight is above, we swap the coloring for these subtree.
     * The current node will record the Black and Red sum of its children's subtree,
     * and add its own weight to its color.
     * </p>
     */
    private static class VertexCoverOfRandomWeightedTree implements VertexCoverAlgorithm {
        private static byte RED = -1;
        private static byte BLACK = 1;
        private static byte UNCOLORED = 0;

        private double[] redWeights;
        private double[] blackWeights;

        private byte[] vertexColor;

        private Graph graph;
        private int coverSize;

        @Override
        public void find(Graph graph) {
            if (graph.isDirected()) {
                throw new IllegalArgumentException("The graph should be undirected");
            }

            this.graph = graph;

            init();

            DepthFirstRecursiveTraversal traversal = new DepthFirstRecursiveTraversal(graph);
            traversal.setVertexPreProcessor(this::colorizeVertex);
            traversal.setVertexPostProcessor((tr, v) -> {
                calcWeight(tr, v);
                optimize(tr, v);
            });

            traversal.traverse(0);
        }

        private void colorizeVertex(GraphTraversal tr, int vertex) {
            if (vertex == 0) {
                setColor(vertex, RED);
            } else {
                int parent = tr.parentOf(vertex);

                if (getColor(parent) == RED) {
                    setColor(vertex, BLACK);
                } else {
                    setColor(vertex, RED);
                }
            }
        }

        private void calcWeight(GraphTraversal tr, int vertex) {
            Iterator<Integer> adjacentVertexIter = graph.getAdjacentVerticesIterator(vertex);
            double redWeight = 0;
            double blackWeight = 0;
            double vertexWeight = graph.getVertexWeight(vertex);

            while (adjacentVertexIter.hasNext()) {
                int adjacentVertex = adjacentVertexIter.next();

                if (adjacentVertex != tr.parentOf(vertex)) {
                    redWeight += getWeight(adjacentVertex, RED);
                    blackWeight += getWeight(adjacentVertex, BLACK);
                }
            }

            if (getColor(vertex) == RED) {
                setWeights(vertex, redWeight + vertexWeight, blackWeight);
            } else {
                setWeights(vertex, redWeight, blackWeight + vertexWeight);
            }
        }

        private void optimize(GraphTraversal tr, int vertex) {
            double redWeight = getWeight(vertex, RED);
            double blackWeight = getWeight(vertex, BLACK);

            if (blackWeight != 0 && redWeight > blackWeight) {
                invertColor(tr, vertex);
            }
        }

        private byte getColor(int vertex) {
            return vertexColor[vertex];
        }

        private double getWeight(int vertex, byte color) {
            if (color == UNCOLORED) {
                throw new RuntimeException("The vertex has not been colorized");
            }

            if (color == RED) {
                return redWeights[vertex];
            } else {
                return blackWeights[vertex];
            }
        }

        private void setWeights(int vertex, double redWeight, double blackWeight) {
            redWeights[vertex] = redWeight;
            blackWeights[vertex] = blackWeight;
        }

        private void setColor(int vertex, byte color) {
            if ((getColor(vertex) == UNCOLORED || getColor(vertex) == BLACK) && color == RED) {
                coverSize++;
            } else if (vertexColor[vertex] == RED && color == BLACK) {
                coverSize--;
            }
            vertexColor[vertex] = color;
        }

        private void invertColor(GraphTraversal tr, int vertex) {
            if (getColor(vertex) == UNCOLORED) {
                throw new RuntimeException("The child has not been colorized");
            }

            if (getColor(vertex) == BLACK) {
                setColor(vertex, RED);
            } else {
                setColor(vertex, BLACK);
            }

            double redWeight = redWeights[vertex];
            setWeights(vertex, blackWeights[vertex], redWeight);

            Iterator<Integer> adjacencyVerticesIter = graph.getAdjacentVerticesIterator(vertex);

            while (adjacencyVerticesIter.hasNext()) {
                int adjacencyVertex = adjacencyVerticesIter.next();

                if (adjacencyVertex != tr.parentOf(vertex)) {
                    invertColor(tr, adjacencyVertex);
                }
            }
        }

        private void init() {
            redWeights = new double[graph.getVerticesCount()];
            blackWeights = new double[graph.getVerticesCount()];
            vertexColor = new byte[graph.getVerticesCount()];
            coverSize = 0;
        }

        @Override
        public double getVerticesCoverWeight() {
            double weight = 0;

            for (int i = 0; i < vertexColor.length; i++) {
                if (vertexColor[i] == RED) {
                    weight += graph.getVertexWeight(i);
                }
            }

            return weight;
        }

        @Override
        public int getVerticesCoverSize() {
            return coverSize;
        }

        @Override
        public int getCoveredEdgesAmount(int vertex) {
            return graph.getDegree(vertex);
        }

        @Override
        public int[] getVerticesCover() {
            int[] cover = new int[getVerticesCoverSize()];

            int idx = 0;
            for (int i = 0; i < vertexColor.length; i++) {
                if (vertexColor[i] == RED) {
                    cover[idx] = i;
                    idx++;
                }
            }

            return cover;
        }
    }

}
