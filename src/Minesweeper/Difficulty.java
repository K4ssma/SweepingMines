package Minesweeper;

import java.awt.*;

public class Difficulty {
    protected final Dimension dimension;
    protected final int bombNumber;

    protected Difficulty(Dimension tileLayout, int bombNumber){
        this.dimension = tileLayout;
        this.bombNumber = bombNumber;
    }
}
