package ro.game.explorations;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import ro.TimeKeeper;
import ro.game.maps.Coordinates;
import ro.game.maps.MatrixMap;
import ro.shared.SharedMemory;
import ro.utils.NodeUtils;

@Log4j2
@Getter
public class MatrixExploration extends Exploration {
    private final int length;

    public MatrixExploration(TimeKeeper timeKeeper, int length) {
        super(timeKeeper);
        this.length = length;
        this.visited = new boolean[length * length];
        this.sharedMemory = new SharedMemory((int) Math.pow(length, 3));
        this.explorationMap = new MatrixMap(length);
        initializeFreeNodes();
    }

    private void initializeFreeNodes() {
        for (int i = 0; i < length; ++i) {
            for (int j = 0; j < length; ++j) {
                freeStartNodes.add(NodeUtils.mapCoordinatesToNode(new Coordinates(i, j), length));
            }
        }
    }

    @Override
    public int getMapLimit() {
        return length;
    }
}

