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

    protected Dimension idToCoord(int id){
        return new Dimension(id % dimension.width, id / dimension.width);
    }
    protected int coordToId(int x, int y){
        return x + (y * dimension.width);
    }
}
