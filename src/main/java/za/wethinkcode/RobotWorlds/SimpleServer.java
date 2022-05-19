package za.wethinkcode.RobotWorlds;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SimpleServer implements Runnable {
    public static ArrayList<String> listRobots = new ArrayList<String>();
    public static final int PORT = 5000;
    private final BufferedReader in;
    private final PrintStream out;
    private final String clientMachine;



    public SimpleServer(Socket socket) throws IOException {
        clientMachine = socket.getInetAddress().getHostName();
        //System.out.println("Connection from " + clientMachine);
        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        //System.out.println("Waiting for client...");
    }

    public void addRobots(String name) {
        listRobots.add(name);
    }

    public void run() {

        try {

            while (true){
                String messageFromClient = null;
                while((messageFromClient = in.readLine()) != null){
                    System.out.println(messageFromClient);

                    JSONObject obj = new JSONObject(messageFromClient);

                    //String[] args = messageFromClient.trim().split(" ", 2);
                    System.out.println(obj.getString("name") + " ---- " + obj.getString("command") +
                            " " + obj.get("arguments"));
                            
                    if (!listRobots.contains(obj.getString("name"))) {
                        addRobots(obj.getString("name"));
                    }

                    //System.out.println("Message \"" + messageFromClient + "\" from " + clientMachine);
                    String reply = doRobot(obj.getString("name"), obj.getString("command"), obj.getJSONArray("arguments"));
                    //out.println("Thanks for this message: " + messageFromClient);
                    out.println(reply);
                }

            }
        } catch(IOException ex) {
            System.out.println("Shutting down single client server");
        } finally {
            closeQuietly();
        }
    }

    private void closeQuietly() {
        try { in.close(); out.close();
        } catch(IOException ex) {}
    }



    String doRobot(String name, String instructions, JSONArray arguments){
        Robot userRobot = Players.getRobot(name);
        userRobot.handleCommand(instructions, arguments);
        return userRobot.getReply().toString();
    }


}
