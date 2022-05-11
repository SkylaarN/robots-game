package za.wethinkcode.RobotWorlds.commands;

import za.wethinkcode.RobotWorlds.Robot;

import java.util.concurrent.TimeUnit;

public class ReloadCommand extends Command{

    public boolean execute(Robot target){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        target.setStatus("reloaded");
        target.setBullets(8);
        return true;
    }

    public ReloadCommand() {
            super("state");
        }
}
