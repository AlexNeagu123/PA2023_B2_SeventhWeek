package ro.game.maps;

import lombok.Getter;
import ro.shared.Token;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Cell {
    public List<Token> tokenList;

    public Cell() {
        tokenList = new ArrayList<>();
    }

    public void addToken(Token token) {
        tokenList.add(token);

    }
}