package ro.app;

import ro.exceptions.InvalidTimeLimitException;

import java.util.concurrent.TimeUnit;

public class TimeKeeper extends Thread {
    private final int seconds;

    public TimeKeeper(int seconds) throws InvalidTimeLimitException {
        if (seconds < 10) {
            throw new InvalidTimeLimitException("The minimum time limit for the exploration should be 10 seconds.");
        }
        this.seconds = seconds;
    }

    @Override
    public void run() {
        for (int i = 0; i < seconds; ++i) {
            if (i > 0 && (seconds - i <= 10 || i % 10 == 0)) {
                System.out.println(i + " seconds passed. " + (seconds - i) + " seconds remaining");
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Time limit has been exceeded. The exploration is stopped.");
    }
}
