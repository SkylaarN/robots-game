package za.wethinkcode.RobotWorlds.domain.ClientCommands;

import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.domain.world.Robot;

import java.util.ArrayList;
import java.util.List;

public class LeftCommand extends Command{

    @Override
    public boolean execute(Robot target){
        /*Function is used to turn the robot left by calling the updateDirection with the
          turnRight parameter as false

          @param target The Robot object
         * @return boolean if the program should continue
         */

        target.updateDirection(false);

        JSONObject reply = new JSONObject();
        JSONObject data = new JSONObject();

        List<Object> pos = new ArrayList<>();
        pos.add(target.getPosition().getX());
        pos.add(target.getPosition().getY());


        reply.put("result", "OK");
        data.put("visibility", target.getVisibility());
        data.put("position", pos);
        data.put("objects", new ArrayList<>());
        reply.put("data", data);
        target.setStatus(reply.toString());

        return true;
    }


    public LeftCommand() {
        super();
    }
}
