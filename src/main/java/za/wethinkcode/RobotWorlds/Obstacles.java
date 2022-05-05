package za.wethinkcode.RobotWorlds;

import edu.princeton.cs.introcs.StdDraw;

import java.util.ArrayList;
import java.util.Random;

public class Obstacles {
    public static ArrayList<SquareObstacle> obstacles = new ArrayList<>();
    public static Random random = new Random();

    public static void generateObstacles(){
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(300)- 150;
            int y = random.nextInt(300)- 150;
            obstacles.add(new SquareObstacle(x, y));
        }
    }

    public static ArrayList<SquareObstacle> getObstacles(){
        return obstacles;
    }

    public static boolean blocksPosition(Position a){
        for (int i = 0; i < obstacles.size(); i++) {
            if(obstacles.get(i).blocksPosition(a) == true){
                return true;
            }
        }
        return false;
    }

    public static boolean blocksPath(Position a, Position b){
        for (int i = 0; i < obstacles.size(); i++) {
            if(obstacles.get(i).blocksPath(a, b) == true){
                return true;
            }
        }
        return false;
    }
}
