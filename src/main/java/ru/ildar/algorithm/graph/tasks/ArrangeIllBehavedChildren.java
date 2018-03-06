package ru.ildar.algorithm.graph.tasks;

import ru.ildar.algorithm.graph.Graph;
import ru.ildar.algorithm.graph.GraphBuilder;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *         <p>
 *         5-23. [5] Your job is to arrange n ill-behaved children in a straight line, facing front. You are given a line of
 *         m statements of the form “i hates j”. If i hates j, then you do not want put i somewhere behind j, because then i
 *         is capable of throwing something at j.
 *         (a) Give an algorithm that orders the line, (or says that it is not possible) in O(m + n) time.
 *         </p>
 */
public class ArrangeIllBehavedChildren {

    public static class StraightLineArrangement {

        private Graph graph;
        private boolean[] visited;
        private boolean[] processed;
        private int[] line;
        private int seek;

        public void arrange(int[][] children) throws CouldNotArrangeException {
            init(children);
            traverse(0);
        }

        private void traverse(int v) throws CouldNotArrangeException {
            visited[v] = true;

            for (int adjacent : graph.getAdjacentVertices(v)) {
                if (visited[adjacent] && !processed[adjacent]) {
                    throw new CouldNotArrangeException("The child " + adjacent + " is already staying in front of the child " + v);
                }

                traverse(adjacent);
            }


            put(v);
        }

        private void put(int child) {
            if (!processed[child]) {
                line[seek] = child;
                seek++;
                processed[child] = true;
            }
        }

        private void init(int[][] children) {
            GraphBuilder builder = GraphBuilder.adjacencyList(true);

            for (int[] entry : children) {
                builder.edge(entry[0], entry[1]);
            }

            graph = builder.create();
            visited = new boolean[graph.getVerticesCount()];
            processed = new boolean[graph.getVerticesCount()];
            line = new int[graph.getVerticesCount()];
            seek = 0;
        }


        public int[] getLine() {
            return line;
        }
    }

    public static class CouldNotArrangeException extends Exception {
        public CouldNotArrangeException(String message) {
            super(message);
        }
    }

}
