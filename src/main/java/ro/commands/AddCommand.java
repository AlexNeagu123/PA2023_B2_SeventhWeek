package ro.commands;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ro.game.Exploration;
import ro.players.Robot;

@AllArgsConstructor
@Log4j2
public class AddCommand implements Command {
    Robot robot;

    @Override
    public void execute() {
        Exploration exploration = robot.getExploration();
        exploration.addRobot(robot);
        System.out.printf("Robot %s was created and placed on coordinates (%d %d)\n", robot.getName(), robot.getRow(), robot.getColumn());
    }
}
