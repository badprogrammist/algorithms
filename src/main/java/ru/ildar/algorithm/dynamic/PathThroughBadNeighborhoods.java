package ru.ildar.algorithm.dynamic;

import ru.ildar.algorithm.datastructure.stack.LinkedStack;
import ru.ildar.algorithm.datastructure.stack.Stack;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *
 * 8-16. Consider a city whose streets are defined by an X×Y grid.
 * We are interested in walking from the upper left-hand corner of the grid to the lower right-hand corner.
 * Unfortunately, the city has bad neighborhoods, whose intersections we do not want to walk in.
 * We are given an X×Y matrix BAD, where BAD[i,j] = yes'
 * if and only if the intersection between streets i and j is in a neighborhood to avoid.
 * (c) Give an O(XY) algorithm to find the shortest path across the grid that avoids bad neighborhoods.
 * You may assume that all blocks are of equal length. For partial credit, give an O(X2Y2) algorithm.
 *
 */
public class PathThroughBadNeighborhoods {

    private int[][] neighborhoods;
    private Position[][] parent;
    private int[][] dist;

    private final Function<Position, Position> top = (Position p) -> new Position(p.x, p.y - 1);
    private final Function<Position, Position> left = (Position p) -> new Position(p.x - 1, p.y);
    private final Function<Position, Position> right = (Position p) -> new Position(p.x, p.y + 1);
    private final Function<Position, Position> bottom = (Position p) -> new Position(p.x + 1, p.y);

    private final Object[] directions = new Object[] {
            top,
            left,
            right,
            bottom
    };

    public void find(int[][] neighborhoods) {
        this.neighborhoods = neighborhoods;
        this.parent = new Position[neighborhoods.length][neighborhoods[0].length];
        boolean[][] visited = new boolean[neighborhoods.length][neighborhoods[0].length];
        this.dist = new int[neighborhoods.length][neighborhoods[0].length];

        for (int i = 0; i < neighborhoods.length; i++) {
            for (int j = 0; j < neighborhoods[i].length; j++) {
                dist[i][j] = Integer.MAX_VALUE;
            }
        }

        Stack<Position> path = new LinkedStack<>();
        path.push(new Position(0, 0));
        dist[0][0] = 0;

        while (path.size() != 0) {
            Position current = path.pop();
            visited[current.x][current.y] = true;

            for (Object df : directions) {
                Function<Position, Position> directionFun = (Function<Position, Position>) df;
                Position adj = directionFun.apply(current);

                if (!adj.isValid() || visited[adj.x][adj.y] || neighborhoods[adj.x][adj.y] == 1) {
                    continue;
                }

                int distance = dist[current.x][current.y] + 1;
                if (distance < dist[adj.x][adj.y]) {
                    dist[adj.x][adj.y] = distance;
                    parent[adj.x][adj.y] = current;
                }

                path.push(adj);
            }
        }
    }

    public int[][] getPath() {
        int[][] path = new int[dist[neighborhoods.length - 1][neighborhoods.length - 1]][2];
        int idx = dist[neighborhoods.length - 1][neighborhoods.length - 1] - 1;
        int i = neighborhoods.length - 1;
        int j = neighborhoods.length - 1;

        while (idx != 0) {
            path[idx][0] = parent[i][j].x;
            path[idx][1] = parent[i][j].y;
            i = parent[i][j].x;
            j = parent[i][j].y;

            idx--;
        }

        return path;
    }

    private class Position {
        int x;
        int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        boolean isValid() {
            return x >= 0 && y >= 0 && x < neighborhoods.length && y < neighborhoods.length;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x &&
                    y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Position{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

}
