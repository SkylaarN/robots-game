package za.wethinkcode.RobotWorlds.domain;

import org.json.JSONArray;
import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.domain.ClientCommands.ErrorHandling;
import za.wethinkcode.RobotWorlds.domain.serverCommands.ServerCommands;
import za.wethinkcode.RobotWorlds.domain.world.Robot;
import za.wethinkcode.RobotWorlds.server.Players;

import java.io.IOException;
import java.net.ServerSocket;

public class Controller {
    public Controller(){

    }

    /**
     * Instructs the robot what to do based on the commands given by the player
     * @param name is the name of the robot
     * @param instructions instruction from the player
     * @param arguments arguments of the player commands
     * @return reply from the server
     */

    public static String executeClientCommand(String name, String instructions, JSONArray arguments){
        if (name.equalsIgnoreCase("error")){
            JSONObject reply = new JSONObject();
            JSONObject data = new JSONObject();
            reply.put("result", "error");
            data.put("message", "Please launch the Robot");
            reply.put("data", data);

            return reply.toString();
        } else {
            Robot userRobot = Players.getRobot(name);
            userRobot.handleCommand(instructions, arguments);
            return userRobot.getReply().toString();
        }
    }

    public static void executeServerCommand(String command, ServerSocket serverSocket) throws IOException {
        ServerCommands serverCommands = new ServerCommands();
        serverCommands.executeCommand(command, serverSocket);
    }
}
