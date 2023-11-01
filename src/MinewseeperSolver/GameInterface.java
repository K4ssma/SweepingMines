package MinewseeperSolver;

public interface GameInterface {
    public int[] getGridInfo();
    public int[] getGridStatus();
    public boolean getIsWon();

    public void leftClickTile(int x, int y);
    public void rightClickTile(int x, int y);
    public void restartGame();
}
