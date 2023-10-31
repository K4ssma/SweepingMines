package Minesweeper;

import java.awt.*;

import static Minesweeper.Variables.*;

public class MinesweeperManager {
    private boolean started;
    private final Gui gui;
    private Field mineField;

    public MinesweeperManager(){
        currentDifficulty = STANDARDDIFFICULTY;
        this.started = false;
        this.gui = new Gui(this);
    }

    protected void start(int x, int y){
        Debugger.info("starting game");
        mineField = new Field(this, currentDifficulty.dimension, currentDifficulty.mineNumber);
        mineField.startField(x, y);
        started = true;
    }
    protected void reset(){
        Debugger.info("resetting game");
        mineField = null;
        started = false;
    }

    public void clickTile(int x, int y){
        Debugger.info("tile: (" + x + "/" + y + ") got left-clicked");

    }
    protected int coordToId(int x, int y){
        return x + (currentDifficulty.dimension.width * y);
    }
    protected Dimension idToCoord(int id){
        return new Dimension(id % currentDifficulty.dimension.width, id / currentDifficulty.dimension.width);
    }
}