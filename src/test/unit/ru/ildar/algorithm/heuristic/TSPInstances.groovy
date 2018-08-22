package ru.ildar.algorithm.heuristic

import ru.ildar.algorithm.graph.Graph
import ru.ildar.algorithm.graph.GraphBuilder

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 */
class TSPInstances {

    private static final Map<Integer, double[]> CAPITALS_OF_USA_COORDINATES = [
            0 : [6734, 1453],
            1 : [2233, 10],
            2 : [5530, 1424],
            3 : [401, 841],
            4 : [3082, 1644],
            5 : [7608, 4458],
            6 : [7573, 3716],
            7 : [7265, 1268],
            8 : [6898, 1885],
            9 : [1112, 2049],
            10: [5468, 2606],
            11: [5989, 2873],
            12: [4706, 2674],
            13: [4612, 2035],
            14: [6347, 2683],
            15: [6107, 669],
            16: [7611, 5184],
            17: [7462, 3590],
            18: [7732, 4723],
            19: [5900, 3561],
            20: [4483, 3369],
            21: [6101, 1110],
            22: [5199, 2182],
            23: [1633, 2809],
            24: [4307, 2322],
            25: [675, 1006],
            26: [7555, 4819],
            27: [7541, 3981],
            28: [3177, 756],
            29: [7352, 4506],
            30: [7545, 2801],
            31: [3245, 3305],
            32: [6426, 3173],
            33: [4608, 1198],
            34: [23, 2216],
            35: [7248, 3779],
            36: [7762, 4595],
            37: [7392, 2244],
            38: [3484, 2829],
            39: [6271, 2135],
            40: [4985, 140],
            41: [1916, 1569],
            42: [7280, 4899],
            43: [7509, 3239],
            44: [10, 2676],
            45: [6807, 2993],
            46: [5185, 3258],
            47: [3023, 1942]]

    private static
    final int[] CAPITALS_OF_USA_TSP_SALVATION = [0, 7, 37, 30, 43, 17, 6, 27, 5, 36, 18, 26, 16, 42, 29, 35, 45, 32, 19, 46, 20, 31, 38, 47, 4, 41, 23, 9, 44, 34, 3, 25, 1, 27, 33, 40, 15, 21, 2, 22, 13, 24, 12, 10, 11, 14, 39, 8, 0]

    public static final double MEDIUM_DISTANCE_BETWEEN_CAPITALS_OF_USA = 3284.6384585649716

    public static Graph getCapitalsOfUSATSP() {
        GraphBuilder gb = GraphBuilder.adjacencyMatrix(false)
        for (int i = 0; i < CAPITALS_OF_USA_COORDINATES.size(); i++) {
            for (int j = 0; j < CAPITALS_OF_USA_COORDINATES.size(); j++) {
                if (i != j) {
                    double dist = distance(i, j)
                    gb.edge(i, j, dist)
                }
            }
        }
        return gb.create()
    }

    public static int[] getCapitalsOfUSATSPSalvation() {
        return CAPITALS_OF_USA_TSP_SALVATION;
    }

    public static double getCapitalsOfUSATSPSalvationCost() {
        double cost = 0

        for (int i = 1; i < CAPITALS_OF_USA_TSP_SALVATION.length; i++) {
            cost += distance(
                    CAPITALS_OF_USA_TSP_SALVATION[i - 1],
                    CAPITALS_OF_USA_TSP_SALVATION[i])
        }

        return cost
    }

    private static double distance(int i, int j) {
        double x1 = CAPITALS_OF_USA_COORDINATES[i][0]
        double y1 = CAPITALS_OF_USA_COORDINATES[i][1]
        double x2 = CAPITALS_OF_USA_COORDINATES[j][0]
        double y2 = CAPITALS_OF_USA_COORDINATES[j][1]
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))
    }

}
