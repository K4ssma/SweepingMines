package Minesweeper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static Minesweeper.Variables.*;

public class Field {
    private final MinesweeperManager manager;
    private final Tile[] tiles;

    protected Field(MinesweeperManager minesweeperManager, Dimension dimension, int initMineNumber){
        Debugger.info("creating new field dimension: " + dimension.width + "x" + dimension.height + " number of Bombs: " + initMineNumber);

        this.manager = minesweeperManager;
        this.tiles = new Tile[currentDifficulty.dimension.width * currentDifficulty.dimension.height];

        Debugger.info("field setup complete");
    }

    public void startField(int x, int y){
        Debugger.info("placing " + currentDifficulty.mineNumber + " mines");

        ArrayList<Integer> minePool = new ArrayList<>(currentDifficulty.dimension.width * currentDifficulty.dimension.height - 1);
        for(int i = 0; i < tiles.length; i++){
            if(i != manager.coordToId(x, y)){
                minePool.add(i);
            }
            tiles[i] = new Tile(false);
        }
        int[] mineIds = ranInt(minePool, currentDifficulty.mineNumber);
        for(int id : mineIds){
            tiles[id].isMine();
        }
        Debugger.info("placed mines at ids: " + Arrays.toString(mineIds));
    }

    protected Tile getTile(int x, int y){
        if(x < 0 || x >= currentDifficulty.dimension.width || y < 0 || y >= currentDifficulty.dimension.height){
            return null;
        }
        return (tiles[x + y * currentDifficulty.dimension.width]);
    }

    private int ranInt(int min, int max) {
        if(max + 1 - min <= 0){
            Debugger.warning("cant ask for random number between " + min + " and " + max);
        }
        Random rand = new Random();
        return rand.nextInt((max + 1) - min) + min;
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
}

