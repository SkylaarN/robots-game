package za.wethinkcode.RobotWorlds;

import java.util.ArrayList;
import java.util.HashMap;

public class Players {
    private static HashMap<String, Robot> robots = new HashMap<String, Robot>();
    private static ArrayList<Robot> playerRobots = new ArrayList<>();

    public static void checkRobot(String name){
        if(!robots.containsKey(name)){
            Robot robot = new Robot(name);
            robots.put(name, robot);
            playerRobots.add(robot);
        }
    }

    public static Robot getRobot(String name){
        checkRobot(name);
        return robots.get(name);
    }

    public static ArrayList<Robot> getPlayers(){
        return playerRobots;
    }
}
