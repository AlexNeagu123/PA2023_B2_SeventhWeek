package ro.utils;

import ro.game.maps.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class Movement {
    private static final int[] dx = new int[]{0, 0, -1, 1};
    private static final int[] dy = new int[]{1, -1, 0, 0};

    private static boolean isValidNeighbour(Coordinates coordinates, int matrixLength) {
        return (coordinates.getRow() >= 0 && coordinates.getColumn() >= 0 &&
                coordinates.getRow() < matrixLength && coordinates.getColumn() < matrixLength);
    }

    public static List<Integer> getValidNeighbours(Coordinates coordinates, int matrixLength) {
        List<Integer> validNeighbours = new ArrayList<>();
        for (int direction = 0; direction < 4; direction++) {
            Coordinates neighbour = new Coordinates(coordinates.getRow() + dx[direction], coordinates.getColumn() + dy[direction]);
            if (isValidNeighbour(neighbour, matrixLength)) {
                validNeighbours.add(NodeUtils.mapCoordinatesToNode(neighbour, matrixLength));
            }
        }
        return validNeighbours;
    }
}
