package za.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.worldLogic.Obstacles;
import za.wethinkcode.RobotWorlds.worldLogic.Robot;
import za.wethinkcode.RobotWorlds.worldLogic.SimpleServer;
import za.wethinkcode.RobotWorlds.worldLogic.SquareObstacle;

import java.util.ArrayList;

public class DumpCommand extends Command {

    public boolean execute(Robot target) {

        ArrayList<SquareObstacle> obstacles = Obstacles.getObstacles();
        int x = target.getPosition().getX();
        int y = target.getPosition().getY();
        String obs = "";

        for (int i = 0; i < obstacles.size(); i++) {
            SquareObstacle sqrObs = obstacles.get(i);
            obs = obs + "\n[" + sqrObs.getBottomLeftX() + " , " + sqrObs.getBottomLeftY() + "]";
        }
        JSONObject reply = new JSONObject();
        JSONObject data = new JSONObject();

        reply.put("result", "OK");
        data.put("message", "Your robot : " + target.getName() + " - At position " + "(" + x + "," + y + "), " + "\n"
                + "All robots: \n"
                + SimpleServer.listRobots
                + "All Obstacles: "
                + obs);
        reply.put("data", data);
        target.setStatus(reply.toString());

        return true;
    }

    public DumpCommand() {
        super("dump");
    }
}
