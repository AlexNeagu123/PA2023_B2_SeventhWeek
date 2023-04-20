package ro.commands;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ro.game.Exploration;
import ro.players.Robot;

@AllArgsConstructor
@Log4j2
public class OnAllCommand implements Command {
    private Exploration exploration;

    @Override
    public void execute() {
        log.info("The executions of all the robots are resumed.");
        for (Robot robot : exploration.getRobots()) {
            robot.setPaused(false);
        }
    }
}
