package za.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.Robot;

import java.util.concurrent.TimeUnit;

public class ReloadCommand extends Command{

    public boolean execute(Robot target){
//        try {
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        target.setStatus("reloaded");
        JSONObject reply = new JSONObject();
        JSONObject data = new JSONObject();
        reply.put("result", "OK");
        data.put("message", "Reloading");
        reply.put("data", data);
        target.setStatus(reply.toString());
        target.setStatusType("RELOAD");
        target.setBullets(8);
        return true;
    }

    public ReloadCommand() {
            super("state");
        }
}
