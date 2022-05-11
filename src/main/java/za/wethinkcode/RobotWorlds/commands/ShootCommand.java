package za.wethinkcode.RobotWorlds.commands;

import org.turtle.*;
import za.wethinkcode.RobotWorlds.Obstacles;
import za.wethinkcode.RobotWorlds.Players;
import za.wethinkcode.RobotWorlds.Position;
import za.wethinkcode.RobotWorlds.Robot;

import java.util.ArrayList;

public class ShootCommand extends Command{
    public boolean execute(Robot target){
        target.fire();
        if(hitPlayer(target.getPosition(), fireBullet(30, target), target)){
            target.setStatus("Player was shot");
        }
        else{
            target.setStatus("Missed");
        }
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

        Position newPosition = new Position(newX, newY);
        return newPosition;
    }

    public boolean hitPlayer(Position a, Position b, Robot target){
        ArrayList<Robot> playerRobots = Players.getPlayers();
        for (int i = 0; i < playerRobots.size(); i++) {

            int x = playerRobots.get(i).getPosition().getX();
            int y = playerRobots.get(i).getPosition().getY();

            if(target != playerRobots.get(i)){
                if(onPlayer(b, x, y) || throughPlayer(a, b, x, y)){
                    if(!Obstacles.blocksPosition(playerRobots.get(i).getPosition()) &&
                            !Obstacles.blocksPath(a, playerRobots.get(i).getPosition())){
                        playerRobots.get(i).damage();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean onPlayer(Position b, int x, int y){
        if(x - 2 <= b.getX() && b.getX() <= x + 2 &&
                y - 2 <= b.getY() && b.getY() <= y + 2){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean throughPlayer(Position a, Position b, int x, int y){
        if(passPlayer(a.getX(), b.getX(), x - 2, x + 2) && a.getY() == b.getY() &&
                a.getY() >= y - 2 && a.getY() <= y + 2) {
            return true;
        }
        else if(passPlayer(a.getY(), b.getY(), y - 2, y + 2) && a.getX() == b.getX() &&
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
                startObs <= endPath && endPath <= endObs);
    }



    public ShootCommand() {
        super("shoot");
    }
}
