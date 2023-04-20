package ro.utils;

import ro.game.Exploration;
import ro.game.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class Movement {
    private static final int[] dx = new int[]{0, 0, -1, 1};
    private static final int[] dy = new int[]{1, -1, 0, 0};

    private static boolean isValidNeighbour(Coordinates coordinates) {
        return (coordinates.getRow() >= 0 && coordinates.getColumn() >= 0 &&
                coordinates.getRow() < Exploration.MATRIX_LENGTH && coordinates.getColumn() < Exploration.MATRIX_LENGTH);
    }

    public static List<Coordinates> getValidNeighbours(Coordinates coordinates) {
        List<Coordinates> validNeighbours = new ArrayList<>();
        for (int direction = 0; direction < 4; direction++) {
            Coordinates neighbour = new Coordinates(coordinates.getRow() + dx[direction], coordinates.getColumn() + dy[direction]);
            if (isValidNeighbour(neighbour)) {
                validNeighbours.add(neighbour);
            }
        }
        return validNeighbours;
    }
}
