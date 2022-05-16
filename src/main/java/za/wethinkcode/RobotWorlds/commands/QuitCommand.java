package za.wethinkcode.RobotWorlds.commands;

import za.wethinkcode.RobotWorlds.Robot;

public class QuitCommand extends Command{

    public QuitCommand() {
        super("quit");
    }

    public boolean execute(Robot target) {
        target.setStatus("Shutting down...");
        return false;
    }

}
