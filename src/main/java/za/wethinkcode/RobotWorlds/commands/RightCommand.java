package za.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.worldLogic.Robot;

import java.util.ArrayList;
import java.util.List;

public class RightCommand extends Command{

    @Override
    public boolean execute(Robot target){
        /**Function is used to turn the robot right by calling the updateDirection with the
         * turnRight parameter as true
         *
         * @param target The Robot object
         * @return boolean if the program should continue
         */

        target.updateDirection(true);
        JSONObject reply = new JSONObject();
        JSONObject data = new JSONObject();

        List pos = new ArrayList<>();
        pos.add(target.getPosition().getX());
        pos.add(target.getPosition().getY());


        reply.put("result", "OK");
//        data.put("message", "Done");
        data.put("visibility", 9);
        data.put("position", pos);
        data.put("objects", new ArrayList<>());
        reply.put("data", data);
        target.setStatus(reply.toString());

        return true;
    }


    public RightCommand() {
        super("right");
    }
}
