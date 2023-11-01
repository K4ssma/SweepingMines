package Minesweeper;

import java.awt.*;

import static Minesweeper.Variables.*;

public class MinesweeperManager {
    private boolean started;
    private final Gui gui;
    private Field mineField;
    private int remainingTiles;
    private boolean won;

    public MinesweeperManager(){
        currentDifficulty = STANDARDDIFFICULTY;
        this.started = false;
        this.won = false;
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
        won = false;
    }

    public int[] getGridInfo(){
        return new int[]{currentDifficulty.dimension.width, currentDifficulty.dimension.height, currentDifficulty.mineNumber};
    }
    public boolean getIsWon(){
        return won;
    }
    public int[] getGridStatus(){
        if(!started) return null;

        int[] status = new int[currentDifficulty.dimension.width * currentDifficulty.dimension.height];
        for(int i = 0; i < status.length; i++){
            Dimension tileCoord = idToCoord(i);
            Tile tile = mineField.getTile(tileCoord.width, tileCoord.height);
            if(tile.getIsFlagged()){
                status[i] = -2;
            }else if(!tile.getDiscovered()){
                status[i] = -1;
            }else{
                status[i] = getNeighbourBombCount(tileCoord.width, tileCoord.height);
            }
        }
        return status;
    }

    public void clickTile(int x, int y){
        if(!started){
            start(x, y);
            return;
        }else if(won) return;

        Tile tile = mineField.getTile(x, y);
        if(tile == null){
            Debugger.info("clicked tile is null");
            return;
        }else if(tile.getIsFlagged()) return;

        if(tile.getDiscovered()) {
            return;
        }else if(tile.getIsMine()){
            Debugger.info("Game Over");
            gui.initGUI(currentDifficulty);
            reset();
            return;
        }

        tile.discover();
        remainingTiles--;
        if(remainingTiles == currentDifficulty.mineNumber) victory();
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
    private void victory(){
        won = true;
        Debugger.info("VICTORY");
    }

    public void rightClickTile(int x, int y){
        if(!started) return;
        else if(won) return;

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