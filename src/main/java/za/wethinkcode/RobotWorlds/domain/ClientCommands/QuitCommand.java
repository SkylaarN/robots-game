package za.wethinkcode.RobotWorlds.domain.ClientCommands;

import za.wethinkcode.RobotWorlds.domain.world.Robot;

public class QuitCommand extends Command{

    public QuitCommand() {
        super();
    }

    public boolean execute(Robot target) {
        target.setStatus("Shutting down...");
        return false;
    }

}
