package za.wethinkcode.RobotWorlds.commands;

import za.wethinkcode.RobotWorlds.Robot;;

public abstract class Command {
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


//    public static Command createCommand(String instruction, boolean save){
//        /**Function uses Parameter instruction to find and return the correct Command class
//         *
//         * @param instruction the String containing the user input command
//         * @param save the boolean that shows if a command must be saved
//         * @return Command containing the correct subclass
//         */
//
//        String[] args = instruction.toLowerCase().trim().split(" ", 2);
//        switch (args[0]){
//            case "shutdown":
//            case "off":
//            case "help":
//            case "forward":
//            case "back":
//            case "left":
//            case "right":
//            default:
//                throw new IllegalArgumentException("Unsupported command: " + instruction);
//        }
//    }
}

