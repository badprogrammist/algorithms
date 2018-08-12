package ru.ildar.algorithm.backtracking;

import ru.ildar.algorithm.datastructure.list.ArrayList;
import ru.ildar.algorithm.datastructure.list.List;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 * TODO add method for retrieving the fundamental solutions
 */
public class NQueensProblem {

    private int n;
    private List<int[]> solutions;

    public void solve(int n) {
        this.n = n;
        this.solutions = new ArrayList<>();
        int[] queens = new int[n];

        next(queens, 0);
    }

    private void next(int[] queens, int k) {
        for (int i = 0; i < n; i++) {
            queens[k] = i;

            if (isSolution(queens)) {
                addSolution(queens);
            }

            if (k + 1 < n) {
                next(queens, k + 1);
            }
        }
    }

    private boolean isSolution(int[] queens) {
        boolean[] rowEngaged = new boolean[n];
        boolean[] columnEngaged = new boolean[n];

        for (int row = 0; row < n; row++) {
            int column = queens[row];

            if (rowEngaged[row]) {
                return false;
            } else {
                rowEngaged[row] = true;
            }

            if (columnEngaged[column]) {
                return false;
            } else {
                columnEngaged[column] = true;
            }

            if (row != 0) {
                int prevQueenColumn = queens[row - 1];

                if (Math.abs(column - prevQueenColumn) == 1) {
                    return false;
                }
            }
        }

        return true;
    }

    private void addSolution(int[] queens) {
        int[] solution = new int[n];
        System.arraycopy(queens, 0, solution, 0, n);

        solutions.add(solution);
    }

    public List<int[]> getSolutions() {
        return solutions;
    }

}
