import MinewseeperSolver.SolvingManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        SolvingManager solver = new SolvingManager(game);

        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        while(i != 2){
            if(i == 0){
                solver.start();
            }else if(i == 1){
                solver.solve();
            }

            i = scanner.nextInt();
        }
    }
}

//0-8 mine count
//-1 undiscovered
//-2 flag