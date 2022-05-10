package za.wethinkcode.RobotWorlds.commands;

import za.wethinkcode.RobotWorlds.Robot;

public class DumpCommand extends Command{
    public boolean execute(Robot target){
        return true;
    }



    public DumpCommand() {
        super("dump");
    }
}
