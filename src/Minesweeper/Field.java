package Minesweeper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Field {
    private final MinesweeperManager manager;
    private final Dimension dimension;
    private final int mineNumber;
    private final Tile[] tiles;
    private boolean started;

    protected Field(MinesweeperManager minesweeperManager, Dimension dimension, int initMineNumber){
        Debugger.info("creating new field dimension: " + dimension.width + "x" + dimension.height + " number of Bombs: " + initMineNumber);

        this.manager = minesweeperManager;
        if(dimension.width <= 0 || dimension.height <= 0){
            Debugger.warning("field dimensions must be at least 1x1. chosen dimensions: " + dimension.width + "x" + dimension.height);
            this.dimension = new Dimension(1, 1);
        }else{
            this.dimension = dimension;
        }
        if(initMineNumber > dimension.width * dimension.height){
            Debugger.warning("cant have more mines than tiles chosen. mine number: " + initMineNumber);
            this.mineNumber = 0;
        }else{
            this.mineNumber = initMineNumber;
        }

        this.tiles = new Tile[this.dimension.width * this.dimension.height];
        this.started = false;

        Debugger.info("field setup complete");
    }

    public void startField(int x, int y){
        if(started){
            Debugger.warning("cant start playing because it has already started");
            return;
        }
        Debugger.info("placing " + mineNumber + " mines");

        ArrayList<Integer> minePool = new ArrayList<>(dimension.width * dimension.height - 1);
        for(int i = 0; i < tiles.length; i++){
            if(i != coordToId(x, y)){
                minePool.add(i);
            }
            tiles[i] = new Tile(false);
        }
        int[] mineIds = ranInt(minePool, mineNumber);
        for(int i = 0; i < mineIds.length; i++){
            tiles[i].isMine();
        }
        Debugger.info("placed mines at ids: " + Arrays.toString(mineIds));

        manager.clickTile(x, y);
    }

    protected boolean getIsBomb(int x, int y){
        Tile tile = getTile(x, y);
        if(tile == null) return false;
        else return tile.getIsBomb();
    }
    private Tile getTile(int x, int y){
        if(x < 0 || x >= dimension.width - 1 || y < 0 || y >= dimension.height - 1){
            Debugger.warning("the requested Tile (" + x + "/" + y + ") is out of bounce");
            return null;
        }
        return (tiles[x + y * dimension.width]);
    }

    private int ranInt(int min, int max) {
        if(max + 1 - min <= 0){
            Debugger.warning("cant ask for random number between " + min + " and " + max);
        }
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    private int[] ranInt(ArrayList<Integer> pool, int numOfPulls){
        Debugger.info("pulling " + numOfPulls + " numbers from a pool of " + pool.size() + " numbers");
        if(numOfPulls > pool.size()){
            Debugger.warning("cant ask for more numbers than there are in the pool pool size: " + pool.size() + " pulls: " + numOfPulls);
            throw new IllegalArgumentException("more pull requests than numbers in the pool");
        }

        int[] ranNumbers = new int[numOfPulls];
        for(int i = 0, numIndex; i < numOfPulls; i++){
            numIndex = ranInt(0, pool.size() - 1);
            ranNumbers[i] = pool.get(numIndex);
            pool.remove(numIndex);
        }
        return ranNumbers;
    }
    private int coordToId(int x, int y){
        return x + (dimension.width * y);
    }
    private Dimension idToCoord(int id){
        return new Dimension(id % dimension.width, id - (id % dimension.width));
    }
}

