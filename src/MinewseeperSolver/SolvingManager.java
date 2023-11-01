package MinewseeperSolver;

import java.awt.*;
import java.util.Arrays;

public class SolvingManager {
    private Dimension dimension;
    private int mineNumber;
    private TileGrid grid;
    private boolean[] needToSolveIds;
    private GameInfo informant;

    public void start(GameInfo informant){
        int[] gridInfo = informant.getGridInfo();
        dimension = new Dimension(gridInfo[0], gridInfo[1]);
        mineNumber = gridInfo[2];

        grid = new TileGrid(dimension);
        this.informant = informant;

        needToSolveIds = new boolean[dimension.width * dimension.height];
        Arrays.fill(needToSolveIds, true);

        if(dimension.width >= 5 && dimension.height >= 5){
            leftClickTile(2, 2);
        }else{
            leftClickTile(0, 0);
        }
        solve();
    }
    private void updateGrid(){
        int[] oldInfo = grid.getTileInfo();
        int[] info = informant.getGridStatus();

        for(int i = 0; i < info.length; i++){
            if(oldInfo[i] != info[i]){
                Tile tile = grid.getTile(i);
                if(info[i] == -2){
                    tile.setIsFlagged(true);
                }else if(info[i] == 0){
                    tile.discover(0);
                }else{
                    tile.discover(i);
                    needToSolveIds[i] = true;
                }
            }
        }
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
