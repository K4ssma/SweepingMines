package Minesweeper;

import java.awt.*;

public class Variables {
    protected static final Difficulty BEGINNER = new Difficulty("Beginner", new Dimension(8, 8), 10);
    protected static final Difficulty INTERMEDIATE = new Difficulty("Intermediate", new Dimension(16, 16), 40);
    protected static final Difficulty EXPERT = new Difficulty("Expert", new Dimension(30, 16), 99);
    protected static final Difficulty STANDARDDIFFICULTY = BEGINNER;
    protected static Difficulty currentDifficulty;
}
