package za.wethinkcode.RobotWorlds.worldLogic;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;


public class Obstacles {
    public static ArrayList<SquareObstacle> obstacles = new ArrayList<>();
    public static Random random = new Random();

    //hope
    // Set the restored obstacles
    public static void setObstacles(List<SquareObstacle> restoredObstacles) {
        obstacles.clear();
        obstacles.addAll(restoredObstacles);
    }

    public static void generateObstacles(){
        for (int i = 0; i < 100; i++) {
            int x = (random.nextInt(60)- 30) * 5;
            int y = (random.nextInt(60)- 30) * 5;
            obstacles.add(new SquareObstacle(x, y));
        }
    }

    public static ArrayList<SquareObstacle> getObstacles(){
        return obstacles;
    }

    public static boolean blocksPosition(Position a){
        for (SquareObstacle obstacle : obstacles) {
            if (obstacle.blocksPosition(a)) {
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
}
