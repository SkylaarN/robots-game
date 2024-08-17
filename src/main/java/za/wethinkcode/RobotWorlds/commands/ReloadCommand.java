package za.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.worldLogic.Robot;

public class ReloadCommand extends Command{

    public boolean execute(Robot target){
        JSONObject reply = new JSONObject();
        JSONObject data = new JSONObject();

        reply.put("result", "OK");
        data.put("message", "Reloading");
        reply.put("data", data);

        target.setStatus(reply.toString());
        target.setStatusType("RELOAD");

        target.setBullets(target.getVisibility());

        return true;
    }


    public ReloadCommand() {
            super();
        }
}
