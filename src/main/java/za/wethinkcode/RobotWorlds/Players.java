package za.wethinkcode.RobotWorlds;

import java.util.HashMap;

public class Players {
    private static HashMap<String, Robot> robots = new HashMap<String, Robot>();

    static void checkRobot(String name){
        if(!robots.containsKey(name)){
            robots.put(name, new Robot(name));
        }
    }

    static Robot getRobot(String name){
        checkRobot(name);
        return robots.get(name);
    }
}
