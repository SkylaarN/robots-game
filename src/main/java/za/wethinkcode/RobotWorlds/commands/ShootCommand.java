package za.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.worldLogic.Obstacles;
import za.wethinkcode.RobotWorlds.worldLogic.Players;
import za.wethinkcode.RobotWorlds.worldLogic.Position;
import za.wethinkcode.RobotWorlds.worldLogic.Robot;

import java.util.ArrayList;

public class ShootCommand extends Command{
    private final JSONObject reply = new JSONObject();
    private final JSONObject data = new JSONObject();

    public boolean execute(Robot target){

        reply.put("result", "OK");
        if(target.getBullets() > 0){
            target.fire();
            if(hitPlayer(target.getPosition(), fireBullet(30, target), target)){
                data.put("message", "Hit");
            }
            else{
                data.put("message", "Missed");
            }
        }
        else{
            data.put("message", "No Bullets");
        }
        reply.put("data", data);
        target.setStatus(reply.toString());
        return true;
    }

    public Position fireBullet(int range, Robot target){
        int newX = target.getPosition().getX();
        int newY = target.getPosition().getY();

        if (Robot.Direction.UP.equals(target.getCurrentDirection())) {
            newY = newY + range;
        }
        else if (Robot.Direction.DOWN.equals(target.getCurrentDirection())){
            newY = newY - range;
        }
        else if (Robot.Direction.RIGHT.equals(target.getCurrentDirection())){
            newX = newX + range;
        }
        else if (Robot.Direction.LEFT.equals(target.getCurrentDirection())){
            newX = newX - range;
        }

        return new Position(newX, newY);
    }

    public boolean hitPlayer(Position a, Position b, Robot target){
        ArrayList<Robot> playerRobots = Players.getPlayers();
        for (Robot playerRobot : playerRobots) {

            int x = playerRobot.getPosition().getX();
            int y = playerRobot.getPosition().getY();


            if (target != playerRobot) {
                if (onPlayer(b, x, y) || throughPlayer(a, b, x, y)) {
                    Position contact = pointContact(target.getPosition(), playerRobot.getPosition());

                    if (!Obstacles.blocksPosition(contact) && !Obstacles.blocksPath(a, contact)) {
                        data.put("robot", playerRobot.getName());
                        data.put("distance", getDistance(target.getPosition(), contact));
                        data.put("state", playerRobot.getStatusType());
                        playerRobot.damage(playerRobot);
                        return true;
                    }

                }
            }
        }
        return false;
    }

    public boolean onPlayer(Position b, int x, int y){
        return x - 2 <= b.getX() && b.getX() <= x + 2 &&
                y - 2 <= b.getY() && b.getY() <= y + 2;
    }

    public boolean throughPlayer(Position a, Position b, int x, int y){
        if(passPlayer(a.getX(), b.getX(), x + 2, x - 2) && a.getY() == b.getY() &&
                a.getY() >= y - 2 && a.getY() <= y + 2) {
            return true;

        }
        else if(passPlayer(a.getY(), b.getY(), y + 2, y - 2) && a.getX() == b.getX() &&
                a.getX() >= x - 2 && a.getX() <= x + 2){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean passPlayer(int startPath, int endPath, int startObs, int endObs){
        return (startPath <= startObs && startObs <= endPath ||
                startPath <= endObs && endObs <= endPath ||
                startObs <= startPath && startPath <= endObs ||
                startObs <= endPath && endPath <= endObs ||
                endPath <= endObs && startObs <= startPath);
    }


    public Position pointContact(Position robot, Position enemy){
        if(robot.getY() >= enemy.getY() - 2 && robot.getY() <= enemy.getY() + 2){
            return new Position(enemy.getX(), robot.getY());
        }
        else if(robot.getX() >= enemy.getX() - 2 && robot.getX() <= enemy.getX() + 2){
            return new Position(robot.getX(), enemy.getY());
        }
        return null;
    }

    public int getDistance(Position player, Position contact){
        if(player.getY() < contact.getY()){
            return contact.getY() - player.getY();
        }
        else if(player.getY() > contact.getY()){
            return player.getY() - contact.getY();
        }
        else if(player.getX() < contact.getX()){
            return contact.getX() - player.getX();
        }
        else if(player.getX() > contact.getX()){
            return player.getX() - contact.getX();
        }
        return 0;
    }



    public ShootCommand() {
        super();
    }
}
