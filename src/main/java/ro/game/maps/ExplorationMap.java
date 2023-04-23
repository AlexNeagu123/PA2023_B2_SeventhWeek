package ro.game.maps;

import ro.players.Robot;

import java.util.List;

public interface ExplorationMap {
    void visit(Integer nodeId, Robot robot);

    void printFinalState();

    List<Integer> getNeighbours(int nodeId);
}
