package ro.utils;

import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class CommandScanner {
    private Scanner scanner;

    public String readCommand() {
        String commandText = "";
        while (commandText.length() == 0) {
            commandText = scanner.nextLine();
        }
        return commandText;
    }
}
