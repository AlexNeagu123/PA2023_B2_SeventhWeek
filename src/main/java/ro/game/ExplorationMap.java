package ro.game;

import lombok.extern.log4j.Log4j2;
import ro.players.Robot;
import ro.shared.Token;

import java.util.List;

@Log4j2
public class ExplorationMap {
    private final Cell[][] matrix;

    public ExplorationMap(int matrixLength) {
        matrix = new Cell[matrixLength][matrixLength];
        for (int i = 0; i < matrixLength; ++i) {
            for (int j = 0; j < matrixLength; ++j) {
                matrix[i][j] = new Cell();
            }
        }
    }

    public Cell getCell(int row, int column) {
        if (row >= 0 && column >= 0 && row < matrix.length && column < matrix.length) {
            return matrix[row][column];
        }
        return null;
    }

    public void visit(Coordinates coordinates, Robot robot) {
        int row = coordinates.getRow(), col = coordinates.getColumn();
        synchronized (matrix[row][col]) {
            log.info(String.format("%s is visiting the cell (%d, %d)", robot, row, col));
            List<Token> extractedTokens = robot.extractTokensFromSharedData();
            if (extractedTokens.isEmpty()) {
                log.info(String.format("No tokens left, so %s stops execution", robot));
                robot.setRunning(false);
                return;
            }
            log.info(String.format("%s extracted the following tokens from the shared memory: %s", robot, extractedTokens));
            for (Token token : extractedTokens) {
                matrix[row][col].addToken(token);
            }
            log.info(String.format("%s finished to put the tokens in cell (%d, %d)", robot, row, col));
        }
    }
}
