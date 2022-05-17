package za.wethinkcode.RobotWorlds.commands;
import org.turtle.*;
import za.wethinkcode.RobotWorlds.Position;
import za.wethinkcode.RobotWorlds.Robot;


public class BackCommand extends Command {

    @Override
    public boolean execute(Robot target) {
        /**Function moves to robot backwards by calling the updatePosition function with Argument
         * nrSteps as a negative integer
         *
         * @param target The Robot object
         * @return boolean if the program should continue
         */

        int nrSteps = Integer.parseInt(getArgument());
        if (target.updatePosition(-1 * nrSteps) == Robot.Conditions.SUCCESS){
            String positionText = "[" + target.getPosition().getX() + "," + target.getPosition().getY() + "]";
            target.setStatus(target.getName() + " > " + positionText + " Moved back by "+nrSteps+" steps.");
        }
        else if (target.updatePosition(nrSteps) == Robot.Conditions.FAILED_OBSTACLE_DETECTED){
            String positionText = "[" + target.getPosition().getX() + "," + target.getPosition().getY() + "]";
            target.setStatus(target.getName() + " > " + positionText + " Sorry, I have encountered an obstacle.");
        }
        else {
            String positionText = "[" + target.getPosition().getX() + "," + target.getPosition().getY() + "]";
            target.setStatus(target.getName() + " > " + positionText + " Sorry, I cannot go outside my safe zone.");
        }

        return true;
    }


    public BackCommand(String argument) {
        super("back", argument);
    }
}
