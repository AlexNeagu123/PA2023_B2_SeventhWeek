package ro.game.explorations;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import ro.TimeKeeper;
import ro.exceptions.NoMoreFreeCoordinates;
import ro.game.maps.ExplorationMap;
import ro.players.Robot;
import ro.shared.SharedMemory;
import ro.shared.Token;

import java.util.*;

@Getter
@Log4j2
public abstract class Exploration implements Runnable {
    protected final List<Robot> robots;
    protected final Set<Integer> freeStartNodes;
    protected boolean[] visited;
    protected final TimeKeeper timeKeeper;
    protected SharedMemory sharedMemory;
    protected ExplorationMap explorationMap;
    @Setter
    protected int state;

    public Exploration(TimeKeeper timeKeeper) {
        this.robots = new ArrayList<>();
        this.freeStartNodes = new HashSet<>();
        this.timeKeeper = timeKeeper;
        this.timeKeeper.setDaemon(true);
        this.state = 0;
    }

    public abstract int getMapLimit();

    public List<Integer> getNeighbours(int nodeId) {
        return explorationMap.getNeighbours(nodeId);
    }


    public boolean hasUniqueName(String robotName) {
        for (Robot robot : robots) {
            if (robot.getName().equals(robotName)) {
                return false;
            }
        }
        return true;
    }

    public void addRobot(Robot robot) {
        robots.add(robot);
    }

    public void visit(Integer nodeId, Robot robot) {
        explorationMap.visit(nodeId, robot);
    }

    public synchronized boolean setVisitedIfNecessary(Integer nodeId) {
        if (visited[nodeId]) {
            return false;
        }
        visited[nodeId] = true;
        return true;
    }

    public Integer generateValidNode() throws NoMoreFreeCoordinates {
        if (freeStartNodes.isEmpty()) {
            throw new NoMoreFreeCoordinates();
        }
        Integer nodeId = freeStartNodes.stream().skip(new Random().nextInt(freeStartNodes.size())).findFirst().orElse(null);
        assert nodeId != null;
        freeStartNodes.remove(nodeId);
        return nodeId;
    }

    protected boolean timeLimitIsExceeded() {
        if (!timeKeeper.isAlive()) {
            for (Robot robot : robots) {
                robot.setRunning(false);
            }
            return true;
        }
        return false;
    }

    protected boolean allRobotsAreFinished() {
        boolean noRobotsLeft = true;
        for (Robot robot : robots) {
            if (robot.isRunning()) {
                noRobotsLeft = false;
                break;
            }
        }
        return noRobotsLeft;
    }

    @Override
    public void run() {
        timeKeeper.start();
        List<Thread> threads = new ArrayList<>();
        this.state = 1;

        for (Robot robot : robots) {
            threads.add(new Thread(robot));
        }
        for (Thread thread : threads) {
            thread.start();
        }

        while (!timeLimitIsExceeded() && !allRobotsAreFinished()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }

        System.out.println("The final state of the board looks like this:");
        explorationMap.printFinalState();
        System.out.println("The number of tokens placed in the board by each robot:");
        for (Robot robot : robots) {
            System.out.printf("Robot %s have placed %d tokens in the board\n", robot.getName(), robot.getTokensPlaced());
        }
        this.state = 2;
    }

    public static void printTokenList(List<Token> tokenList) {
        System.out.print("[");
        for (int k = 0; k < tokenList.size(); ++k) {
            if (k + 1 < tokenList.size()) {
                System.out.print(tokenList.get(k).getNumber() + ", ");
            } else {
                System.out.print(tokenList.get(k).getNumber());
            }
        }
        System.out.println("]");
    }
}
