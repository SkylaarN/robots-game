package za.wethinkcode.RobotWorlds;

import java.util.ArrayList;
import java.util.HashMap;

public class Players {
    private static HashMap<String, Robot> robots = new HashMap<String, Robot>();

    public static void checkRobot(String name){
        if(!robots.containsKey(name)){
            Robot robot = new Robot(name);
            robots.put(name, robot);
        }
    }

    public static Robot getRobot(String name){
        checkRobot(name);
        return robots.get(name);
    }

//    public static ArrayList<Robot> getRobots(){
//        ArrayList<SquareObstacle> obstacles = new ArrayList<>();
//        for (int i = 0; i < robots.size(); i++) {
//
//        }
//    }
}
