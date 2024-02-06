import MinewseeperSolver.SolvingManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        SolvingManager solver = new SolvingManager(game);

        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        while(i >= 0){

            switch(i){
                case 0:
                    solver.solve();
                    break;
                case 1:
                    solver.onlyTrySimpleSolve();
                    break;
                case 2:
                    solver.onlyTryAdvancedSolve();
                    break;
                default:
                    break;
            }

            i = scanner.nextInt();
        }
    }
}

//0-8 mine count
//-1 undiscovered
//-2 flag