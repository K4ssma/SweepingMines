package MinesweeperGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static MinesweeperGame.Variables.*;

public class Field {
    private final MinesweeperManager manager;
    private final Tile[] tiles;

    protected Field(MinesweeperManager minesweeperManager, Dimension dimension, int initMineNumber){
        DebugPackage.Debugger.info("creating new field dimension: " + dimension.width + "x" + dimension.height + " number of Bombs: " + initMineNumber);

        this.manager = minesweeperManager;
        this.tiles = new Tile[currentDifficulty.dimension.width * currentDifficulty.dimension.height];

        DebugPackage.Debugger.info("field setup complete");
    }

    public void startField(int x, int y){
        DebugPackage.Debugger.info("placing " + currentDifficulty.mineNumber + " mines");

        ArrayList<Integer> minePool = new ArrayList<>(currentDifficulty.dimension.width * currentDifficulty.dimension.height - 1);
        for(int i = 0; i < tiles.length; i++){
            if(i != manager.coordToId(x, y)){
                minePool.add(i);
            }
            tiles[i] = new Tile();
        }
        int[] mineIds = ranInt(minePool, currentDifficulty.mineNumber);
        for(int id : mineIds){
            tiles[id].isMine();
        }
        DebugPackage.Debugger.info("placed mines at ids: " + Arrays.toString(mineIds));
    }

    protected Tile getTile(int x, int y){
        if(x < 0 || x >= currentDifficulty.dimension.width || y < 0 || y >= currentDifficulty.dimension.height){
            return null;
        }
        return (tiles[x + y * currentDifficulty.dimension.width]);
    }

    private int ranInt(int max) {
        if(max + 1 <= 0){
            DebugPackage.Debugger.warning("cant ask for random number between " + 0 + " and " + max);
        }
        Random rand = new Random();
        return rand.nextInt(max + 1);
    }
    private int[] ranInt(ArrayList<Integer> pool, int numOfPulls){
        DebugPackage.Debugger.info("pulling " + numOfPulls + " numbers from a pool of " + pool.size() + " numbers");
        if(numOfPulls > pool.size()){
            DebugPackage.Debugger.warning("cant ask for more numbers than there are in the pool pool size: " + pool.size() + " pulls: " + numOfPulls);
            throw new IllegalArgumentException("more pull requests than numbers in the pool");
        }

        int[] ranNumbers = new int[numOfPulls];
        for(int i = 0, numIndex; i < numOfPulls; i++){
            numIndex = ranInt(pool.size() - 1);
            ranNumbers[i] = pool.get(numIndex);
            pool.remove(numIndex);
        }
        return ranNumbers;
    }
}

