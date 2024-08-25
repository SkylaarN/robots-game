package za.wethinkcode.RobotWorlds.domain.ClientCommands;
import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.domain.world.Position;
import za.wethinkcode.RobotWorlds.domain.world.Robot;
import za.wethinkcode.RobotWorlds.server.Players;
import za.wethinkcode.RobotWorlds.server.SimpleServer;

import java.util.ArrayList;
import java.util.List;


public class BackCommand extends Command {

    /**Function moves to robot backwards by calling the updatePosition function with Argument
     * nrSteps as a negative integer
     *
     * @param target The Robot object
     * @return boolean if the program should continue
     */
    @Override
    public boolean execute(Robot target) {

        JSONObject reply = new JSONObject();
        JSONObject data = new JSONObject();
        reply.put("result", "OK");

        int nrSteps = Integer.parseInt(getArgument()) * -1;
        if (collide(target, newPosition(target, nrSteps))) {
            data.put("message", "Obstructed");
        }else if (target.updatePosition(nrSteps) == Robot.Conditions.SUCCESS){
            List<Object> pos = new ArrayList<>();
            pos.add(target.getPosition().getX());
            pos.add(target.getPosition().getY());


            Command.Putdata(target,pos);
        } else if (target.updatePosition(nrSteps) == Robot.Conditions.FATAL_PIT_DETECTED){
            data.put("message", "You fell into a pit.");
            Players.getPlayers().remove(target);
            SimpleServer.listRobots.remove(target.getName());
        }
        else if (target.updatePosition(nrSteps) == Robot.Conditions.FAILED_OBSTACLE_DETECTED){
            data.put("message", "Obstructed");
        }
        else {
            data.put("message", "Border");
        }

        reply.put("data", data);
        target.setStatus(reply.toString());
        return true;
    }

    public boolean collide(Robot target, Position newPosition){
        for (Robot robot : Players.playerRobots){
            if (target != robot) {
                if (target.getPosition().getX() == robot.getPosition().getX()) {
                    if (moveVertically(target, robot, newPosition)) {
                        return true;
                    }
                } else if (target.getPosition().getY() == robot.getPosition().getY()) {
                    if (moveHorizontally(target, robot, newPosition)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Position newPosition(Robot target, int nrSteps){
        int newX = target.getPosition().getX();
        int newY = target.getPosition().getY();

        if (Robot.Direction.UP.equals(target.getCurrentDirection())) {
            newY = newY + nrSteps;
        }
        else if (Robot.Direction.DOWN.equals(target.getCurrentDirection())){
            newY = newY - nrSteps;
        }
        else if (Robot.Direction.RIGHT.equals(target.getCurrentDirection())){
            newX = newX + nrSteps;
        }
        else if (Robot.Direction.LEFT.equals(target.getCurrentDirection())){
            newX = newX - nrSteps;
        }

        return new Position(newX, newY);
    }

    public boolean moveVertically(Robot target, Robot visitor, Position newPosition) {
        if (target.getCurrentDirection().equals(Robot.Direction.DOWN)) {
            if (target.getPosition().getY() < visitor.getPosition().getY()) {
                return newPosition.getY() >= visitor.getPosition().getY();
            }
        } else if (target.getCurrentDirection().equals(Robot.Direction.UP)) {
            if (target.getPosition().getY() > visitor.getPosition().getY()) {
                return newPosition.getY() <= visitor.getPosition().getY();
            }
        }
        return false;
    }

    private boolean moveHorizontally(Robot target, Robot visitor, Position newPosition) {
        if (target.getCurrentDirection().equals(Robot.Direction.RIGHT)) {
            if (target.getPosition().getX() > visitor.getPosition().getX()) {
                return newPosition.getX() <= visitor.getPosition().getX();
            }
        } else if (target.getCurrentDirection().equals(Robot.Direction.LEFT)) {
            if (target.getPosition().getX() < visitor.getPosition().getX()) {
                return newPosition.getX() >= visitor.getPosition().getX();
            }
        }
        return false;
    }




    public BackCommand(String argument) {
        super(argument);
    }
}
