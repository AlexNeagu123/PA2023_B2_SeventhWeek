package ro;

import ro.commands.Command;
import ro.exceptions.*;
import ro.game.Exploration;
import ro.utils.CommandParser;
import ro.utils.CommandScanner;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Exploration exploration = new Exploration();
        CommandScanner commandScanner = new CommandScanner(new Scanner(System.in));
        CommandParser commandParser = new CommandParser(exploration);
        while (commandParser.getState() != 2) {
            try {
                String commandText = commandScanner.readCommand();
                Command command = commandParser.parseCommand(commandText);
                command.execute();
            } catch (NoMoreFreeCoordinates | AddCommandException | StartCommandException | NonExistentCommand |
                     OffCommandException | OnCommandException | OnAllCommandException | OffAllCommandException commandException) {
                System.out.println(commandException.getMessage());
            }
        }
    }
}
