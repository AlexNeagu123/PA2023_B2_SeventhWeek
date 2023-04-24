package ro.utils;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ro.app.TimeKeeper;
import ro.exceptions.InvalidEdgeCountException;
import ro.exceptions.InvalidTimeLimitException;
import ro.game.explorations.Exploration;
import ro.game.explorations.GraphExploration;
import ro.game.explorations.MatrixExploration;

import java.util.Scanner;

@AllArgsConstructor
@Log4j2
public class CommandScanner {
    private Scanner scanner;

    public String readCommand() {
        String commandText = "";
        while (commandText.length() == 0) {
            commandText = scanner.nextLine();
        }
        return commandText;
    }

    public TimeKeeper collectTimeKeeper() {
        System.out.println("Please enter the maximum number of seconds you want to wait for the exploration");
        while (true) {
            try {
                String strNumber = readCommand();
                int seconds = Integer.parseInt(strNumber);
                return new TimeKeeper(seconds);
            } catch (NumberFormatException nfe) {
                System.out.println("Please, enter an integer number of seconds!");
            } catch (InvalidTimeLimitException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean extractExplorationType() {
        boolean isMatrix = false;
        while (true) {
            String explorationType = readCommand();
            if (explorationType.equals("matrix")) {
                isMatrix = true;
                break;
            }
            if (explorationType.equals("graph")) {
                break;
            }
            System.out.println("Please enter a valid word: 'graph' or 'matrix'");
        }
        return isMatrix;
    }

    public int extractNodeCount() {
        int nodeCount;
        while (true) {
            try {
                String strNumber = readCommand();
                nodeCount = Integer.parseInt(strNumber);
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("Please, enter an integer number!");
            }
        }
        return nodeCount;
    }

    public int extractEdgeCount(int nodeCount) {
        int edgeCount;
        while (true) {
            try {
                String strNumber = readCommand();
                edgeCount = Integer.parseInt(strNumber);
                if (edgeCount < 0 && edgeCount > nodeCount * (nodeCount - 1) / 2) {
                    throw new InvalidEdgeCountException(nodeCount);
                }
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("Please, enter an integer number!");
            } catch (InvalidEdgeCountException e) {
                System.out.println(e.getMessage());
            }
        }
        return edgeCount;
    }


    public Exploration collectExploration(TimeKeeper timeKeeper) {
        System.out.println("Please choose the exploration type you want: 'matrix' or 'graph'");
        boolean isMatrix = extractExplorationType();

        if (isMatrix) {
            System.out.println("Please enter the length of the matrix");
        } else {
            System.out.println("Please enter the number of nodes in the graph");
        }

        int nodeCount = extractNodeCount();
        if (isMatrix) {
            return new MatrixExploration(timeKeeper, nodeCount);
        }

        System.out.println("Please enter the number of edges in the graph");
        int edgeCount = extractEdgeCount(nodeCount);
        return new GraphExploration(timeKeeper, nodeCount, edgeCount);
    }


}
