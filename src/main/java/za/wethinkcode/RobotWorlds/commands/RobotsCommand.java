package za.wethinkcode.RobotWorlds.commands;

import za.wethinkcode.RobotWorlds.Robot;

public class RobotsCommand extends Command{
    public boolean execute(Robot target){

        return true;
    }



    public RobotsCommand() {
        super("robots");
    }
}
