package MinewseeperSolver;

import java.awt.*;

public class TileGrid {
    private final Dimension dimension;
    private final Tile[] tiles;

    protected TileGrid(Dimension dimension){
        this.dimension = dimension;
        tiles = new Tile[dimension.width * dimension.height];
        for(int i = 0; i < tiles.length; i++){
            tiles[i] = new Tile(i);
        }
    }

    protected Tile getTile(int x, int y){
        return tiles[coordToId(x, y)];
    }
    protected Tile getTile(int id){
        return tiles[id];
    }
    protected int[] getTileInfo(){
        int[] info = new int[tiles.length];
        for(int i = 0; i < info.length; i++){
            if(tiles[i].getIsFlagged()){
                info[i] = -2;
            }else if(tiles[i].getIsDiscovered()){
                info[i] = tiles[i].getMineCount();
            }else{
                info[i] = -1;
            }
        }
        return info;
    }

    protected Dimension idToCoord(int id){
        return new Dimension(id % dimension.width, id / dimension.width);
    }
    protected int coordToId(int x, int y){
        return x + (y * dimension.width);
    }
}
