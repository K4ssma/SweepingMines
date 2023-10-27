package Minesweeper;

public class Tile {
    private final boolean bomb;
    private boolean isDiscovered;
    private boolean isFlagged;

    protected Tile(boolean bomb){
        this.bomb = bomb;
        isDiscovered = false;
        isFlagged = false;
    }

    protected boolean getIsBomb(){
        return bomb;
    }
    protected void setDiscovered(boolean isDiscovered){
        this.isDiscovered = isDiscovered;
    }
    protected boolean getDiscovered(){
        return isDiscovered;
    }
    protected void setFlag(boolean isFlagged){
        this.isFlagged = isFlagged;
    }
    protected boolean getIsFlagged(){
        return isFlagged;
    }
}
