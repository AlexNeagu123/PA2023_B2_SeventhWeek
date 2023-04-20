package ro.commands;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ro.game.Exploration;
import ro.players.Robot;

@AllArgsConstructor
@Log4j2
public class OnCommand implements Command {
    String robotName;
    Exploration exploration;

    @Override
    public void execute() {
        for (Robot robot : exploration.getRobots()) {
            if (robot.getName().equals(robotName)) {
                log.info(String.format("The execution of %s is resumed.", robot));
                robot.setPaused(false);
                break;
            }
        }
    }
}
