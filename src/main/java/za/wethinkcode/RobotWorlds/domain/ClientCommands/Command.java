package za.wethinkcode.RobotWorlds.domain.ClientCommands;

import org.json.JSONArray;
import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.domain.serverCommands.RestoreCommand;
import za.wethinkcode.RobotWorlds.domain.serverCommands.SaveCommand;
import za.wethinkcode.RobotWorlds.domain.world.Robot;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    private final String argument;


    public abstract boolean execute(Robot target);


    public Command(){
        this.argument = "";
    }

    public Command(String arg){
        this.argument = arg;
    }


    public String getArgument() {

        return this.argument;
    }

    public static void Putdata(Robot target, List<Object> pos){

        JSONObject reply = new JSONObject();
        JSONObject data = new JSONObject();

        reply.put("result", "OK");
        data.put("message", "Done");
        data.put("visibility", target.getVisibility());
        data.put("position", pos);
        data.put("objects", new ArrayList<>());
        reply.put("data", data);
    }


    public static Command createCommand(String instruction, JSONArray args){


        switch (instruction){
            case "help":
                return new HelpCommand();

            case "launch":
                return new LaunchCommand(args.getString(0));

            case "forward":
                return new ForwardCommand(args.getString(0));

            case "back":
                return new BackCommand(args.getString(0));

            case "right":
                return new RightCommand();

            case "left":
                return new LeftCommand();

            case "state":
                return new StateCommand();

            case "look":
                return new LookCommand();

            case "shoot":
                return new ShootCommand();

            case "reload":
                return new ReloadCommand();

            case "repair":
                return new RepairCommand();

            case "quit":
                return new QuitCommand();

            case "save":
                return new SaveCommand(args.getString(0));

            case "restore":
                return new RestoreCommand(args.getString(0));

            default:
                return new ErrorHandling(instruction);
        }
    }
}



