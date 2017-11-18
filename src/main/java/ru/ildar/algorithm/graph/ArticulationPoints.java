package ru.ildar.algorithm.graph;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class ArticulationPoints {

    private Graph graph;

    private boolean[] points;

    private int[] reachableAncestor;
    private int[] vertexOutDegree;

    public void find(Graph g, int start) {
        this.graph = g;
        init();

        AbstractDepthFirstTraversal traversal = new DepthFirstRecursiveTraversal(graph);

        traversal.setVertexPreProcessor((trv, v) ->
                setReachableAncestor(v, v)
        );

        traversal.setEdgeProcessor((trv, v1, v2) -> {
            AbstractDepthFirstTraversal.EdgeClassification classification;
            classification = traversal.getEdgeClassification(v1, v2);

            if (classification == AbstractDepthFirstTraversal.EdgeClassification.TREE) {
                vertexOutDegree[v1]++;
            }

            if ((classification == AbstractDepthFirstTraversal.EdgeClassification.BACK)
                    && (traversal.parentOf(v1) != v2)) {
                if (traversal.getEntryTime(v2) < traversal.getEntryTime(reachableAncestor[v1])) {
                    setReachableAncestor(v1, v2);
                }
            }

        });

        traversal.setVertexPostProcessor((trv, v) -> {
            int parent = traversal.parentOf(v);

            boolean isRoot = v == start;
            boolean isParentRoot = parent == start;

            if (isRoot) {
                if (vertexOutDegree[v] > 1) {
                    setArticulationPoint(v);
                }
            } else {

                if ((reachableAncestor[v] == parent) && !isParentRoot) {
                    setArticulationPoint(parent); // parent articulation vertex
                }

                if (reachableAncestor[v] == v && !isParentRoot) {
                    setArticulationPoint(parent); // bridge articulation vertex

                    if (vertexOutDegree[v] > 0) {
                        setArticulationPoint(v); // bridge articulation vertex
                    }
                }

                int vertexTime = traversal.getEntryTime(reachableAncestor[v]); // earliest reachable time for v
                int parentTime = traversal.getEntryTime(reachableAncestor[parent]); // earliest reachable time for parent[v]

                if (vertexTime < parentTime) {
                    setReachableAncestor(parent, reachableAncestor[v]);
                }
            }

        });

        traversal.traverse(start);
    }

    private void setReachableAncestor(int vertex, int ancestor) {
        reachableAncestor[vertex] = ancestor;
    }

    private void setArticulationPoint(int vertex) {
        points[vertex] = true;
    }

    private void init() {
        points = new boolean[graph.getVerticesCount()];
        reachableAncestor = new int[graph.getVerticesCount()];
        vertexOutDegree = new int[graph.getVerticesCount()];
    }

    public int[] getPoints() {
        int pointsCount = 0;
        for (boolean isArticulation : points) {
            if (isArticulation) {
                pointsCount++;
            }
        }

        int[] ap = new int[pointsCount];

        int idx = 0;
        for (int i = 0; i < points.length; i++) {
            if (points[i]) {
                ap[idx] = i;
                idx++;
            }
        }

        return ap;
    }

}
