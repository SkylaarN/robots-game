package za.wethinkcode.RobotWorlds.server;

import org.json.JSONArray;
import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.api.Api;
import za.wethinkcode.RobotWorlds.domain.Controller;

import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class SimpleServer implements Runnable {
    public static String reply;
    public static ArrayList<String> listRobots = new ArrayList<>();
    public static int PORT = 8000;
    private final BufferedReader in;
    private final PrintStream out;
    private final Socket clientSocket;



    public SimpleServer(Socket socket) throws IOException {
        clientSocket = socket;

        out = new PrintStream(socket.getOutputStream());

        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
    }



    /**
     * Initiate the server
     */
    public void run() {
//        System.out.println("simple server");
//        Api api = new Api("start");
//
//
//        api.start(3000);


        try {

            while (clientSocket.isConnected()){
                String messageFromClient=in.readLine();

                while(messageFromClient != null){


                    JSONObject obj = new JSONObject(messageFromClient);

                    JSONArray arguments = obj.optJSONArray("arguments");

                    String name = obj.getString("robot");
                    String command = obj.getString("command");

                    if (!listRobots.contains(obj.getString("robot")) & command.equalsIgnoreCase("launch")) {
                        addRobots(obj.getString("robot"));
                        reply = Controller.executeClientCommand(name, command, arguments);

//                        api.Launch_robot(reply);
                        out.println(reply);

                    }else if (listRobots.contains(name)) {
                        String reply = Controller.executeClientCommand(name, command, arguments);
                        out.println(reply);
                    }else {
                        out.println("\u001B[31mPlease launch the robot\u001B[0m");
                    }
                    messageFromClient = in.readLine();
                }
                break;


            }
        } catch(IOException ex) {
            System.out.println("Shutting down single client server");
        } finally {
            closeQuietly();
        }
    }

    /**
     * Add new loaded robots
     * @param name name of the robot
     */
    public void addRobots(String name) {
        listRobots.add(name);
        Players.robotsSSSS.put(name, clientSocket);

    }

    /**
     * Shut down the server
     */
    private void closeQuietly() {
        try { in.close(); out.close();
        } catch(IOException ex) {
            System.out.println("Error trying to close the server");
        }
    }





}
