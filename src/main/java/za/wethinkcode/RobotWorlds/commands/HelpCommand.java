package za.wethinkcode.RobotWorlds.commands;

import za.wethinkcode.RobotWorlds.Robot;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help");
    }

    @Override
    public boolean execute(Robot target) {
        target.setStatus("< Robot Commands >~" +
                "OFF  - Shut down robot~" +
                "HELP - provide information about commands~" +
                "FORWARD - move forward by specified number of steps, e.g. 'forward 10'~" +
                "BACK - move backward by specific number of steps, e.g. 'back 10'~" +
                "RIGHT - turn right by 90 degrees~" +
                "LEFT - turn left by 90 degrees~" +
                "ROBOTS - List all robots~" +
                "SHOOT - Shoot robot~" +
                "LOOK - See everything in its line of sight~");
        return true;
    }
}
