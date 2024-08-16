package za.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.worldLogic.Robot;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReloadCommand extends Command{

    public boolean execute(Robot target){
        JSONObject reply = new JSONObject();
        JSONObject data = new JSONObject();

        reply.put("result", "OK");
        data.put("message", "Reloading");
        reply.put("data", data);

        target.setStatus(reply.toString());
        target.setStatusType("RELOAD");

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable resetBullets = () -> {
            target.setBullets(target.getVisibility());
        };
        scheduler.schedule(resetBullets, 15, TimeUnit.SECONDS);
        scheduler.schedule(() -> {
            try {
                if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
            }
        }, 17, TimeUnit.SECONDS);


        return true;
    }


    public ReloadCommand() {
            super("reload");
        }
}
