package za.wethinkcode.RobotWorlds.commands;

import org.turtle.*;
import za.wethinkcode.RobotWorlds.Position;
import za.wethinkcode.RobotWorlds.Robot;

public class ForwardCommand extends Command {

    @Override
    public boolean execute(Robot target) {
        /**Function moves to robot forward by calling the updatePosition function with Argument
         * nrSteps as a positive integer
         *
         * @param target The Robot object
         * @return boolean if the program should continue
         */

        int nrSteps = Integer.parseInt(getArgument());
        if (target.updatePosition(nrSteps) == "UpdateResponse.SUCCESS"){
            String positionText = "[" + target.getPosition().getX() + "," + target.getPosition().getY() + "]";
            target.setStatus(target.getName() + " > " + positionText + " Moved forward by "+nrSteps+" steps.");
        }
        else if (target.updatePosition(nrSteps) == "UpdateResponse.FAILED_OBSTRUCTED"){
            String positionText = "[" + target.getPosition().getX() + "," + target.getPosition().getY() + "]";
            target.setStatus(target.getName() + " > " + positionText + " Sorry, I have encountered an obstacle.");
        }
        else {
            String positionText = "[" + target.getPosition().getX() + "," + target.getPosition().getY() + "]";
            target.setStatus(target.getName() + " > " + positionText + " Sorry, I cannot go outside my safe zone.");
        }
        return true;
    }


    public ForwardCommand(String argument) {
        super("forward", argument);
    }
}

