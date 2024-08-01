package za.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.worldLogic.Robot;

public class ErrorHandling extends Command {

    public ErrorHandling(String command){
        super("Unsupported command :", command);
    }


    @Override
    public boolean execute(Robot target) {
        //If i type an invalid command
        //I should get ERROR


        JSONObject reply = new JSONObject();
        JSONObject data = new JSONObject();

        reply.put("result","ERROR");
        data.put("message", "Unsupported command");
        reply.put("data",data);
        target.setStatus(reply.toString());
        return true;
    }
}
