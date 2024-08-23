package za.wethinkcode.RobotWorlds.api;

import io.javalin.Javalin;
import za.wethinkcode.RobotWorlds.Database.DbConnect;
import za.wethinkcode.RobotWorlds.client.Client;
import za.wethinkcode.RobotWorlds.domain.serverCommands.RestoreCommand;
import za.wethinkcode.RobotWorlds.domain.world.Robot;
import za.wethinkcode.RobotWorlds.domain.world.SquareObstacle;


import java.io.*;
import java.net.Socket;
import java.util.List;

//import static za.wethinkcode.RobotWorlds.server.ActivateServer.getRobotToRestore;

public class Api {


    public Javalin getServer() {
        return server;
    }

    private final Javalin server;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String s = "test";

    public Api() {

        server = Javalin.create();
        this.Launch_robot("");
        this.restore_world();



    }

    public void restore_world(){
        server.get("/worlds/{world_name}", context -> {

            try {
                String worldName = context.pathParam("world_name");

                DbConnect dbConnect = new DbConnect();
                List<SquareObstacle> restoredObstacles = dbConnect.restoreObstacles(worldName);
                // Your logic here
                System.out.println("objects in world "+worldName+":\n");

                context.json(restoredObstacles+"\n");


                RestoreCommand restoreCommand = new RestoreCommand(worldName);

            } catch (Exception e) {
                e.printStackTrace();  // Logs the exception to the server console
                context.status(500).result("Server Error: " + e.getMessage());
            }
        });
    }

    public void Launch_robot(String mss) {

        server.post("/{robot_name}/launch/{robot_type}", context -> {


            String robotName = context.pathParam("robot_name");
            String command = "launch";
            String robortType = context.pathParam("robot_type");

            String msg = robotName+" "+command+" "+robortType;


            Socket socket1 = new Socket("localhost", 8000);
            Client client = new Client(socket1);

            BufferedReader bufferedReader ;
            String msgToSend = msg;


            while (socket1.isConnected()) {

                if (msgToSend == null){
                    this.bufferedReader.close();
                    bufferedWriter.close();
                    socket1.close();

                    break;
                }
                String arguments = robortType;
                String request = "{" +
                        "  \"robot\": \""+robotName+"\"," +
                        "  \"command\": \""+command+"\"," +
                        "  \"arguments\": ["+arguments+"]" +
                        "}";

                this.bufferedWriter =new BufferedWriter(new OutputStreamWriter(socket1.getOutputStream()));

                this.bufferedReader = new BufferedReader(new InputStreamReader(socket1.getInputStream()));

                System.out.println("ex");

                System.out.println("before");

                System.out.println(request);
                System.out.println("before after");

//                bufferedWriter.newLine();
                bufferedWriter.write(request + "\n");
                bufferedWriter.flush();

                System.out.println("ex1");


                System.out.println("ex1");



                String msgTorev = this.bufferedReader.readLine();


                System.out.println("ex2");

                context.result("Endpoint works\n" +  msgTorev + "\n");
                System.out.println("-----------------------"+msgTorev);
                msgToSend=null;



            }
        });
    }



    public Javalin start(int port) {
        System.out.println("starting...");
        return this.server.start(port);
    }


    public Javalin stop() {
        System.out.println("stopping...");
        return this.server.stop();
    }

}
