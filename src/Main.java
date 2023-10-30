import Minesweeper.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Minesweeper minesweeper = new Minesweeper();
        minesweeper.startGui();
        Scanner scanner = new Scanner(System.in);
        if(scanner.nextInt() == 1){
            System.out.println(minesweeper.windowSize().width + ", " + minesweeper.windowSize().height);
        }
    }
}