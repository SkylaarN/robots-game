package za.wethinkcode.RobotWorlds.commands;

import org.json.JSONArray;
import za.wethinkcode.RobotWorlds.worldLogic.Robot;

public abstract class Command {
    private static Exception UnknownFormatConversionException;
    private final String name;
    private String argument;


    public abstract boolean execute(Robot target);


    public Command(String name){
        this.name = name.trim().toLowerCase();
        this.argument = "";
    }

    public Command(String name, String arg){
        this.name = name.trim().toLowerCase();
        this.argument = arg;
    }


    public String getName() {                                                                           //<2>
        /**Function returns the name of the command to be executed
         *
         * @return String The name of the command to be executed
         */

        return name;
    }


    public String getArgument() {
        /**Function returns the Argument being used for the command
         *
         * @return String The commands Argument
         */

        return this.argument;
    }


    public static Command createCommand(String instruction, JSONArray args){
        /**Function uses Parameter instruction to find and return the correct Command class
         *
         * @param instruction the String containing the user input command
         * @param save the boolean that shows if a command must be saved
         * @return Command containing the correct subclass
         */


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

            case "dump":
                return new DumpCommand();

            case "shoot":
                return new ShootCommand();

            case "reload":
                return new ReloadCommand();

            case "repair":
                return new RepairCommand();

            case "robots":
                return new RobotsCommand();

            case "quit":
                return new QuitCommand();

            //For database
            case "save":
                return new SaveCommand(args.getString(0));

            case "restore":
                return new RestoreCommand(args.getString(0));

            default:
                System.out.println("Unsupported command: " + instruction);

                return new ErrorHandling(instruction);
//                break;
        }

        //will error handle here
    }
}



