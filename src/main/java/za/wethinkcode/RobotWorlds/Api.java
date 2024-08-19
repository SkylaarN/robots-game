package za.wethinkcode.RobotWorlds;

import io.javalin.Javalin;
import za.wethinkcode.RobotWorlds.Database.DbConnect;
import za.wethinkcode.RobotWorlds.commands.RestoreCommand;
import za.wethinkcode.RobotWorlds.worldLogic.Robot;
import za.wethinkcode.RobotWorlds.worldLogic.SquareObstacle;

import java.io.InputStream;
import java.util.List;

import static za.wethinkcode.RobotWorlds.ActivateServer.getRobotToRestore;

public class Api {


    public Javalin getServer() {
        return server;
    }

    private final Javalin server;
    private String s = "test";

    public Api(String world_name) {
        this.server = Javalin.create();
        this.server.get("/worlds/{world_name}", context -> {
            // Retrieve the world_name from the path parameter
            try {
                String worldName = context.pathParam("world_name");

                DbConnect dbConnect = new DbConnect();
                List<SquareObstacle> restoredObstacles = dbConnect.restoreObstacles(worldName);
                // Your logic here
                System.out.println("objects in world "+worldName+":");

                context.json(restoredObstacles);
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

    public Javalin start() {
        System.out.println("starting...");
        return this.server.start(3000);
    }

    public String setstring(String s){
        return this.s=s;
    }

    public Javalin stop() {
        System.out.println("stopping...");
        return this.server.stop();
    }

//    public static void main(String[] args) {
//        Api api = new Api("katlego");
//        api.start();
//        api.stop();
//    }
}
