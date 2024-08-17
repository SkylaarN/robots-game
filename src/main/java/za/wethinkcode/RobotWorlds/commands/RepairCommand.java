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
        Repairing(target);
        return true;
    }

    public void Repairing(Robot target){

        int visibility = target.getVisibility();

        switch (visibility){
            case 5:
                target.setHealth(1);
            case 4:
                target.setHealth(2);
            case 3:
                target.setHealth(3);
            case 2:
                target.setHealth(4);
            case 1:
                target.setHealth(5);
        }
    }



    public RepairCommand() {
        super();
    }
}
