package Minesweeper;

import java.awt.*;

import static Minesweeper.Variables.*;

public class MinesweeperManager {
    private boolean started;
    private final Gui gui;
    private Field mineField;
    private int remainingTiles;

    public MinesweeperManager(){
        currentDifficulty = STANDARDDIFFICULTY;
        this.started = false;
        this.gui = new Gui(this);
    }

    private void start(int x, int y){
        Debugger.info("starting game");
        mineField = new Field(this, currentDifficulty.dimension, currentDifficulty.mineNumber);
        mineField.startField(x, y);
        started = true;
        remainingTiles = currentDifficulty.dimension.width * currentDifficulty.dimension.height;
        clickTile(x, y);
    }
    protected void reset(){
        Debugger.info("resetting game");
        mineField = null;
        started = false;
    }

    public void clickTile(int x, int y){
        if(!started){
            start(x, y);
            return;
        }
        Tile tile = mineField.getTile(x, y);
        if(tile == null){
            Debugger.info("clicked tile is null");
            return;
        }

        if(tile.getIsFlagged()){
            Debugger.info("cant click flagged tiles");
            return;
        }

        if(tile.getDiscovered()) {
            Debugger.info("tile already discovered");
            return;
        }else if(tile.getIsMine()){
            Debugger.info("Game Over");
            gui.initGUI(currentDifficulty);
            reset();
            return;
        }

        tile.discover();
        remainingTiles--;
        if(remainingTiles == currentDifficulty.mineNumber) Debugger.info("VICTORY");
        int bombCount = getNeighbourBombCount(x, y);
        gui.discoverTile(x, y, bombCount);
        if(bombCount == 0){
            clickTile(x, y + 1);
            clickTile(x + 1, y + 1);
            clickTile(x + 1, y);
            clickTile(x + 1, y - 1);
            clickTile(x, y - 1);
            clickTile(x - 1, y - 1);
            clickTile(x - 1, y);
            clickTile(x - 1, y + 1);
        }
    }

    public void rightClickTile(int x, int y){
        if(!started) return;
        Tile tile = mineField.getTile(x, y);
        if(tile.getDiscovered()) return;

        if(tile.getIsFlagged()){
            tile.setFlag(false);
            gui.setFlag(x, y, false);
        }
        else{
            tile.setFlag(true);
            gui.setFlag(x, y, true);
        }
    }

    private Tile[] getNeighbours(int x, int y){
        Tile[] n = new Tile[8];
        n[0] = mineField.getTile(x, y + 1);
        n[1] = mineField.getTile(x + 1, y + 1);
        n[2] = mineField.getTile(x + 1, y);
        n[3] = mineField.getTile(x + 1, y - 1);
        n[4] = mineField.getTile(x, y - 1);
        n[5] = mineField.getTile(x - 1, y - 1);
        n[6] = mineField.getTile(x - 1, y);
        n[7] = mineField.getTile(x - 1, y + 1);
        return n;
    }
    private int getNeighbourBombCount(int x, int y){
        int counter = 0;
        for(Tile tile : getNeighbours(x, y)){
            if(tile != null && tile.getIsMine()) counter++;
        }
        return counter;
    }

    protected int coordToId(int x, int y){
        return x + (currentDifficulty.dimension.width * y);
    }
    protected Dimension idToCoord(int id){
        return new Dimension(id % currentDifficulty.dimension.width, id / currentDifficulty.dimension.width);
    }
}