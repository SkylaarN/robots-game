package za.wethinkcode.RobotWorlds.server;
import za.wethinkcode.RobotWorlds.api.Api;
import za.wethinkcode.RobotWorlds.domain.Controller;
import za.wethinkcode.RobotWorlds.domain.world.Obstacles;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.Level;

public class ActivateServer {

    public static ArrayList<Socket> listRobotsSockets = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ((Logger) LoggerFactory.getLogger("org.eclipse.jetty")).setLevel(Level.WARN);
        ((Logger) LoggerFactory.getLogger("io.javalin")).setLevel(Level.WARN);

        ServerSocket serverSocket = new ServerSocket(SimpleServer.PORT);
        System.out.println("\u001B[1m\u001B[34m***** WELCOME TO ROBOT WORLDS! *****\u001B[0m");

        Api api = new Api();


        api.start(3000);
        new Thread(() -> {
            Obstacles.generateObstacles();
            while (!serverSocket.isClosed()) {

                try {
                    Socket socket = serverSocket.accept();
                    listRobotsSockets.add(socket);
                    Runnable r = new SimpleServer(socket);
                    Thread task = new Thread(r);
                    task.start();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        while (!serverSocket.isClosed()) {
            System.out.print("Enter command: ");
            String command = scanner.nextLine();
            Controller.executeServerCommand(command, serverSocket);


        }
    }




}
