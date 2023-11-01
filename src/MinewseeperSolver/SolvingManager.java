package MinewseeperSolver;

import DebugPackage.Debugger;

import java.awt.*;
import java.util.ArrayList;
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
        Arrays.fill(needToSolveIds, false);

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
                    tile.discover(info[i]);
                    needToSolveIds[i] = true;
                }
            }
        }
    }

    public void solve(){
        Debugger.info("solving");

        boolean changed = true;
        while(!game.getIsWon() && changed){
            updateGrid();
            changed = simpleSolve();
        }

        if(game.getIsWon()){
            while(changed){
                updateGrid();
                changed = simpleSolve();
            }
            Debugger.info("VICTORY");
        }else {
            Debugger.warning("unable to solve more tiles");
        }
    }

    private boolean simpleSolve(){
        Debugger.info("simple solve");
        boolean somethingChanged = false;

        for(int i = 0; i < needToSolveIds.length; i++){
            if(needToSolveIds[i]){

                Tile tile = grid.getTile(i);
                int neighbourFlags = grid.neighbourFlags(i);
                int undiscoveredNeighbours = grid.undiscoveredNeighbours(i);

                Debugger.info("trying to solve: (" + grid.idToCoord(i).width + "/" + grid.idToCoord(i).height + ")");
                Debugger.info("neighbouring flags: " + neighbourFlags);
                Debugger.info("undiscovered neighbours: " + undiscoveredNeighbours);
                Debugger.info("mines: " + tile.getMineCount());

                //check if already solved
                if(neighbourFlags == tile.getMineCount() && undiscoveredNeighbours - neighbourFlags == 0){
                    Debugger.info("nothing to do, marking tile as solved");
                    needToSolveIds[i] = false;
                }

                //check if remaining neighbours is equal to remaining mines
                else if(undiscoveredNeighbours - neighbourFlags == tile.getMineCount() - neighbourFlags){
                    Debugger.info("placing flags at neighbours");
                    for(Tile neighbour : grid.getNeighbours(i)){
                        if(neighbour != null && !neighbour.getIsDiscovered() && !neighbour.getIsFlagged()){

                            neighbour.setIsFlagged(true);
                            Dimension coord = grid.idToCoord(neighbour.getId());
                            Debugger.info("placing flag at: (" + coord.width + "/" + coord.height + ")");
                            game.rightClickTile(coord.width, coord.height);

                        }
                    }
                    needToSolveIds[i] = false;
                    somethingChanged = true;
                }

                //check if no mine on neighbours is left
                else if(tile.getMineCount() - neighbourFlags == 0){
                    Debugger.info("no mines left");
                    for(Tile neighbour : grid.getNeighbours(i)){
                        if(neighbour != null && !neighbour.getIsFlagged() && !neighbour.getIsDiscovered()){
                            Dimension coord = grid.idToCoord(neighbour.getId());
                            Debugger.info("clicking (" + coord.width + "/" + coord.height + ")");
                            Debugger.info("neighbour id: " + neighbour.getId());
                            game.leftClickTile(coord.width, coord.height);
                        }
                    }
                    needToSolveIds[i] = false;
                    somethingChanged = true;
                }
            }
        }
        return somethingChanged;
    }

    private boolean advancedSolve(){
        Debugger.info("advanced solve");
        boolean changed = false;

        for(int i = 0; i < needToSolveIds.length; i++){
            if(needToSolveIds[i]){

                //1 check if one tile shares all its undiscovered neighbours with one of his unsolved neighbours

                Tile[] neighbours = grid.getNeighbours(i);

                //1.1 get the undiscovered and unflagged neighbours
                ArrayList<Integer> validNeighbourIds = new ArrayList<>();
                for(Tile neighbour : neighbours){
                    if(!neighbour.getIsFlagged() && !neighbour.getIsDiscovered()){
                        validNeighbourIds.add(neighbour.getId());
                    }
                }

                //1.2 compare them to all neighbours
                for(Tile neighbour : neighbours){

                    //1.2.1 getting the valid neighbours neighbour ids
                    ArrayList<Integer> validNeighboursNeighbourIds = new ArrayList<>();
                    for(Tile neighboursneighbour : grid.getNeighbours(neighbour.getId())){
                        if(!neighboursneighbour.getIsFlagged() && !neighboursneighbour.getIsDiscovered()){
                            validNeighboursNeighbourIds.add(neighboursneighbour.getId());
                        }
                    }

                    //1.2.2 comparing neighbour to origin tile
                    ArrayList<Integer> foundCommonNeighboursIds = new ArrayList<>();
                    ArrayList<Integer> unsharedNeighboursIds = new ArrayList<>();
                    for(int neighboursNeighbourId : validNeighboursNeighbourIds){
                        boolean found = false;
                        for(int neighbourId : validNeighbourIds){
                            if(neighboursNeighbourId == neighbourId){
                                found = true;
                            }
                        }
                        if(found){
                            foundCommonNeighboursIds.add(neighboursNeighbourId);
                        }else{
                            unsharedNeighboursIds.add(neighboursNeighbourId);
                        }
                    }
                    if(foundCommonNeighboursIds.size() == validNeighbourIds.size()){

                        //found all valid tiles that are shared with one of its neighbours
                        //1.3 checking if the shared mines from the origin tile say something about the neighbours unshared tiles

                        //1.3.1 case 1 there cant be any mines on the remaining tile
                        if((neighbour.getMineCount() - grid.neighbourFlags(neighbour.getId()))
                                - (grid.getTile(i).getMineCount() - grid.neighbourFlags(i)) == 0){
                            Dimension coord = grid.idToCoord(neighbour.getId());
                            Debugger.info("clicking unshared neighbours of (" + coord.width + "/" + coord.height + ")");
                            for(int unsharedNeighbour : unsharedNeighboursIds){
                                game.leftClickTile(grid.idToCoord(unsharedNeighbour).width, grid.idToCoord(unsharedNeighbour).height);
                                changed = true;
                            }
                        }
                        //1.3.2 case 2 there are remaining mines equal to the unshared mines
                        else if(validNeighboursNeighbourIds.size() == (neighbour.getMineCount() - grid.neighbourFlags(neighbour.getId()))
                                - (grid.getTile(i).getMineCount() - grid.neighbourFlags(i))){
                            for(int unsharedTiles : unsharedNeighboursIds){
                                game.leftClickTile(grid.idToCoord(unsharedTiles).width, grid.idToCoord(unsharedTiles).height);
                                changed = true;
                            }
                        }
                    }
                }

            }
        }

        return changed;
    }
}
