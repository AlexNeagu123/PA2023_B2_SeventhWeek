package ro.game.maps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
public class Coordinates {
    private int row;
    private int column;
}
