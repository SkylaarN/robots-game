package za.wethinkcode.RobotWorlds.server;

import org.apache.commons.cli.*;
import za.wethinkcode.RobotWorlds.domain.world.SquareObstacle;
import za.wethinkcode.RobotWorlds.domain.world.configuration.Configuration;

import static za.wethinkcode.RobotWorlds.domain.world.Obstacles.obstacles;

public class CommandLineArgs {
    public CommandLineArgs(String[] args){
        cmdArgs(args);
    }
    public static void cmdArgs(String[] args) {
        Options options = new Options();

        Option port = new Option("p", "port", true, "port number");
        port.setRequired(false);
        options.addOption(port);

        Option size = new Option("s", "size", true, "size of the world");
        size.setRequired(false);
        options.addOption(size);

        Option obstacle = new Option("o", "obstacle", true, "obstacle");
        obstacle.setRequired(false);
        options.addOption(obstacle);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            System.exit(1);
            return;
        }

        // Get the port if specified
        if (cmd.hasOption("port")) {
            String port1 = cmd.getOptionValue("port");
            SimpleServer.PORT = Integer.parseInt(port1);
        }
        if (cmd.hasOption("size")) {
            String worldSize = cmd.getOptionValue("size");
            int widthHeight = Integer.parseInt(worldSize);
            Configuration config = new Configuration();
            config.setWorldSize(widthHeight,widthHeight);
        }
        if (cmd.hasOption("obstacle")) {
            String obstacleSize = cmd.getOptionValue("obstacle");
            String[] obstacleSizeArray = obstacleSize.split(",");
            int x = Integer.parseInt(obstacleSizeArray[0]);
            int y = Integer.parseInt(obstacleSizeArray[1]);
            obstacles.clear();
            obstacles.add(new SquareObstacle(x, y));

        }
    }
}
