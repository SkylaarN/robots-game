package za.wethinkcode.RobotWorlds.worldLogic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class SimpleServer implements Runnable {
    public static ArrayList<String> listRobots = new ArrayList<String>();
    public static final int PORT = 5000;
    private final BufferedReader in;
    private final PrintStream out;
    private final String clientMachine;


    public SimpleServer(Socket socket) throws IOException {
        clientMachine = socket.getInetAddress().getHostName();
        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
    }

    /**
     * Add new loaded robots
     * @param name name of the robot
     */
    public void addRobots(String name) {
        listRobots.add(name);
    }

    /**
     * Initiate the server
     */
    Robot robot = new Robot("nathi");
    public void run() {

        try {

            while (true){
                String messageFromClient = null;
                while((messageFromClient = in.readLine()) != null){

                    JSONObject obj = new JSONObject(messageFromClient);

                    JSONArray arguments = obj.optJSONArray("arguments");

                    String name = obj.getString("robot");
                    String command = obj.getString("command");

                    if (!listRobots.contains(obj.getString("robot"))) {
                        addRobots(obj.getString("robot"));
                    }

                    String reply = doRobot(name, command, arguments);

                    out.println(reply);

                }

            }
        } catch(IOException ex) {
            System.out.println("Shutting down single client server");
        } finally {
            closeQuietly();
        }
    }

    /**
     * Shut down the server
     */
    private void closeQuietly() {
        try { in.close(); out.close();
        } catch(IOException ex) {}
    }


    /**
     * Instructs the robot what to do based on the commands given by the player
     * @param name is the name of the robot
     * @param instructions instruction from the player
     * @param arguments arguments of the player commands
     * @return reply from the server
     */
    String doRobot(String name, String instructions, JSONArray arguments){
        Robot userRobot = Players.getRobot(name);
        userRobot.handleCommand(instructions, arguments);
        return userRobot.getReply().toString();
    }


}
