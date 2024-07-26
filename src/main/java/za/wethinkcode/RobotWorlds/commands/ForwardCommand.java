package za.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
import org.turtle.*;
import za.wethinkcode.RobotWorlds.Position;
import za.wethinkcode.RobotWorlds.Robot;

import java.util.ArrayList;
import java.util.List;

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
            List pos = new ArrayList<>();
            pos.add(target.getPosition().getX());
            pos.add(target.getPosition().getY());

            reply.put("result", "OK");
            data.put("message", "Done");
            data.put("visibility", 9);
            data.put("position", pos);
            data.put("objects", new ArrayList<>());
            reply.put("data", data);
        }
        else if (target.updatePosition(nrSteps) == Robot.Conditions.FAILED_OBSTACLE_DETECTED){
            data.put("message", "Obstructed");
        }
        else {
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

