package za.wethinkcode.RobotWorlds.commands;

import za.wethinkcode.RobotWorlds.Robot;

import java.util.concurrent.TimeUnit;

public class RepairCommand extends Command{

    public boolean execute(Robot target){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        target.setStatus("repaired");
        target.setHealth(5);
        return true;
    }

    public RepairCommand() {
        super("state");
    }
}
