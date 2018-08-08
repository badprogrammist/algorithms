package ru.ildar.algorithm.combinatorialsearch

import spock.lang.Specification

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class SudokuTest extends Specification {

    def "Test of solving Sudoku puzzle"() {
        given: "A Sudoku puzzle"
        when: "Trying to solve Sudoku puzzle"
        Sudoku alg = new Sudoku()
        alg.solve(puzzle as int[][])
        int[][] salvation = alg.getSalvation()

        then:
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assert salvation[i][j] == expectedSalvation[i][j]
            }
        }

        where:
        puzzle                        | expectedSalvation
        // easy
        [[0, 0, 0, 3, 0, 0, 2, 0, 0],
         [0, 0, 0, 0, 0, 8, 0, 0, 0],
         [0, 7, 8, 0, 6, 0, 3, 4, 0],
         [0, 4, 2, 5, 1, 0, 0, 0, 0],
         [1, 0, 6, 0, 0, 0, 4, 0, 9],
         [0, 0, 0, 0, 8, 6, 1, 5, 0],
         [0, 3, 5, 0, 9, 0, 7, 6, 0],
         [0, 0, 0, 7, 0, 0, 0, 0, 0],
         [0, 0, 9, 0, 0, 5, 0, 0, 0]] | [[9, 6, 1, 3, 5, 4, 2, 7, 8],
                                         [4, 2, 3, 1, 7, 8, 5, 9, 6],
                                         [5, 7, 8, 9, 6, 2, 3, 4, 1],
                                         [8, 4, 2, 5, 1, 9, 6, 3, 7],
                                         [1, 5, 6, 2, 3, 7, 4, 8, 9],
                                         [3, 9, 7, 4, 8, 6, 1, 5, 2],
                                         [2, 3, 5, 8, 9, 1, 7, 6, 4],
                                         [6, 8, 4, 7, 2, 3, 9, 1, 5],
                                         [7, 1, 9, 6, 4, 5, 8, 2, 3]]

        // hard, solves very long time

//        [[0, 0, 0, 0, 0, 0, 0, 1, 2],
//         [0, 0, 0, 0, 3, 5, 0, 0, 0],
//         [0, 0, 0, 6, 0, 0, 0, 7, 0],
//         [7, 0, 0, 0, 0, 0, 3, 0, 0],
//         [0, 0, 0, 4, 0, 0, 8, 0, 0],
//         [1, 0, 0, 0, 0, 0, 0, 0, 0],
//         [0, 0, 0, 1, 2, 0, 0, 0, 0],
//         [0, 8, 0, 0, 0, 0, 0, 4, 0],
//         [0, 5, 0, 0, 0, 0, 6, 0, 0]] | [[6, 7, 3, 8, 9, 4, 5, 1, 2],
//                                         [9, 1, 2, 7, 3, 5, 4, 8, 6],
//                                         [8, 4, 5, 6, 1, 2, 9, 7, 3],
//                                         [7, 9, 8, 2, 6, 1, 3, 5, 4],
//                                         [5, 2, 6, 4, 7, 3, 8, 9, 1],
//                                         [1, 3, 4, 5, 8, 9, 2, 6, 7],
//                                         [4, 6, 9, 1, 2, 8, 7, 3, 5],
//                                         [2, 8, 7, 3, 5, 6, 1, 4, 9],
//                                         [3, 5, 1, 9, 4, 7, 6, 2, 8]]
    }

}
