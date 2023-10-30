package Minesweeper;

import java.awt.*;

public class Difficulty {
    protected final String name;
    protected final Dimension dimension;
    protected final int bombNumber;

    protected Difficulty(String name, Dimension tileLayout, int bombNumber){
        this.name = name;
        this.dimension = tileLayout;
        this.bombNumber = bombNumber;
    }
}
