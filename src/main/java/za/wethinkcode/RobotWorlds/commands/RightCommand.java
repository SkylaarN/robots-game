package za.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.Robot;

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

        reply.put("result", "OK");
        data.put("message", "Done");
        reply.put("data", data);
        target.setStatus(reply.toString());

        return true;
    }


    public RightCommand() {
        super("right");
    }
}
