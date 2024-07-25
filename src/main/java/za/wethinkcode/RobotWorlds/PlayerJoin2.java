package za.wethinkcode.RobotWorlds;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.turtle.*;
public class PlayerJoin2 {


    private static final Logger log = LoggerFactory.getLogger(PlayerJoin2.class);

    /**
     * Sends request to the server through the established network
     * @param args a string array with the IP Address of the server
     */
    public static void main(String[] args) {

        String serverIP;
        if(args.length != 0){
            serverIP = args[0];
        }
        else{
            serverIP = "localhost";
        }

//        ResponseReader reader = new ResponseReader();
        String input = "";
        String name = getInput("Please Enter your robots name.");
        while(!input.equalsIgnoreCase("quit")){
            try (
                    Socket socket = new Socket(serverIP, 5000);
                    PrintStream out = new PrintStream(socket.getOutputStream());
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            socket.getInputStream()));
            ) {



                JSONObject obj = new JSONObject();

                obj.put("robot", name);

                input = getInput(name + " > Please Enter your Message.");

                String[] text = input.toLowerCase().trim().split(" ", 2);

                if (text.length == 1) {
                    obj.put("command", text[0]);
                    obj.put("arguments",new JSONArray());

                } else {
                    //this is when the txt.length  1
                    obj.put("command", text[0]);
                    obj.put("arguments", new JSONArray(text[1].split(" ")));


                }


                out.println(obj);
                out.flush();

                //

                String command = obj.getString("command");
//                String name1 = obj.getString("name");
//                JSONArray arguments = (JSONArray) new JSONObject().get("arguments");
//                 arguments = new JSONObject().get("arguments");

                Robot robot = new Robot(name);

//                robot.handleCommand(command, arguments);
                String messageFromServer = in.readLine();
                System.out.println(obj);



//                reader.handleReply(new JSONObject(messageFromServer), obj);




            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**Function returns the user input
     *
     * @param prompt the String containing question for the user
     * @return String the answer the user gave to the prompt
     */
    private static String getInput(String prompt) {

        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        String input = scanner.nextLine();

        while (input.isBlank()) {
            input = scanner.nextLine();
        }
        return input;
    }
}
