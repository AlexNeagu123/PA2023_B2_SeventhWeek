package ro.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ro.commands.*;
import ro.exceptions.*;
import ro.game.explorations.Exploration;
import ro.players.Robot;

import java.util.Arrays;

@RequiredArgsConstructor
public class CommandParser {
    @Getter
    private final Exploration exploration;

    private String checkAddCommandAndExtractName(String[] arguments) throws InvalidCommandArgumentsException, CommandNotAvailableException {
        if (arguments.length != 1) {
            throw new InvalidCommandArgumentsException("add", 1, arguments.length);
        }
        if (exploration.getState() != 0) {
            throw new CommandNotAvailableException("add");
        }

        if (!exploration.hasUniqueName(arguments[0])) {
            throw new InvalidCommandArgumentsException("add", String.format("A robot with the name '%s' already exists!", arguments[0]));
        }
        return arguments[0];
    }

    private void checkStartCommand(String[] arguments) throws InvalidCommandArgumentsException, CommandNotAvailableException {
        if (arguments.length != 0) {
            throw new InvalidCommandArgumentsException("start", 0, arguments.length);
        }
        if (exploration.getState() != 0) {
            throw new CommandNotAvailableException("start");
        }
    }

    private String checkOffCommandAndExtractName(String[] arguments, Exploration exploration) throws InvalidCommandArgumentsException, CommandNotAvailableException {
        if (arguments.length != 1) {
            throw new InvalidCommandArgumentsException("off", 1, arguments.length);
        }
        if (exploration.getState() != 1) {
            throw new CommandNotAvailableException("off");
        }
        if (exploration.hasUniqueName(arguments[0])) {
            throw new InvalidCommandArgumentsException("off", String.format("A robot with the name '%s' doesn't exist!", arguments[0]));
        }
        return arguments[0];
    }

    private String checkOnCommandAndExtractName(String[] arguments, Exploration exploration) throws InvalidCommandArgumentsException, CommandNotAvailableException {
        if (arguments.length != 1) {
            throw new InvalidCommandArgumentsException("on", 1, arguments.length);
        }
        if (exploration.getState() != 1) {
            throw new CommandNotAvailableException("on");
        }
        if (exploration.hasUniqueName(arguments[0])) {
            throw new InvalidCommandArgumentsException("on", String.format("A robot with the name '%s' doesn't exist!", arguments[0]));
        }
        return arguments[0];
    }

    private void checkOffAllCommand(String[] arguments) throws InvalidCommandArgumentsException, CommandNotAvailableException {
        if (arguments.length != 0) {
            throw new InvalidCommandArgumentsException("off-all", 0, arguments.length);
        }
        if (exploration.getState() != 1) {
            throw new CommandNotAvailableException("off-all");
        }
    }

    private void checkOnAllCommand(String[] arguments) throws InvalidCommandArgumentsException, CommandNotAvailableException {
        if (arguments.length != 0) {
            throw new InvalidCommandArgumentsException("on-all", 0, arguments.length);
        }
        if (exploration.getState() != 1) {
            throw new CommandNotAvailableException("on-all");
        }
    }

    private void checkQuitCommand(String[] arguments) throws InvalidCommandArgumentsException, CommandNotAvailableException {
        if (arguments.length != 0) {
            throw new InvalidCommandArgumentsException("quit", 0, arguments.length);
        }
    }

    public Command matchAndBuildCommand(String commandName, String[] arguments) throws CommandNotAvailableException, InvalidCommandArgumentsException, NoMoreFreeCoordinates, NonExistentCommand {
        if (commandName.equals("add")) {
            String robotName = checkAddCommandAndExtractName(arguments);
            return new AddCommand(new Robot(robotName, exploration));
        }

        if (commandName.equals("quit")) {
            checkQuitCommand(arguments);
            return new QuitCommand();
        }

        if (commandName.equals("start")) {
            checkStartCommand(arguments);
            return new StartCommand(exploration);
        }

        if (commandName.equals("off")) {
            String robotName = checkOffCommandAndExtractName(arguments, exploration);
            return new OffCommand(robotName, exploration);
        }

        if (commandName.equals("on")) {
            String robotName = checkOnCommandAndExtractName(arguments, exploration);
            return new OnCommand(robotName, exploration);
        }

        if (commandName.equals("off-all")) {
            checkOffAllCommand(arguments);
            return new OffAllCommand(exploration);
        }

        if (commandName.equals("on-all")) {
            checkOnAllCommand(arguments);
            return new OnAllCommand(exploration);
        }
        throw new NonExistentCommand(commandName);
    }

    public Command parseCommand(String commandText) throws CommandNotAvailableException, InvalidCommandArgumentsException, NoMoreFreeCoordinates, NonExistentCommand {
        String[] splitCommand = commandText.split("\\s+");
        return matchAndBuildCommand(splitCommand[0], Arrays.copyOfRange(splitCommand, 1, splitCommand.length));
    }
}