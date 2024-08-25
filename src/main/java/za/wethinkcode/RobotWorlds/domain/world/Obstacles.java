package za.wethinkcode.RobotWorlds.domain.world;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;


public class Obstacles {


    public static ArrayList<SquareObstacle> obstacles = new ArrayList<>();
    public static ArrayList<SquareObstacle> pits = new ArrayList<>();
    public static Random random = new Random();


    //hope
    // Set the restored obstacles
    public static void setObstacles(List<SquareObstacle> restoredObstacles) {
        obstacles.addAll(restoredObstacles);
        pits.addAll(restoredObstacles);
    }

    public static void generateObstacles(){
//        System.out.println("used obstacle generateobstacle");
        for (int i = 0; i < 10; i++) {
            int x = (random.nextInt(60)- 30) * 5;
            int y = (random.nextInt(60)- 30) * 5;
            obstacles.add(new SquareObstacle(x, y));
            pits.add(new SquareObstacle(x, y));


        }
    }

    public static ArrayList<SquareObstacle> getObstacles(){
        return obstacles;
    }

    public static boolean blocksPosition(Position a){
//        System.out.println("used obstacle");
        for (SquareObstacle obstacle : obstacles) {
            if (obstacle.blocksPosition(a)) {
                return true;
            }
        }

        for (SquareObstacle pit : pits) {
            if (pit.blocksPosition(a)) {
                return true;
            }
        }
        return false;
    }
    public static boolean pitPosition(Position a){
//        System.out.println("used obstacle");

        for (SquareObstacle pit : pits) {
            if (pit.blocksPosition(a)) {
                return true;
            }
        }
        return false;
    }

    public static boolean blocksPath(Position a, Position b){
        for (SquareObstacle obstacle : obstacles) {
            if (obstacle.blocksPath(a, b)) {
                return true;
            }
        }

        return false;
    }

    public static boolean pitPath(Position a, Position b){
        for (SquareObstacle pit : pits) {
            if (pit.blocksPath(a, b)) {
                return true;
            }
        }
        return false;
    }
}
