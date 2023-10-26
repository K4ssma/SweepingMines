package Minesweeper;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Field {
    private int ranInt(int min, int max) {
        if(max + 1 - min <= 0){
            throw new IllegalArgumentException("cant ask for random number between min: " + min + " and max: " + max);
        }
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    private int[] ranInt(int min, int max, int numOfPulls){
        if(max + 1 - min <= 0){
            throw new IllegalArgumentException("cant ask for random number between min: " + min + " and max: " + max);
        }else if(max + 1 - min < numOfPulls){
            throw new IllegalArgumentException("cant ask for more numbers than there are in the Pool min: " + min + " max: " + max + " num of pulls: " + numOfPulls);
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

