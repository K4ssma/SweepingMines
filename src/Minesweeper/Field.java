package Minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Field {
    private final int width, height;
    private final Tile[] tiles;

    protected Field(int width, int height, int initNumBombs){
        Debugger.info("creating new field width: " + width + " height: " + height + " number of Bombs: " + initNumBombs);
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width * height];

        int[] bombIndices = ranInt(0, width * height, initNumBombs);
        Arrays.sort(bombIndices);
        for(int i = 0, bombIndex = 0; i < width * height; i++){
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

    protected int getNeighbourBombCount(int x, int y){
        Debugger.info("checking neighbour bomb number of x: " + x + " y: " + y);
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
        Debugger.info("getting Neighbours of Tile x: " + x + " y: " + y);
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
            if(coord[0] < 0 || coord[1] < 0 || coord[0] >= width || coord[1] >= height){
                neighbours[i] = null;
            }else{
                neighbours[i] = getTile(coord[0], coord[1]);
            }
        }
        return neighbours;
    }
    protected boolean getIsBomb(int x, int y){
        Tile tile = getTile(x, y);
        if(tile == null) return false;
        else return tile.getIsBomb();
    }
    private Tile getTile(int x, int y){
        if(x < 0 || x > width - 1 || y < 0 || y > height - 1){
            throw new IndexOutOfBoundsException("cant request tile which is out of bounce x: " + x + " y: " + y);
        }
        return (tiles[x + y * width]);
    }

    private int ranInt(int min, int max) {
        if(max + 1 - min <= 0){
            Debugger.warning("cant ask for random number between min: " + min + " and max: " + max);
        }
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    private int[] ranInt(int min, int max, int numOfPulls){
        if(max + 1 - min <= 0){
            Debugger.warning("cant ask for random number between min: " + min + " and max: " + max);
        }else if(max + 1 - min < numOfPulls){
            Debugger.warning("cant ask for more numbers than there are in the Pool min: " + min + " max: " + max + " num of pulls: " + numOfPulls);
        }

        ArrayList<Integer> pool = new ArrayList<>();
        for(int i = min; i <= max; i++){
            pool.add(i);
        }

        int[] result = new int[numOfPulls];
        for(int i = 0; i < numOfPulls; i++){
            int rndNum = ranInt(0, pool.size() - 1);
            result[i] = rndNum;
            pool.remove(rndNum);
        }
        return result;
    }
}

