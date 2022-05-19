package za.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.Robot;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help");
    }

    @Override
    public boolean execute(Robot target) {
        JSONObject reply = new JSONObject();
        JSONObject data = new JSONObject();

        reply.put("result", "OK");
        data.put("message", "< Robot Commands >~" +
                "OFF  - Shut down robot~" +
                "HELP - provide information about commands~" +
                "FORWARD - move forward by specified number of steps, e.g. 'forward 10'~" +
                "BACK - move backward by specific number of steps, e.g. 'back 10'~" +
                "RIGHT - turn right by 90 degrees~" +
                "LEFT - turn left by 90 degrees~" +
                "ROBOTS - List all robots~" +
                "SHOOT - Shoot robot~" +
                "LOOK - See everything in its line of sight~");
        reply.put("data", data);
        target.setStatus(reply.toString());
        return true;
    }
}
