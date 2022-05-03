package za.wethinkcode.RobotWorlds.commands;

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
        String positionText = "[" + target.getPosition().getX() + "," + target.getPosition().getY() + "]";
        System.out.println(target.getName() + " > " + positionText + " Turned right");
        return true;
    }


    public RightCommand() {
        super("right");
    }
}
