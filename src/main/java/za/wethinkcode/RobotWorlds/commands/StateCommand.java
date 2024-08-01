package za.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.worldLogic.Robot;

public class StateCommand extends Command{
    public boolean execute(Robot target){
        JSONObject reply = new JSONObject();

        reply.put("result", "OK");
        target.setStatus(reply.toString());
        return true;
    }



    public StateCommand() {
        super("state");
    }
}
