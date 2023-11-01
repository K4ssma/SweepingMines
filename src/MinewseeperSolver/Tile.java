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

    protected void discover(){
        isDiscovered = true;
    }

    protected void setIsFlagged(boolean isFlagged){
        this.isFlagged = isFlagged;
    }
    protected boolean getIsStatus(){
        return isFlagged;
    }

    protected void setMineCount(int mineCount){
        this.mineCount = mineCount;
    }
    protected int getMineCount(){
        return mineCount;
    }
}
