package za.wethinkcode.RobotWorlds.commands;
import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.worldLogic.Robot;

import java.util.ArrayList;
import java.util.List;


public class BackCommand extends Command {

    /**Function moves to robot backwards by calling the updatePosition function with Argument
     * nrSteps as a negative integer
     *
     * @param target The Robot object
     * @return boolean if the program should continue
     */
    @Override
    public boolean execute(Robot target) {

        JSONObject reply = new JSONObject();
        JSONObject data = new JSONObject();
        reply.put("result", "OK");

        int nrSteps = Integer.parseInt(getArgument());
        if (target.updatePosition(-1 * nrSteps) == Robot.Conditions.SUCCESS){
            List pos = new ArrayList<>();
            pos.add(target.getPosition().getX());
            pos.add(target.getPosition().getY());


            reply.put("result", "OK");
            data.put("message", "Done");
            data.put("visibility", target.getVisibility());
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


    public BackCommand(String argument) {
        super("back", argument);
    }
}
