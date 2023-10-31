package Minesweeper;

public class Tile {
    private boolean mine;
    private boolean isDiscovered;
    private boolean isFlagged;

    protected Tile(boolean isMine){
        this.mine = false;
        isDiscovered = false;
        isFlagged = false;
    }

    protected void isMine(){
        this.mine = true;
    }
    protected boolean getIsBomb(){
        return mine;
    }
    protected void discover(){
        this.isDiscovered = true;
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
