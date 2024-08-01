package za.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.worldLogic.Robot;

public class RepairCommand extends Command{

    public boolean execute(Robot target){
        JSONObject reply = new JSONObject();
        JSONObject data = new JSONObject();

        reply.put("result", "OK");
        data.put("message", "Repairing");
        reply.put("data", data);

        target.setStatus(reply.toString());
        target.setStatusType("REPAIR");

        target.setHealth(5);
        return true;
    }

    public RepairCommand() {
        super("repair");
    }
}
