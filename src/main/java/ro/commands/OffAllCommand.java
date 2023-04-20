package ro.commands;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ro.game.Exploration;
import ro.players.Robot;

@AllArgsConstructor
@Log4j2
public class OffAllCommand implements Command {
    private Exploration exploration;

    @Override
    public void execute() {
        log.info("The executions of all the robots are paused.");
        for (Robot robot : exploration.getRobots()) {
            robot.setPaused(true);
        }
    }
}
