package za.wethinkcode.RobotWorlds;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import org.turtle.*;
import org.json.JSONObject;

public class SimpleClient {


    public static void main(String args[]) {
        TurtleRobot tRobot = new TurtleRobot();
        String input = "";
        String name = getInput("Please Enter your robots name.");
        while(!input.equalsIgnoreCase("quit")){
        try (
                Socket socket = new Socket("localhost", 5000);
                PrintStream out = new PrintStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
        ) {
            JSONObject obj = new JSONObject();
            obj.put("name", name);

            input = getInput(name + " > Please Enter your Message.");

            String[] text = input.toLowerCase().trim().split(" ", 2);

            if (text.length == 1) {
                obj.put("command", text[0]);
                obj.put("arguments",new ArrayList<>());

            } else {

                obj.put("command", text[0]);
                obj.put("arguments", text[1].split(" "));


            }

            System.out.println(obj);
            out.println(obj);
            out.flush();

            String messageFromServer = in.readLine();
            System.out.println(messageFromServer);
            tRobot.handleReply(new JSONObject(messageFromServer));




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
