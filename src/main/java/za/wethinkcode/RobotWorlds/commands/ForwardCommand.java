package za.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
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
        JSONObject reply = new JSONObject();
        JSONObject data = new JSONObject();
        reply.put("result", "OK");
        int nrSteps = Integer.parseInt(getArgument());
        if (target.updatePosition(nrSteps) == Robot.Conditions.SUCCESS){
            String positionText = "[" + target.getPosition().getX() + "," + target.getPosition().getY() + "]";
            //target.setStatus(target.getName() + " > " + positionText + " Moved forward by "+nrSteps+" steps.");
            data.put("message", "Done");
        }
        else if (target.updatePosition(nrSteps) == Robot.Conditions.FAILED_OBSTACLE_DETECTED){
            String positionText = "[" + target.getPosition().getX() + "," + target.getPosition().getY() + "]";
            //target.setStatus(target.getName() + " > " + positionText + " Sorry, I have encountered an obstacle.");
            data.put("message", "Obstructed");
        }
        else {
            String positionText = "[" + target.getPosition().getX() + "," + target.getPosition().getY() + "]";
            //target.setStatus(target.getName() + " > " + positionText + " Sorry, I cannot go outside my safe zone.");
            data.put("message", "Border");
        }
        reply.put("data", data);
        target.setStatus(reply.toString());
        return true;
    }


    public ForwardCommand(String argument) {
        super("forward", argument);
    }
}

