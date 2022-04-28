package za.wethinkcode.RobotWorlds.commands;
import edu.princeton.cs.introcs.StdDraw;
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
        if (target.updatePosition(-1 * nrSteps) == "UpdateResponse.SUCCESS"){
            System.out.println("Moved back by "+nrSteps+" steps.");
        }
        else if (target.updatePosition(nrSteps) == "UpdateResponse.FAILED_OBSTRUCTED"){
            System.out.println("Sorry, I have encountered an obstacle.");
        }
        else {
            System.out.println("Sorry, I cannot go outside my safe zone.");
        }

        return true;
    }


    public BackCommand(String argument) {
        super("back", argument);
    }
}
