package ro.utils;

import ro.game.maps.Coordinates;

public class NodeUtils {
    public static Integer mapCoordinatesToNode(Coordinates coordinates, int length) {
        return coordinates.getRow() * length + coordinates.getColumn();
    }

    public static Coordinates mapNodeToCoordinates(Integer nodeId, int length) {
        return new Coordinates(nodeId / length, nodeId % length);
    }
}
