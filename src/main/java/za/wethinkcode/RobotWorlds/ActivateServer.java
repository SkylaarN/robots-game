package za.wethinkcode.RobotWorlds;

import org.apache.commons.cli.*;
import za.wethinkcode.RobotWorlds.configuration.Configuration;
import za.wethinkcode.RobotWorlds.worldLogic.Obstacles;
import za.wethinkcode.RobotWorlds.worldLogic.SimpleServer;
import za.wethinkcode.RobotWorlds.worldLogic.SquareObstacle;

import org.apache.commons.cli.Options;
import za.wethinkcode.RobotWorlds.commands.RestoreCommand;
import za.wethinkcode.RobotWorlds.commands.SaveCommand;
import za.wethinkcode.RobotWorlds.worldLogic.Obstacles;
import za.wethinkcode.RobotWorlds.worldLogic.Players;
import za.wethinkcode.RobotWorlds.worldLogic.Robot;
import za.wethinkcode.RobotWorlds.worldLogic.SimpleServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static za.wethinkcode.RobotWorlds.worldLogic.Obstacles.obstacles;

public class ActivateServer {

    public static ArrayList<Socket> listRobotsSockets = new ArrayList<Socket>();

    public static void main(String[] args) throws ClassNotFoundException, IOException {

        ServerSocket serverSocket = new ServerSocket(SimpleServer.PORT);
        System.out.println("\u001B[1m\u001B[34m***** WELCOME TO ROBOT WORLDS! *****\u001B[0m");
        ActivateServer activateServer = new ActivateServer();
        activateServer.cmdArgs(args);
        // Start a thread to handle client connections
        new Thread(() -> {
//            Obstacles.generateObstacles();
//            System.out.println(Obstacles.getObstacles().toString());
            while (!serverSocket.isClosed()) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("Connection: " + socket);
                    listRobotsSockets.add(socket);
                    Runnable r = new SimpleServer(socket);
                    Thread task = new Thread(r);
                    task.start();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

        // Listen for console input in the main thread
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter command: ");
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("quit")){
//                ArrayList<Robot> robots = Players.;
//                System.out.println();
//                robots.clear();
                for (Socket socket : listRobotsSockets){
                    socket.close();
                }
                serverSocket.close();
                break;
            }
            if (command.equalsIgnoreCase("save")) {
                // Execute SaveCommand when "save" is entered
                System.out.print("Enter world name to save: ");
                String worldName = scanner.nextLine();

                // Assuming you have a default Robot to save the world data
                Robot robot = getRobotToSave();

                SaveCommand saveCommand = new SaveCommand(worldName);
                saveCommand.execute(robot);

                System.out.println(robot.getStatus());
            }else if (command.equalsIgnoreCase("restore")) {
                System.out.print("Enter world name to restore: ");
                String worldName = scanner.nextLine();

                Robot robot = getRobotToRestore(" ");

                RestoreCommand restoreCommand = new RestoreCommand(worldName);
                restoreCommand.execute(robot);

                System.out.println(robot.getStatus());
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
            System.out.println(config.getWorldSize().getX()+";"+config.getWorldSize().getY());
            config.setWorldSize(widthHeight,widthHeight);
            System.out.println(config.getWorldSize().getX()+";"+config.getWorldSize().getY() + "new size");

        }
        if (cmd.hasOption("obstacle")) {
            String obstacleSize = cmd.getOptionValue("obstacle");
            String[] obstacleSizeArray = obstacleSize.split(",");
            int x = Integer.parseInt(obstacleSizeArray[0]);
            int y = Integer.parseInt(obstacleSizeArray[1]);
            obstacles.add(new SquareObstacle(x, y));

        }
    }

    private static Robot getRobotToSave() {
        // Retrieve or create the Robot whose world state you want to save
        return Players.getRobot("robot1");  // Replace with the actual robot name or retrieval logic
    }

    private static Robot getRobotToRestore(String robotName) {
        return new Robot(robotName);
    }
}
