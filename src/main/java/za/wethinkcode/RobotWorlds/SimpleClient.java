package za.wethinkcode.RobotWorlds;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class SimpleClient {

    private static Robot robot = new Robot();

    public static void main(String args[]) {
        String input = "";
        while(!input.equalsIgnoreCase("quit")){
        try (
                Socket socket = new Socket("localhost", 5000);
                PrintStream out = new PrintStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
        ) {
            input = getInput("Please Enter your Message.");
            robot.handleCommand(input);
            out.println(input);
            out.flush();
            String messageFromServer = in.readLine();
            System.out.println("Response: " + messageFromServer);


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
