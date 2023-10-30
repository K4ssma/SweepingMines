package Minesweeper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Field {
    private final Dimension dimension;
    private final Tile[] tiles;

    protected Field(Dimension dimension, int initNumBombs){
        Debugger.info("creating new field dimension: " + dimension.width + "x" + dimension.height + " number of Bombs: " + initNumBombs);
        this.dimension = dimension;
        this.tiles = new Tile[dimension.width * dimension.height];

        ArrayList<Integer> pool = new ArrayList<>(dimension.width * dimension.height);
        for(int i = 0; i < dimension.width * dimension.height; i++) pool.add(i);
        int[] bombIndices = ranInt(pool, initNumBombs);
        Arrays.sort(bombIndices);
        for(int i = 0, bombIndex = 0; i < dimension.width * dimension.height; i++){
            if(i == bombIndices[bombIndex]){
                tiles[i] = new Tile(true);
                bombIndex++;
            }else{
                tiles[i] = new Tile(false);
            }
        }
        Debugger.info("instantiated all " + tiles.length + " tiles");
        Debugger.info("completed Field initialization");
    }

    protected void discoverTile(int x,  int y){
        Tile tile = getTile(x, y);
        if(tile == null){
            Debugger.warning("could not discover Tile (" + x + "/" + y + ")");
            return;
        }
        if(!tile.getDiscovered()){
            tile.discover();
            Debugger.info("Tile (" + x + "/" + y + ") discovered");
        }else{
            Debugger.info("cant discover Tile (" + x + "/" + y +") because it is already discovered");
        }
    }

    protected void flagTile(int x, int y){
        Tile tile = getTile(x, y);
        if(tile == null){
            Debugger.warning("could not change flag of Tile (" + x + "/" + y +")");
            return;
        }
        if(!tile.getIsFlagged()){
            Debugger.info("flagging Tile (" + x + "/" + y + ")");
            tile.setFlag(true);
        }else{
            Debugger.info("unflagging Tile (" + x + "/" + y + ")");
            tile.setFlag(true);
        }
    }

    protected int getNeighbourBombCount(int x, int y){
        Debugger.info("checking neighbour bomb number of Tile (" + x + "/" + y + ")");
        Tile[] neighbours = getNeighbours(x, y);
        int bombCount = 0;
        for(Tile neighbour : neighbours){
            if(neighbour != null && neighbour.getIsBomb()){
                bombCount++;
            }
        }
        return bombCount;
    }
    private Tile[] getNeighbours(int x, int y){
        Debugger.info("getting Neighbours of Tile (" + x + "/" + y + ")");
        Tile[] neighbours = new Tile[8];
        int[] coord = new int[2];
        for(int i = 0; i < 8; i++){
            switch (i) {
                case 0 -> {
                    coord[0] = x;
                    coord[1] = y + 1;
                }
                case 1 -> {
                    coord[0] = x + 1;
                    coord[1] = y + 1;
                }
                case 2 -> {
                    coord[0] = x + 1;
                    coord[1] = y;
                }
                case 3 -> {
                    coord[0] = x + 1;
                    coord[1] = y - 1;
                }
                case 4 -> {
                    coord[0] = x;
                    coord[1] = y - 1;
                }
                case 5 -> {
                    coord[0] = x - 1;
                    coord[1] = y - 1;
                }
                case 6 -> {
                    coord[0] = x - 1;
                    coord[1] = y;
                }
                case 7 -> {
                    coord[0] = x - 1;
                    coord[1] = y + 1;
                }
            }
            neighbours[i] = getTile(coord[0], coord[1]);
        }
        return neighbours;
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
}

