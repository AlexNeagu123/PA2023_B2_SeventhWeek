package ro.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ro.commands.*;
import ro.exceptions.*;
import ro.game.Exploration;
import ro.players.Robot;

@AllArgsConstructor
@RequiredArgsConstructor
public class CommandParser {
    private final Exploration exploration;
    // STATES:
    // 0 - addingPhase
    // 1 - on/off Phase
    // 2 - finished
    @Getter
    private int state = 0;

    private String checkAddCommandAndExtractName(String[] splitCommand) throws AddCommandException {
        if (splitCommand.length != 2) {
            throw new AddCommandException("The 'add' command should have exactly one argument: The name of the robot");
        }
        if (state != 0) {
            throw new AddCommandException("You can't execute the 'add' command right now!");
        }
        return splitCommand[1];
    }

    private void checkStartCommand(String[] splitCommand) throws StartCommandException {
        if (splitCommand.length != 1) {
            throw new StartCommandException("The 'start' command should not contain any additional arguments");
        }
        if (state != 0) {
            throw new StartCommandException("You can't execute the 'start' command right now!");
        }
    }

    private String checkOffCommand(String[] splitCommand, Exploration exploration) throws OffCommandException {
        if (splitCommand.length != 2) {
            throw new OffCommandException("The 'off' command should contain only one argument - the robot name");
        }
        if (state != 1) {
            throw new OffCommandException("You can't execute the 'off' command right now!");
        }
        if (!exploration.hasRobotWithName(splitCommand[1])) {
            throw new OffCommandException(String.format("A robot with the name '%s' doesn't exist!", splitCommand[1]));
        }
        return splitCommand[1];
    }

    private String checkOnCommand(String[] splitCommand, Exploration exploration) throws OnCommandException {
        if (splitCommand.length != 2) {
            throw new OnCommandException("The 'on' command should contain only one argument - the robot name");
        }
        if (state != 1) {
            throw new OnCommandException("You can't execute the 'on' command right now!");
        }
        if (!exploration.hasRobotWithName(splitCommand[1])) {
            throw new OnCommandException(String.format("A robot with the name '%s' doesn't exist!", splitCommand[1]));
        }
        return splitCommand[1];
    }

    private void checkOffAllCommand(String[] splitCommand) throws OffAllCommandException {
        if (splitCommand.length != 1) {
            throw new OffAllCommandException("The 'off-all' command should not contain any additional arguments");
        }
        if (state != 1) {
            throw new OffAllCommandException("You can't execute the 'off-all' command right now!");
        }
    }

    private void checkOnAllCommand(String[] splitCommand) throws OnAllCommandException {
        if (splitCommand.length != 1) {
            throw new OnAllCommandException("The 'on-all' command should not contain any additional arguments");
        }
        if (state != 1) {
            throw new OnAllCommandException("You can't execute the 'on-all' command right now!");
        }
    }
    public Command parseCommand(String commandText) throws NonExistentCommand, NoMoreFreeCoordinates,
            AddCommandException, StartCommandException, OffCommandException, OnCommandException, OffAllCommandException, OnAllCommandException {
        String[] splitCommand = commandText.split(" ", 2);
        if (splitCommand[0].equals("add")) {
            String robotName = checkAddCommandAndExtractName(splitCommand);
            return new AddCommand(new Robot(robotName, exploration));
        }
        if (splitCommand[0].equals("start")) {
            checkStartCommand(splitCommand);
            this.state = 1;
            return new StartCommand(exploration);
        }
        if (splitCommand[0].equals("off")) {
            String robotName = checkOffCommand(splitCommand, exploration);
            return new OffCommand(robotName, exploration);
        }
        if (splitCommand[0].equals("on")) {
            String robotName = checkOnCommand(splitCommand, exploration);
            return new OnCommand(robotName, exploration);
        }
        if (splitCommand[0].equals("off-all")) {
            checkOffAllCommand(splitCommand);
            return new OffAllCommand(exploration);
        }
        if (splitCommand[0].equals("on-all")) {
            checkOnAllCommand(splitCommand);
            return new OnAllCommand(exploration);
        }
        throw new NonExistentCommand(splitCommand[0]);
    }
}