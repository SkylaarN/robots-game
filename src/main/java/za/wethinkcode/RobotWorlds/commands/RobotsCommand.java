package za.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.worldLogic.Robot;
import za.wethinkcode.RobotWorlds.worldLogic.SimpleServer;

public class RobotsCommand extends Command{
    public boolean execute(Robot target) {
        JSONObject reply = new JSONObject();
        JSONObject data = new JSONObject();

        reply.put("result", "OK");
        data.put("message", "Robots: " + SimpleServer.listRobots);
        reply.put("data", data);

        target.setStatus(reply.toString());
        System.out.println(reply);
        return true;
    }



    public RobotsCommand() {
        super();
    }
}
