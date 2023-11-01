package MinewseeperSolver;

public class Tile {
    private final int id;
    private boolean isDiscovered, isFlagged;
    private int mineCount;

    protected Tile(int id){
        this.id = id;
        this.isDiscovered = false;
        this.isFlagged = false;
    }

    protected void discover(int mineCount){
        isDiscovered = true;
        this.mineCount = mineCount;
    }
    protected boolean getIsDiscovered(){
        return isDiscovered;
    }

    protected void setIsFlagged(boolean isFlagged){
        this.isFlagged = isFlagged;
    }
    protected boolean getIsFlagged(){
        return isFlagged;
    }

    protected int getMineCount(){
        return mineCount;
    }

    protected int getId(){
        return id;
    }
}
