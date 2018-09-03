package ru.ildar.algorithm.backtracking.tasks;

import ru.ildar.algorithm.datastructure.list.ArrayList;
import ru.ildar.algorithm.datastructure.list.List;
import ru.ildar.algorithm.datastructure.queue.LinkedQueue;
import ru.ildar.algorithm.datastructure.queue.Queue;
import ru.ildar.algorithm.graph.Graph;

import java.util.Arrays;
import java.util.Comparator;


/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *         <p>
 *         Design and implement an algorithm for testing whether two graphs are isomorphic to each other.
 *         With proper pruning, graphs on hundreds of vertices can be tested reliably.
 */
public class GraphIsomorphic {

    private Graph g1;
    private Graph g2;

    private int[] mapping;
    private boolean isomorphic;

    public void test(Graph g1, Graph g2) {
        this.g1 = g1;
        this.g2 = g2;
        isomorphic = false;

        if (g1.getVerticesCount() != g2.getVerticesCount()) {
            return;
        }

        if (g1.getEdgesCount() != g2.getEdgesCount()) {
            return;
        }

        Integer[] vertices1 = getSortedVertices(g1);
        Integer[] vertices2 = getSortedVertices(g2);

        for (int i = 0; i < g1.getVerticesCount(); i++) {
            if (g1.getDegree(vertices1[i]) != g2.getDegree(vertices2[i])) {
                return;
            }
        }

        for (int i = 0; i < g1.getVerticesCount(); i++) {
            if (isomorphic) {
                break;
            }
            test(vertices1[i], vertices2[i],
                    new boolean[g1.getVerticesCount()],
                    new boolean[g2.getVerticesCount()],
                    new int[g1.getVerticesCount()]
            );
        }

    }

    private Integer[] getSortedVertices(Graph g) {
        Integer[] vertices = new Integer[g.getVerticesCount()];
        for (int v = 0; v < g.getVerticesCount(); v++) {
            vertices[v] = v;
        }
        Arrays.sort(vertices, Comparator.comparingInt(g::getDegree));

        return vertices;
    }

    private void test(int v1, int v2, boolean[] visited1, boolean[] visited2, int[] mapping) {
        visited1[v1] = true;
        visited2[v2] = true;

        mapping[v1] = v2;

        if (isAllVisited(visited1) && isSolution(mapping)) {
            isomorphic = true;
            this.mapping = new int[mapping.length];
            System.arraycopy(mapping, 0, this.mapping, 0, mapping.length);
            return;
        }

        for (int adj1 : g1.getAdjacentVertices(v1)) {
            if (!visited1[adj1]) {
                int[] candidates = getCandidates(g2, v2, g1.getDegree(adj1));

                for (int adj2 : candidates) {
                    if (!visited2[adj2]) {
                        test(adj1, adj2, visited1, visited2, mapping);
                    }
                }
            }
        }
    }

    private int[] getCandidates(Graph graph, int v, int degree) {
        List<Integer> candidates = new ArrayList<>();

        for (int adj : graph.getAdjacentVertices(v)) {
            if (graph.getDegree(adj) == degree) {
                candidates.add(adj);
            }
        }

        int[] a = new int[candidates.size()];
        for (int i = 0; i < candidates.size(); i++) {
            a[i] = candidates.get(i);
        }

        return a;
    }

    private boolean isAllVisited(boolean[] visited) {
        for (int v = 0; v < g1.getVerticesCount(); v++) {
            if (!visited[v]) {
                return false;
            }
        }

        return true;
    }

    private boolean isSolution(int[] mapping) {
        Queue<Integer> queue = new LinkedQueue<>();
        boolean[] visited = new boolean[g1.getVerticesCount()];
        queue.add(0);

        while (queue.size() != 0) {
            int v = queue.poll();
            visited[v] = true;

            for (int adj : g1.getAdjacentVertices(v)) {
                if (!visited[adj]) {
                    if (!g2.isAdjacent(mapping[v], mapping[adj])) {
                        return false;
                    }

                    queue.add(adj);
                }
            }
        }

        return true;
    }

    public boolean isIsomorphic() {
        return isomorphic;
    }

    public int[] getMapping() {
        return mapping;
    }
}
