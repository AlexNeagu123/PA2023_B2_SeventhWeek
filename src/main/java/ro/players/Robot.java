package ro.players;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ro.game.Exploration;
import ro.exceptions.NoMoreFreeCoordinates;
import ro.game.Coordinates;
import ro.shared.Token;
import ro.utils.Movement;

import java.util.Collections;
import java.util.List;

@Getter
@ToString(onlyExplicitlyIncluded = true)
public class Robot implements Runnable {
    @ToString.Include
    private final String name;
    private final Exploration exploration;
    private Coordinates coordinates;
    @Setter
    private boolean running;
    @Setter
    private boolean paused;

    public Robot(String name, Exploration exploration) throws NoMoreFreeCoordinates {
        this.name = name;
        this.exploration = exploration;
        this.coordinates = exploration.generateValidCoordinates();
        this.running = true;
        this.paused = false;
    }

    public Robot(String name, Exploration exploration, Coordinates coordinates) {
        this.name = name;
        this.coordinates = coordinates;
        this.exploration = exploration;
        this.running = true;
        this.paused = false;
    }

    public int getRow() {
        return coordinates.getRow();
    }

    public int getColumn() {
        return coordinates.getColumn();
    }

    private Coordinates getRandomNeighbour(Coordinates coordinates) {
        List<Coordinates> validNeighbours = Movement.getValidNeighbours(coordinates);
        Collections.shuffle(validNeighbours);
        return validNeighbours.get(0);
    }

    public List<Token> extractTokensFromSharedData() {
        return exploration.getSharedMemory().extractTokens(Exploration.MATRIX_LENGTH);
    }

    public void run() {
        while (running) {
            if (paused) {
                continue;
            }
            try {
                exploration.getExplorationMap().visit(coordinates, this);
                coordinates = getRandomNeighbour(coordinates);
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
