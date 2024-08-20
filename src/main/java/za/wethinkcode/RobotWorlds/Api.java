package za.wethinkcode.RobotWorlds;

import io.javalin.Javalin;
import za.wethinkcode.RobotWorlds.Database.DbConnect;
import za.wethinkcode.RobotWorlds.commands.RestoreCommand;
import za.wethinkcode.RobotWorlds.worldLogic.Robot;
import za.wethinkcode.RobotWorlds.worldLogic.SimpleServer;
import za.wethinkcode.RobotWorlds.worldLogic.SquareObstacle;


import java.io.*;
import java.net.Socket;
import java.util.List;

import static za.wethinkcode.RobotWorlds.ActivateServer.getRobotToRestore;
import static za.wethinkcode.RobotWorlds.Client.*;

public class Api {


    public Javalin getServer() {
        return server;
    }

    private final Javalin server;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String s = "test";

//    public Api() {
//        server = Javalin.create();
//
//        this.Launch_robot();
//    }
    public Api(String start_server) {

        server = Javalin.create();
        this.Launch_robot("");

//        this.restore_world(this.server);

    }

    public void restore_world(Javalin server){
        server.get("/worlds/{world_name}", context -> {
            // Retrieve the world_name from the path parameter
            try {
                String worldName = context.pathParam("world_name");

                DbConnect dbConnect = new DbConnect();
                List<SquareObstacle> restoredObstacles = dbConnect.restoreObstacles(worldName);
                // Your logic here
                System.out.println("objects in world "+worldName+":\n");

                context.json(restoredObstacles+"\n");
                Robot robot = getRobotToRestore(" ");

                RestoreCommand restoreCommand = new RestoreCommand(worldName);
                restoreCommand.execute(robot);

                System.out.println(robot.getStatus());
            } catch (Exception e) {
                e.printStackTrace();  // Logs the exception to the server console
                context.status(500).result("Server Error: " + e.getMessage());
            }
        });
    }

    public void Launch_robot(String mss) {
        System.out.println("ye ye ye");
        server.post("/{robot_name}/launch/{robot_type}", context -> {
            System.out.println("ye ye ye1");

            String robotName = context.pathParam("robot_name");
            String command = "launch";
            String robortType = context.pathParam("robot_type");

            String msg = robotName+" "+command+" "+robortType;


            Socket socket1 = new Socket("localhost", 8000);
            Client client = new Client(socket1);

            BufferedReader bufferedReader ;
            String msgToSend = msg;

//            System.out.println("here is the message"+msgToSend);
            while (socket1.isConnected()) {

                if (msgToSend == null){
//                    bufferedReader.close();
//                    bufferedWriter.close();
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

//                System.out.println( msgToSend );




//                if (socket1.isConnected()) {
//                System.out.println("yeah");
//                client.listenForMsg(command);
////                String response =
//                System.out.println("111111111111111111111");
////                System.out.println(response);
//                client.sendMessage(msg);


//                context.result("Endpoint works\n" +  msgToSend + "\n");
//                client.listenForMsg(null);
                System.out.println("end---------------------------------------------------------------------");

            }
//            String msgToSend1 = client.msgToSend;
//            System.out.println("here"+msgToSend1);

//            String out = client.list.getFirst();
//            String out = SimpleServer.reply;

//
//            context.result(("endpoint works\n"+out+"\n"));


        });
//
    }

//    public void Launch_robot(){
//        server.post("/{robot_name}/launch/{robot_type}", context -> {
//            String robotName = context.pathParam("robot_name");
//                String command = "launch";
//                String robortType = context.pathParam("robot_type");
//
//                String msg = robotName+" "+command+" "+robortType;
//
//            Socket socket1 = new Socket("localhost", 8000);
//            Client client = new Client(socket1);
//
//            client.sendMessage(msg);
//            client.sendMessage();
//
//        });
//    }


    public Javalin start(int port) {
        System.out.println("starting...");
        return this.server.start(port);
    }

//    public String setstring(String s){
//        return this.s=s;
//    }

    public Javalin stop() {
        System.out.println("stopping...");
        return this.server.stop();
    }

}
