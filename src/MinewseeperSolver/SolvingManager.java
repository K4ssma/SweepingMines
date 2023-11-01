package MinewseeperSolver;

import java.awt.*;
import java.util.Arrays;

public class SolvingManager {
    private final Dimension DIM = new Dimension(16, 16);
    private int[] info;
    private TileGrid grid;
    private boolean[] needToSolveIds;

    public void start(){
        //get GridInfo
        info = new int[DIM.width * DIM.height];
        grid = new TileGrid(DIM);

        needToSolveIds = new boolean[DIM.width * DIM.height];
        Arrays.fill(needToSolveIds, false);

        if(DIM.width >= 5 && DIM.height >= 5){
            leftClickTile(2, 2);
        }else{
            leftClickTile(0, 0);
        }
        solve();
    }
    private void updateGrid(){
        //get GridStatus
    }

    public void solve(){

    }

    private boolean simpleSolve(){
        boolean somethingChanged = false;

    }

    private void leftClickTile(int x, int y){
        //click Tile(x/y)
    }
    private void rightClickTile(int x, int y){
        //right click Tile(x/y)
    }
}
