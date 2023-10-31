package Minesweeper;

import java.awt.*;

public class MinesweeperManager {

    private final Gui gui;
    private Field mineField;

    public MinesweeperManager(){
        this.gui = new Gui(this);
    }

    public void start(Dimension dimension, int mineNumber, Dimension startTileCoord){
        Debugger.info("starting game");
        mineField = new Field(this, dimension, mineNumber);
        mineField.startField(startTileCoord.width, startTileCoord.height);
    }

    public void clickTile(int x, int y){
        
    }
}