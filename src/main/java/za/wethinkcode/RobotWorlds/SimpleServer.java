package za.wethinkcode.RobotWorlds;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class SimpleServer implements Runnable {

    public static final int PORT = 5000;
    private final BufferedReader in;
    private final PrintStream out;
    private final String clientMachine;
    private HashMap<String, Robot> robots = new HashMap();


    public SimpleServer(Socket socket) throws IOException {
        clientMachine = socket.getInetAddress().getHostName();
        System.out.println("Connection from " + clientMachine);
        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        System.out.println("Waiting for client...");
    }

    public void run() {
        try {
            String messageFromClient;

            while((messageFromClient = in.readLine()) != null){
                String[] args = messageFromClient.trim().split(" ", 2);
                checkRobot(args[0]);
                doRobot(args[0], args[1]);
                //System.out.println("Message \"" + messageFromClient + "\" from " + clientMachine);
                out.println("Thanks for this message: " + messageFromClient);

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

    void checkRobot(String name){
        if(!robots.containsKey(name)){
            robots.put(name, new Robot(name));
        }
    }

    void doRobot(String name, String instructions){
        Robot userRobot = robots.get(name);
        userRobot.handleCommand(instructions);
    }
}
