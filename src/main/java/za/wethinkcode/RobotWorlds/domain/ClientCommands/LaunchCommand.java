package za.wethinkcode.RobotWorlds.domain.ClientCommands;

import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.domain.world.Robot;

import java.util.ArrayList;
import java.util.List;

public class LaunchCommand extends Command {

    @Override
    public boolean execute(Robot target) {
        /*Function moves to robot forward by calling the updatePosition function with Argument
          nrSteps as a positive integer

          @param target The Robot object
         * @return boolean if the program should continue
         */
        JSONObject reply = new JSONObject();
        JSONObject data = new JSONObject();
        reply.put("result", "OK");

        new Make(getArgument(), target);

        List<Object> pos = new ArrayList<>();
        pos.add(target.getPosition().getX());
        pos.add(target.getPosition().getY());

        reply.put("result", "OK");
        data.put("message", "Done");
        data.put("visibility", target.getVisibility());
        data.put("position", pos);
        data.put("objects", new ArrayList<>());
        reply.put("data", data);


        reply.put("data", data);
        target.setStatus(reply.toString());
        return true;
    }


    public LaunchCommand(String argument) {
        super(argument);
    }
}

