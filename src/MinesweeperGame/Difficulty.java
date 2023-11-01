package MinesweeperGame;

import java.awt.*;

public class Difficulty {
    protected final String name;
    protected final Dimension dimension;
    protected final int mineNumber;

    protected Difficulty(String name, Dimension tileLayout, int mineNumber){
        this.name = name;
        this.dimension = tileLayout;
        this.mineNumber = mineNumber;
    }
}
