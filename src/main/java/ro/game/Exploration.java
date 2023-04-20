package ro.game;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import ro.exceptions.NoMoreFreeCoordinates;
import ro.players.Robot;
import ro.shared.SharedMemory;
import ro.shared.Token;

import java.util.*;

@Getter
@Log4j2
public class Exploration implements Runnable {
    private final SharedMemory sharedMemory;
    private final ExplorationMap explorationMap;
    private final List<Robot> robots;
    private final Set<Coordinates> freeCoordinates;
    public static final int MATRIX_LENGTH = 3;

    public Exploration() {
        sharedMemory = new SharedMemory((int) Math.pow(MATRIX_LENGTH, 3));
        explorationMap = new ExplorationMap(MATRIX_LENGTH);
        robots = new ArrayList<>();
        freeCoordinates = new HashSet<>();
        initializeFreeCoordinates();
    }

    private void initializeFreeCoordinates() {
        for (int i = 0; i < MATRIX_LENGTH; ++i) {
            for (int j = 0; j < MATRIX_LENGTH; ++j) {
                freeCoordinates.add(new Coordinates(i, j));
            }
        }
    }

    public boolean hasRobotWithName(String robotName) {
        for (Robot robot : robots) {
            if (robot.getName().equals(robotName)) {
                return true;
            }
        }
        return false;
    }

    public void addRobot(Robot robot) {
        robots.add(robot);
    }

    public Coordinates generateValidCoordinates() throws NoMoreFreeCoordinates {
        if (freeCoordinates.isEmpty()) {
            throw new NoMoreFreeCoordinates();
        }
        Coordinates coordinates = freeCoordinates.stream().skip(new Random().nextInt(freeCoordinates.size())).findFirst().orElse(null);
        assert coordinates != null;
        freeCoordinates.remove(coordinates);
        return coordinates;
    }

    public void run() {
        List<Thread> threads = new ArrayList<>();
        for (Robot robot : robots) {
            threads.add(new Thread(robot));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException interruptedException) {
                log.error(interruptedException.getMessage());
            }
        }
        System.out.println("The final state of the board looks like this:");
        for (int i = 0; i < MATRIX_LENGTH; ++i) {
            for (int j = 0; j < MATRIX_LENGTH; ++j) {
                System.out.printf("Cell (%d, %d) contains the following tokens: [ ", i, j);
                for (Token token : explorationMap.getCell(i, j).getTokenList()) {
                    System.out.print(token.getNumber() + " ");
                }
                System.out.println("]");
            }
        }
    }
}
