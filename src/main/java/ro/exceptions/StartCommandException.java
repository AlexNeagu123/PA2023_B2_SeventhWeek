package ro.exceptions;

import ro.commands.StartCommand;

public class StartCommandException extends Exception {
    public StartCommandException(String message) {
        super(message);
    }
}
