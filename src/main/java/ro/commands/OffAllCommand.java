package ro.commands;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ro.game.explorations.Exploration;
import ro.players.Robot;

@AllArgsConstructor
@Log4j2
public class OffAllCommand implements Command {
    private Exploration exploration;

    @Override
    public void execute() {
        System.out.println("The executions of all the robots are paused.");
        for (Robot robot : exploration.getRobots()) {
            robot.setPaused(true);
        }
    }
}
