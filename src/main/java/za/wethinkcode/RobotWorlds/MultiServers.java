package za.wethinkcode.RobotWorlds;

import java.net.*;
import java.io.*;

public class MultiServers {
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        ServerSocket s = new ServerSocket( SimpleServer.PORT);
        System.out.println("Server running & waiting for client connections.");
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
}
