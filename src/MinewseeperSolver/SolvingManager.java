package MinewseeperSolver;

import DebugPackage.Debugger;

import java.awt.*;
import java.util.Arrays;

public class SolvingManager {
    private Dimension dimension;
    private TileGrid grid;
    private boolean[] needToSolveIds;
    private final GameInterface game;

    public SolvingManager(GameInterface game){
        this.game = game;
    }

    public void start(){
        Debugger.info("started solver");

        int[] gridInfo = game.getGridInfo();
        dimension = new Dimension(gridInfo[0], gridInfo[1]);

        grid = new TileGrid(dimension);

        needToSolveIds = new boolean[dimension.width * dimension.height];
        Arrays.fill(needToSolveIds, true);

        if(dimension.width >= 5 && dimension.height >= 5){
            game.leftClickTile(2, 2);
        }else{
            game.leftClickTile(0, 0);
        }
        solve();
    }
    private void updateGrid(){
        Debugger.info("grid getting updated");
        int[] oldInfo = grid.getTileInfo();
        int[] info = game.getGridStatus();

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

    private void solve(){
        Debugger.info("solving");
        updateGrid();
        simpleSolve();
    }

    private boolean simpleSolve(){
        Debugger.info("simple solve");
        boolean somethingChanged = false;

        for(int i = 0; i < needToSolveIds.length; i++){
            if(needToSolveIds[i]){
                Tile tile = grid.getTile(i);
                int neighbourFlags = grid.neighbourFlags(i);
                //check if already solved
                if(neighbourFlags == tile.getMineCount()){
                    needToSolveIds[i] = false;
                }else if(neighbourFlags + 1 == tile.getMineCount()){
                    for(Tile neighbour : grid.getNeighbours(i)){
                        if(neighbour != null && !neighbour.getIsDiscovered()){
                            neighbour.setIsFlagged(true);
                            Dimension coord = grid.idToCoord(i);
                            game.rightClickTile(coord.width, coord.height);
                        }
                    }
                    needToSolveIds[i] = false;
                    somethingChanged = true;
                }
            }
        }
        return somethingChanged;
    }
}
