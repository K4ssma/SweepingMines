import MinesweeperGame.MinesweeperManager;
import MinewseeperSolver.SolvingManager;

public class Main {
    public static void main(String[] args) {
        MinesweeperManager game = new MinesweeperManager();
        SolvingManager solver = new SolvingManager();
    }
}

//0-8 mine count
//-1 undiscovered
//-2 flag