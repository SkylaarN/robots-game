package za.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.Robot;

public class LeftCommand extends Command{

    @Override
    public boolean execute(Robot target){
        /**Function is used to turn the robot left by calling the updateDirection with the
         * turnRight parameter as false
         *
         * @param target The Robot object
         * @return boolean if the program should continue
         */

        target.updateDirection(false);
        String positionText = "[" + target.getPosition().getX() + "," + target.getPosition().getY() + "]";
        //target.setStatus(target.getName() + " > " + positionText + " Turned left");
        JSONObject reply = new JSONObject();
        JSONObject data = new JSONObject();
        reply.put("result", "OK");
        //data.put("message", "Done");
        data.put("message", target.getName() + " > " + positionText + " Turned left");
        reply.put("data", data);
        target.setStatus(reply.toString());
        return true;
    }


    public LeftCommand() {
        super("left");
    }
}
