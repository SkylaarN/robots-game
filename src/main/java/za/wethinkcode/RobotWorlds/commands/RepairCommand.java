package za.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.Robot;

import java.util.concurrent.TimeUnit;

public class RepairCommand extends Command{

    public boolean execute(Robot target){
//        try {
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        target.setStatus("repaired");
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
        super("state");
    }
}
