package za.wethinkcode.RobotWorlds.commands;

import za.wethinkcode.RobotWorlds.Robot;

public class StateCommand extends Command{
    public boolean execute(Robot target){
        target.setStatus("Shield: " + target.getHealth() + "  Bullets: " + target.getBullets());
        return true;
    }



    public StateCommand() {
        super("state");
    }
}
