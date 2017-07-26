package ru.ildar.algorithm.datastructure;

import java.util.Arrays;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *
 * Suppose that we are given a sequence of n values x1,x2,...,xn and seek to quickly answer repeated queries of the form:
 * given i and j, find the smallest value in xi,…,xj.
 *
 *   1. Design a data structure that uses O(n^2) space and answers queries in O(1) time.
 *   2. Design a data structure that uses O(n) space and answers queries in O(logn) time. For partial credit,
 *      your data structure can use O(nlogn) space and have O(logn) query time.
 *
 */
public class FastGetMinFromSequence {

    public static GetMinFromSequence cubicSpace(int[] elements) {
        return new GetMinFromSequenceByCubicSpace(elements);
    }

    public static GetMinFromSequence nSpace(int[] elements) {
        return new GetMinFromSequenceByNSpace(elements);
    }

    public static abstract class GetMinFromSequence {
        abstract int getMin(int i, int j) throws IllegalArgumentException;

        int findMin(int[] elements, int i, int j) {
            int min = Integer.MAX_VALUE;
            for (int k = i; k <= j; k++) {
                if (elements[k] < min) {
                    min = elements[k];
                }
            }
            return min;
        }
    }

    static class GetMinFromSequenceByCubicSpace extends GetMinFromSequence {

        private int[][] mins;
        private int length;

        GetMinFromSequenceByCubicSpace(int[] elements) {
            this.length = elements.length;
            init(elements);
        }

        @Override
        public int getMin(int i, int j) throws IllegalArgumentException {
            if (i < 0) {
                throw new IllegalArgumentException("i < 0");
            }
            if (j > length) {
                throw new IllegalArgumentException("j > length");
            }
            if (i > j) {
                throw new IllegalArgumentException("i > j");
            }

            return mins[i][length - j - 1];
        }

        private void init(int[] elements) {
            if (elements != null) {
                mins = new int[elements.length][];
                for (int i = 0; i < elements.length; i++) {
                    mins[i] = new int[elements.length - i];
                    for (int j = i; j < elements.length; j++) {
                        int min = findMin(elements, i, j);
                        mins[i][elements.length - j - 1] = min;
                    }
                }
            }
        }


    }

    static class GetMinFromSequenceByNSpace extends GetMinFromSequence {

        private Range root;

        GetMinFromSequenceByNSpace(int[] elements) {
            generateTree(elements);
        }

        private void generateTree(int[] elements) {
            root = new Range(0, elements.length - 1, elements);
            fillChildren(root);
        }

        private void fillChildren(Range parent) {
            if (parent.range.length > 2) {
                int center = (parent.range.length - 1) / 2;
                parent.left = new Range(parent.i, parent.i + center, copyArray(parent.range, 0, center));
                parent.right = new Range(parent.i + center + 1, parent.j, copyArray(parent.range, center + 1, parent.range.length - 1));
                fillChildren(parent.left);
                fillChildren(parent.right);
            }
        }

        private int[] copyArray(int[] original, int i, int j) {
            int[] n = new int[(j - i) + 1];
            for (int k = 0; k + i <= j; k++) {
                n[k] = original[k + i];
            }
            return n;
        }

        @Override
        public int getMin(int i, int j) throws IllegalArgumentException {
            return getMin(root, i, j);
        }

        private int getMin(Range range, int i, int j) {
            if (range != null) {
                if (range.i == i && range.j == j) {
                    return range.min;
                } else if (range.left != null && range.left.i <= i && j <= range.left.j) {
                    return getMin(range.left, i, j);
                } else if (range.right != null && range.right.i <= i && j <= range.right.j) {
                    return getMin(range.right, i, j);
                } else if (range.left != null && range.right != null) {
                    int leftMin = i != range.left.j ? getMin(range.left, i, range.left.j) : range.range[i];
                    int rightMin = range.right.i != j ? getMin(range.right, range.right.i, j) : range.range[j];
                    return leftMin < rightMin ? leftMin : rightMin;
                }
            }
            return -1;
        }

        private class Range {
            Range left;
            Range right;
            int i;
            int j;
            int[] range;
            int min;

            Range(int i, int j, int[] range) {
                this.i = i;
                this.j = j;
                this.range = range;
                this.min = findMin(range, 0, range.length - 1);
            }
        }

    }


}
