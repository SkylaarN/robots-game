package za.wethinkcode.RobotWorlds;

import za.wethinkcode.RobotWorlds.configuration.Configuration;
import za.wethinkcode.RobotWorlds.worldLogic.Obstacles;
import za.wethinkcode.RobotWorlds.worldLogic.Position;
import za.wethinkcode.RobotWorlds.worldLogic.SimpleServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ActivateServer {
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        // Default port and configuration
        int port = SimpleServer.PORT;
        Configuration config = new Configuration();

        // Parse command line arguments
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-p":
                    port = Integer.parseInt(args[++i]);
                    break;
                case "-s":
                    int size = Integer.parseInt(args[++i]);
                    config.setWorldSize(size, size);
                    break;
                case "-o":
                    String[] coordinates = args[++i].split(",");
                    int x = Integer.parseInt(coordinates[0]);
                    int y = Integer.parseInt(coordinates[1]);
                    config.addObstacle(new Position(x, y));
                    break;
                default:
                    System.out.println("Unknown argument: " + args[i]);
            }
        }

        // Start the server on the specified port
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("\u001B[1m\u001B[34m***** WELCOME TO ROBOT WORLDS! *****\u001B[0m");

        // Start a thread to handle client connections
        new Thread(() -> {
            Obstacles.generateObstacles();
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("Connection: " + socket);
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

            if (command.equalsIgnoreCase("quit")) {
                System.out.println("Shutting down server...");
                serverSocket.close();
                break;
            }
            // Add additional console commands handling here
        }
    }
}
