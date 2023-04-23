package ro.commands;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ro.game.explorations.Exploration;

@AllArgsConstructor
@Log4j2
public class StartCommand implements Command {
    Exploration exploration;

    @Override
    public void execute() {
        System.out.println("The exploration process have been started...");
        new Thread(exploration).start();
    }
}
