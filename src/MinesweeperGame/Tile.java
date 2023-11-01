package MinesweeperGame;

public class Tile {
    private boolean mine;
    private boolean isDiscovered;
    private boolean isFlagged;

    protected Tile(){
        this.mine = false;
        isDiscovered = false;
        isFlagged = false;
    }

    protected void isMine(){
        this.mine = true;
    }
    protected boolean getIsMine(){
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
