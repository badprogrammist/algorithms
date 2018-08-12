package ru.ildar.algorithm.backtracking;


/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
public class Sudoku {

    private static final int SIZE = 9;

    private int[][] puzzle;
    private int[][] salvation;


    public void solve(int[][] puzzle) {
        this.puzzle = puzzle;
        solve();
    }


    private void solve() {
        if (isSolved()) {
            saveSalvation();
        }

        int x = 0;
        int y = 0;

        // TODO optimise choosing the next vacant square
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (puzzle[i][j] == 0) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }

        boolean[] valueOccupied = getOccupiedValues(x, y);

        for (int i = 1; i <= SIZE; i++) {
            if (!valueOccupied[i]) {
                puzzle[x][y] = i;
                solve();
            }
        }

        puzzle[x][y] = 0;
    }

    private void saveSalvation() {
        salvation = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                salvation[i][j] = puzzle[i][j];
            }
        }
    }

    private boolean isSolved() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (puzzle[x][y] == 0) {
                    return false;
                }
            }
        }

        return true;
    }

    // TODO optimise getting the possible values
    private boolean[] getOccupiedValues(int x, int y) {
        boolean[] occupied = new boolean[SIZE + 1];
        occupied[0] = true;

        for (int i = 0; i < SIZE; i++) {
            if (puzzle[x][i] != 0) {
                occupied[puzzle[x][i]] = true;
            }
            if (puzzle[i][y] != 0) {
                occupied[puzzle[i][y]] = true;
            }
        }

        int squareRowStart = 0;
        int squareRowFinish = 2;
        int squareColumnStart = 0;
        int squareColumnFinish = 2;

        if (3 <= x && x <= 5) {
            squareRowStart = 3;
            squareRowFinish = 5;
        } else if (6 <= x && x <= 8) {
            squareRowStart = 6;
            squareRowFinish = 8;
        }

        if (3 <= y && y <= 5) {
            squareColumnStart = 3;
            squareColumnFinish = 5;
        } else if (6 <= y && y <= 8) {
            squareColumnStart = 6;
            squareColumnFinish = 8;
        }

        for (int i = squareRowStart; i <= squareRowFinish; i++) {
            for (int j = squareColumnStart; j <= squareColumnFinish; j++) {
                if (puzzle[i][j] != 0) {
                    occupied[puzzle[i][j]] = true;
                }
            }
        }

        return occupied;
    }

    public int[][] getSalvation() {
        return salvation;
    }

}
