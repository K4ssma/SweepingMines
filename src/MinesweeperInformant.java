import MinesweeperGame.MinesweeperManager;
import MinewseeperSolver.GameInfo;

public class MinesweeperInformant extends MinesweeperManager implements GameInfo {
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
