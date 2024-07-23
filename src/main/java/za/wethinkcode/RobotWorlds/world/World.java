package za.wethinkcode.RobotWorlds.world;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;



import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class World implements Runnable {
    public static ArrayList<World> clientHandlers = new ArrayList<>();
    @JsonIgnore
    public Socket socket;

    @JsonIgnore
    public PrintWriter messageToClient;
    @JsonIgnore
    public BufferedReader messageToServer;


    public World(Socket socket, int worldSizeX, int worldSizeY) {

        try {
            this.socket = socket;
            this.messageToClient = new PrintWriter(socket.getOutputStream(), true);
            this.messageToServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts the World instance to a pretty-printed JSON string.
     *
     * @return JSON representation of the World.
     * @throws Exception If an error occurs during JSON processing.
     */
    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT,true);
        return mapper.writeValueAsString(this);
    }



    @Override
    public void run() {

        String msgFromClient;
        while (!socket.isClosed()) {
            try {
                msgFromClient = this.messageToServer.readLine();
                System.out.println(msgFromClient);

                if (msgFromClient == null) {
                    break;
                }

                String whatToDo = msgFromClient;
                messageToClient.println(whatToDo);
                messageToClient.flush();
                broadcastMessage(whatToDo);


            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void broadcastMessage(String msg) {

        try {



            for (World client : clientHandlers) {
                client.messageToClient.println(msg);
                client.messageToClient.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("\u001B[1m\u001B[34m***** WELCOME TO ROBOT WORLDS! *****\u001B[0m");
            System.out.println("\u001B[1m\u001B[34m***** CREATING WORLD... *****\u001B[0m");
            System.out.print("\u001B[1m\u001B[33mEnter the world width: \u001B[0m");

            int worldSizeX = Integer.parseInt(reader.readLine());
            System.out.print("\u001B[1m\u001B[33mEnter the world height: \u001B[0m");
            int worldSizeY = Integer.parseInt(reader.readLine());

            System.out.println("\u001B[1m\u001B[32mThe world dimensions are set to Width: " + worldSizeX + " and Height: " + worldSizeY + ".\u001B[0m");

            Properties properties = new Properties();
            properties.setProperty("world.size.x", String.valueOf(worldSizeX));
            properties.setProperty("world.size.y", String.valueOf(worldSizeY));
            properties.store(new FileOutputStream("src/main/java/za/wethinkcode/RobotWorlds/config/config.json"), null);

            List<Socket> clientSockets = new ArrayList<>();

            try (ServerSocket serverSocket1 = new ServerSocket(1234)) {
                new Thread(() -> {
                    String serverCommand;
                    try {
                        while (!serverSocket1.isClosed()) {
                            serverCommand = reader.readLine();
                            if (serverCommand.equalsIgnoreCase("off") ||
                                    serverCommand.equalsIgnoreCase("quit")) {
                                System.out.println("\u001B[1m\u001B[31mShutting down\u001B[0m");
                                for (Socket allSockets : clientSockets) {
                                    allSockets.close();
                                }
                                serverSocket1.close();
                                break;
                            }
                            System.out.println(serverCommand);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

                while (!serverSocket1.isClosed()) {
                    Socket socket = serverSocket1.accept();

                    clientSockets.add(socket);

                    World world = new World(socket, worldSizeX, worldSizeY);
                    System.out.println(world.toJson());

                    Thread thread = new Thread(world);
                    thread.start();
                }
            } catch (IOException e) {
                System.out.println("\u001B[1m\u001B[31mError creating server socket.\u001B[0m");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("\u001B[1m\u001B[31mError reading input.\u001B[0m");
            e.printStackTrace();
        }

    }

}

