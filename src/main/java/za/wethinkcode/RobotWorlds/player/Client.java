package za.wethinkcode.RobotWorlds.player;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * The Client class represents a client that connects to a server for a toy robot application.
 * The client can send and receive messages, and manages its connection to the server.
 */
public class Client {
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String clientName;
    private String make;

    // ANSI color constants
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BOLD = "\u001B[1m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    /**
     * Constructs a new Client with the given socket.
     *
     * @param socket The socket connection to the server.
     */
    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Error initializing client: " + e.getMessage() + ANSI_RESET);
        }
    }

    /**
     * Listens for incoming messages from the server and prints them to the console.
     * Runs in a separate thread to allow for continuous listening.
     */
    public void listenForMsg() {
        new Thread(() -> {
            String msg;
            try {
                while (socket.isConnected()) {
                    msg = bufferedReader.readLine();

                    System.out.println(ANSI_CYAN + msg + ANSI_RESET);
                }
            } catch (IOException e) {
                System.out.println(ANSI_RED + "Error while trying to get message from server: " + e.getMessage() + ANSI_RESET);
            }
        }).start();
    }

    /**
     * Sends messages to the server. Continuously reads input from the console and sends it
     * to the server as long as the socket is open.
     */
    public void sendMessage() {
        try {
            Scanner scanner = new Scanner(System.in);

            String name="", command="", arguments = "";


            while (!socket.isClosed()) {
                String msgToSend = scanner.nextLine();

                String[] msg = msgToSend.split(" ");

                switch (msg.length){
                    case 3:
                        name = msg[0];
                        command = msg[1];
                        arguments = "\""+msg[2]+"\"";
                        break;
                    case 2:
                        command = msg[0];
                        arguments = "\""+msg[1]+"\"";
                        break;
                    default:
                        command = msgToSend;
                        arguments = "";
                        break;
                }

                String request = "{" +
                        "  \"robot\": \""+name+"\"," +
                        "  \"command\": \""+command+"\"," +
                        "  \"arguments\": ["+arguments+"]" +
                        "}";
                bufferedWriter.write(request);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Error sending message to server: " + e.getMessage() + ANSI_RESET);
        }
    }



    /**
     * The main method to run the Client. Prompts the user for a make and username, and
     * connects to the server. Listens for and sends messages based on user input.
     *
     * @param args Command-line arguments.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ANSI_BOLD + ANSI_PURPLE + "***** WELCOME TO ROBOT WORLDS! *****" + ANSI_RESET);
        System.out.println(ANSI_BOLD + ANSI_PURPLE + "Here is a list of our robots make:\n" +
                "1. Cowboy\n" +
                "2. Sniper\n" +
                "3. Assassin\n" +
                "4. Priest\n" + ANSI_RESET);

        System.out.println(ANSI_BOLD + ANSI_PURPLE + "Enter make from above and your desired username (eg. 'Sniper Hal'): " + ANSI_RESET);

        System.out.println(ANSI_BOLD + ANSI_PURPLE + "Type 'Launch' to place your robot in the world." + ANSI_RESET);

        Socket socket1 = new Socket("localhost", 5000);
        Client client = new Client(socket1);
        if (socket1.isConnected()) {
            client.listenForMsg();
            client.sendMessage();
        } else {
            System.out.println(ANSI_RED + "Failed to connect to server." + ANSI_RESET);
        }

    }

}
