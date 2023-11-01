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

    protected Tile getTile(int id){
        if(id >= tiles.length) return null;
        return tiles[id];
    }
    protected Tile getTile(int x, int y){
        return getTile(coordToId(x, y));
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

    protected Tile[] getNeighbours(int x, int y){
        Tile[] neighbours = new Tile[8];
        neighbours[0] = getTile(x, y + 1);
        neighbours[1] = getTile(x + 1, y + 1);
        neighbours[2] = getTile(x + 1, y);
        neighbours[3] = getTile(x + 1, y - 1);
        neighbours[4] = getTile(x, y - 1);
        neighbours[5] = getTile(x - 1, y - 1);
        neighbours[6] = getTile(x - 1, y);
        neighbours[7] = getTile(x - 1, y + 1);
        return neighbours;
    }
    protected Tile[] getNeighbours(int id){
        return getNeighbours(idToCoord(id).width, idToCoord(id).height);
    }
    protected int neighbourFlags(int x, int y){
        int counter = 0;
        for(Tile neighbour : getNeighbours(x, y)){
            if(neighbour != null && neighbour.getIsFlagged()){
                counter++;
            }
        }
        return counter;
    }
    protected int neighbourFlags(int id){
        Dimension coord = idToCoord(id);
        return neighbourFlags(coord.width, coord.height);
    }

    protected Dimension idToCoord(int id){
        return new Dimension(id % dimension.width, id / dimension.width);
    }
    protected int coordToId(int x, int y){
        return x + (y * dimension.width);
    }
}
