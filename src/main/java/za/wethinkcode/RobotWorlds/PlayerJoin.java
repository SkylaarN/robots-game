package za.wethinkcode.RobotWorlds;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.json.JSONArray;
import org.turtle.*;
import org.json.JSONObject;

public class PlayerJoin {


    public static void main(String[] args) {
        String serverIP;
        if(args.length != 0){
            serverIP = args[0];
        }
        else{
            serverIP = "localhost";
        }


        ResponseReader reader = new ResponseReader();
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

                obj.put("command", text[0]);
                obj.put("arguments", new JSONArray(text[1].split(" ")));


            }

            out.println(obj);
            out.flush();

            String messageFromServer = in.readLine();
            reader.handleReply(new JSONObject(messageFromServer), obj);




        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }

    private static String getInput(String prompt) {
        /**Function returns the user input
         *
         * @param prompt the String containing question for the user
         * @return String the answer the user gave to the prompt
         */
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);
        String input = scanner.nextLine();

        while (input.isBlank()) {
            input = scanner.nextLine();
        }
        return input;
    }
}
