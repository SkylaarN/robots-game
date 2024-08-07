package za.wethinkcode.RobotWorlds;

import org.apache.commons.cli.*;
import za.wethinkcode.RobotWorlds.configuration.Configuration;
import za.wethinkcode.RobotWorlds.worldLogic.Obstacles;
import za.wethinkcode.RobotWorlds.worldLogic.SimpleServer;
import za.wethinkcode.RobotWorlds.worldLogic.SquareObstacle;

import java.net.*;
import java.io.*;

import static za.wethinkcode.RobotWorlds.worldLogic.Obstacles.obstacles;

public class ActivateServer {
    public static void main(String[] args) throws ClassNotFoundException, IOException {

        ActivateServer activateServer = new ActivateServer();
        activateServer.cmdArgs(args);

        ServerSocket s = new ServerSocket( SimpleServer.PORT);
        System.out.println("\u001B[1m\u001B[34m***** WELCOME TO ROBOT WORLDS! *****\u001B[0m");

        Obstacles.generateObstacles();
        while(true) {
            try {
                Socket socket = s.accept();
                System.out.println("Connection: " + socket);

                Runnable r = new SimpleServer(socket);
                Thread task = new Thread(r);
                task.start();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void cmdArgs(String[] args) throws IOException {
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
            obstacles.add(new SquareObstacle(x, y));

        }
    }
}
