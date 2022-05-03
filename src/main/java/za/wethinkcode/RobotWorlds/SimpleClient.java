package za.wethinkcode.RobotWorlds;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class SimpleClient {


    public static void main(String args[]) {
        String input = "";
        String name = getInput("Please Enter your robots name.");
        while(!input.equalsIgnoreCase("quit")){
        try (
                Socket socket = new Socket("localhost", 5000);
                PrintStream out = new PrintStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
        ) {

            input = getInput(name + " > Please Enter your Message.");
            out.println(name + " " + input);
            out.flush();

//            String messageFromServer = in.readLine();
//            System.out.println("Response: " + messageFromServer);


        } catch (IOException e) {
            e.printStackTrace();
        }
            System.out.println(input);
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
            System.out.println(prompt);
            input = scanner.nextLine();
        }
        return input;
    }
}
