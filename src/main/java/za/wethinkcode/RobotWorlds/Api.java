package za.wethinkcode.RobotWorlds;

import io.javalin.Javalin;
import za.wethinkcode.RobotWorlds.Database.DbConnect;
import za.wethinkcode.RobotWorlds.commands.RestoreCommand;
import za.wethinkcode.RobotWorlds.worldLogic.Robot;
import za.wethinkcode.RobotWorlds.worldLogic.SquareObstacle;

import java.net.Socket;
import java.util.List;

import static za.wethinkcode.RobotWorlds.ActivateServer.getRobotToRestore;

public class Api {


    public Javalin getServer() {
        return server;
    }

    private final Javalin server;
    private String s = "test";

    public Api() {
        server = Javalin.create();

        this.Launch_robot();
    }
    public Api(String start_server) {
        server = Javalin.create();

        this.restore_world(this.server);

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

    public void Launch_robot() {
        server.post("/{robot_name}/launch/{robot_type}", context -> {

            String robotName = context.pathParam("robot_name");
            String command = "launch";
            String robortType = context.pathParam("robot_type");

            String msg = robotName+" "+command+" "+robortType;




            Socket socket1 = new Socket("localhost", 8000);
            Client client = new Client(socket1);

            String received ;
            if (socket1.isConnected()) {
//                client.sendMessage(msg);
                client.listenForMsg();
                client.sendMessage(msg);

            }

            Robot robot = new Robot(robotName);

            context.result(("endpoint works\n"+robot.getStatus()));


        });

    }

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
