package za.wethinkcode.RobotWorlds.commands;

import za.wethinkcode.RobotWorlds.Obstacles;
import za.wethinkcode.RobotWorlds.Robot;
import za.wethinkcode.RobotWorlds.SimpleServer;
import za.wethinkcode.RobotWorlds.SquareObstacle;

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

        target.setStatus("Your robot : " + target.getName() + " - At position " + "(" + x + "," + y + "), " + "~"
                + "All robots: "
                + SimpleServer.listRobots + "~" + "There are obtacles:" + "~" + "- At position "
                + a + "," + b + " (to " + (a + 4 - 1) + "," + (b + 4 - 1) + ")");

        return true;
    }

    public DumpCommand() {
        super("dump");
    }
}
