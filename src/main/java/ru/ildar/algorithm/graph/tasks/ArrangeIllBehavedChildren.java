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
 *         (b) Suppose instead you want to arrange the children in rows such that if i hates j, then i must be in a lower numbered row than j.
 *         Give an efficient algorithm to find the minimum number of rows needed, if it is possible.
 *         </p>
 */
public class ArrangeIllBehavedChildren {

    private static class Node {
        int child;
        Node prev;

        Node(int child) {
            this.child = child;
        }
    }

    private static class Line {

        private Node first;
        private int size = 0;

        Line() {
        }

        boolean add(int child, int beforeChild) {
            Node beforeNode = find(beforeChild);

            if (beforeNode != null && find(child) == null) {
                Node node = new Node(child);
                Node prev = beforeNode.prev;

                beforeNode.prev = node;
                node.prev = prev;

                size++;

                return true;
            }

            return false;
        }

        void add(int child) {
            if (find(child) == null) {
                Node node = new Node(child);

                if (first == null) {
                    first = node;
                } else {
                    first.prev = node;
                    first = node;
                }

                size++;
            }
        }

        boolean isStay(int before, int child) {
            Node childNode = find(child);

            if (childNode == null) {
                return false;
            }

            Node beforeNode = childNode.prev;

            while (beforeNode != null) {
                if (beforeNode.child == before) {
                    return true;
                }

                beforeNode = beforeNode.prev;
            }

            return false;
        }

        private Node find(int child) {
            Node node = first;

            while (node != null) {
                if (node.child == child) {
                    return node;
                }
                node = node.prev;
            }

            return null;
        }

        private int[] toArray() {
            int[] line = new int[size];
            Node node = first;
            int idx = size - 1;

            while (node != null) {
                line[idx] = node.child;
                idx--;
                node = node.prev;
            }


            return line;
        }

    }

    public static class StraightLineArrangement {

        private Graph graph;
        private Line line;

        public void arrange(int[][] children) throws CouldNotArrangeException {
            initGraph(children);
            line = new Line();
            traverse(0);
        }

        private void traverse(int v) throws CouldNotArrangeException {
            line.add(v);

            for (int adjacent : graph.getAdjacentVertices(v)) {
                if (line.isStay(v, adjacent)) {
                    throw new CouldNotArrangeException("The child " + adjacent + "is already staying in front of the child " + v);
                }

                boolean success = line.add(adjacent, v);

                if (!success) {
                    line.add(adjacent);
                }

                traverse(adjacent);
            }
        }


        private void initGraph(int[][] children) {
            GraphBuilder builder = GraphBuilder.adjacencyList(true);

            for (int[] entry : children) {
                builder.edge(entry[0], entry[1]);
            }

            graph = builder.create();

        }

        public int[] getLine() {
            return line.toArray();
        }
    }

    public static class CouldNotArrangeException extends Exception {
        public CouldNotArrangeException(String message) {
            super(message);
        }
    }

}
