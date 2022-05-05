package za.wethinkcode.RobotWorlds;

import java.util.HashMap;

public class Players {
    private static HashMap<String, Robot> robots = new HashMap<String, Robot>();

    static void checkRobot(String name){
        if(!robots.containsKey(name)){
            Robot robot = new Robot(name);
            robots.put(name, robot);
            robot.drawObstacles();
        }
    }

    static Robot getRobot(String name){
        checkRobot(name);
        return robots.get(name);
    }
}
