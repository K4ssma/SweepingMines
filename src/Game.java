import MinesweeperGame.MinesweeperManager;
import MinewseeperSolver.GameInterface;

public class Game extends MinesweeperManager implements GameInterface {
    @Override
    public int[] getGridInfo() {
        return super.getGridInfo();
    }

    @Override
    public int[] getGridStatus() {
        return super.getGridStatus();
    }

    @Override
    public boolean getIsWon() {
        return super.getIsWon();
    }
}
