package za.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.Obstacles;
import za.wethinkcode.RobotWorlds.Robot;
import za.wethinkcode.RobotWorlds.SimpleServer;
import za.wethinkcode.RobotWorlds.SquareObstacle;

import java.util.ArrayList;

public class DumpCommand extends Command {
    private int a;
    private int b;

    public boolean execute(Robot target) {

        ArrayList<SquareObstacle> obstacles = Obstacles.getObstacles();
        int x = target.getPosition().getX();
        int y = target.getPosition().getY();

        for (int i = 0; i < obstacles.size(); i++) {
            SquareObstacle sqrObs = obstacles.get(i);
            this.a = sqrObs.getBottomLeftX();
            this.b = sqrObs.getBottomLeftY();
        }
        JSONObject reply = new JSONObject();
        JSONObject data = new JSONObject();

        reply.put("result", "OK");
        data.put("message", "Your robot : " + target.getName() + " - At position " + "(" + x + "," + y + "), " + "~"
                + "All robots: "
                + SimpleServer.listRobots);
        reply.put("data", data);
        target.setStatus(reply.toString());

        return true;
    }

    public DumpCommand() {
        super("dump");
    }
}
