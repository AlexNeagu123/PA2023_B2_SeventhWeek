package ro.players;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ro.game.explorations.Exploration;
import ro.exceptions.NoMoreFreeCoordinates;
import ro.shared.Token;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

@Getter
@ToString(onlyExplicitlyIncluded = true)
public class Robot implements Runnable {
    protected final Exploration exploration;
    @ToString.Include
    protected final String name;
    protected Integer nodeId;
    @Setter
    protected boolean running;
    @Setter
    protected boolean paused;
    protected int tokensPlaced;
    protected final Deque<Integer> dfsStack;

    public Robot(String name, Exploration exploration) throws NoMoreFreeCoordinates {
        this.name = name;
        this.tokensPlaced = 0;
        this.exploration = exploration;
        this.nodeId = exploration.generateValidNode();
        this.dfsStack = new ArrayDeque<>();
        this.running = true;
        this.paused = false;
    }

    public List<Token> extractTokensFromSharedData() {
        List<Token> extractedTokens = exploration.getSharedMemory().extractTokens(exploration.getMapLimit());
        tokensPlaced += extractedTokens.size();
        return extractedTokens;
    }

    protected void updateDfsStack(List<Integer> neighbours) {
        for (Integer neighbourId : neighbours) {
            if (exploration.setVisitedIfNecessary(neighbourId)) {
                dfsStack.add(neighbourId);
            }
        }
    }

    public int getNodeId() {
        return nodeId;
    }

    @Override
    public void run() {
        exploration.setVisitedIfNecessary(nodeId);
        dfsStack.clear();
        dfsStack.add(nodeId);
        while (running && !dfsStack.isEmpty()) {
            if (paused) {
                continue;
            }
            try {
                nodeId = dfsStack.pollLast();
                exploration.visit(nodeId, this);
                updateDfsStack(exploration.getNeighbours(nodeId));
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (this.running) {
            this.running = false;
        }
    }
}
