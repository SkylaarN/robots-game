package za.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
import org.turtle.*;
import za.wethinkcode.RobotWorlds.Obstacles;
import za.wethinkcode.RobotWorlds.Players;
import za.wethinkcode.RobotWorlds.Position;
import za.wethinkcode.RobotWorlds.Robot;

import java.util.ArrayList;

public class ShootCommand extends Command{
    private JSONObject reply = new JSONObject();
    private JSONObject data = new JSONObject();

    public boolean execute(Robot target){

        reply.put("result", "OK");
        if(target.getBullets() > 0){
            target.fire();
            if(hitPlayer(target.getPosition(), fireBullet(30, target), target)){
                reply.put("data", data);
                target.setStatus(reply.toString());
                //target.setStatus("Player was shot");
            }
            else{
                data.put("message", "Missed");
                reply.put("data", data);
                target.setStatus(reply.toString());
                //target.setStatus("Missed");
            }
        }
        else{
            data.put("message", "No Bullets");
            reply.put("data", data);
            target.setStatus(reply.toString());
            //target.setStatus("No Bullets");
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

//        if(Obstacles.blocksPosition(newPosition) ||
//                Obstacles.blocksPath(target.getPosition(), newPosition)){
//            System.out.println("Bullet blocked");
//        }
//        else{
//            System.out.println("Bullet not blocked");
//        }

        return newPosition;
    }

    public boolean hitPlayer(Position a, Position b, Robot target){
        ArrayList<Robot> playerRobots = Players.getPlayers();
        System.out.println(playerRobots);
        for (int i = 0; i < playerRobots.size(); i++) {

            int x = playerRobots.get(i).getPosition().getX();
            int y = playerRobots.get(i).getPosition().getY();

//            System.out.println("Checking Enemy:  - " + playerRobots.get(i).getName());
//            System.out.println("Target X: - " + target.getPosition().getX());
//            System.out.println("Target Y: - " + target.getPosition().getY());
//            System.out.println("Enemy X: - " + playerRobots.get(i).getPosition().getX());
//            System.out.println("Enemy Y: - " + playerRobots.get(i).getPosition().getY());
//line from robot to target is not straight

            if(target != playerRobots.get(i)){
                if(onPlayer(b, x, y) || throughPlayer(a, b, x, y)){
                    Position contact = pointContact(target.getPosition(), playerRobots.get(i).getPosition());
//                    System.out.println("Contact X: - " + contact.getX());
//                    System.out.println("Contact Y: - " + contact.getY());
                    if(!Obstacles.blocksPosition(contact) && !Obstacles.blocksPath(a, contact)){
//                        System.out.println(playerRobots.get(i).getName() + " - 1 damage.");
                        data.put("message", playerRobots.get(i).getName() + " was Hit");
                        data.put("robot", playerRobots.get(i).getName());
                        data.put("distance", getDistance(target.getPosition(), contact));
                        //data.put("state", playerRobots.get(i).getStatusType());
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
        if(passPlayer(a.getX(), b.getX(), x + 2, x - 2) && a.getY() == b.getY() &&
                a.getY() >= y - 2 && a.getY() <= y + 2) {
            System.out.println("Through x");
            return true;

        }
        else if(passPlayer(a.getY(), b.getY(), y + 2, y - 2) && a.getX() == b.getX() &&
                a.getX() >= x - 2 && a.getX() <= x + 2){
            System.out.println("Through y");
            return true;
        }
        else{
            return false;
        }
    }

    public boolean passPlayer(int startPath, int endPath, int startObs, int endObs){
//        System.out.println("---PassPlayer---");
//        System.out.println(startPath <= startObs && startObs <= endPath);
//        System.out.println(startPath <= endObs && endObs <= endPath);
//        System.out.println(startObs <= startPath && startPath <= endObs);
//        System.out.println(startObs <= endPath && endPath <= endObs);
//        System.out.println(endPath <= endObs && startObs <= startPath);
//        System.out.println("----------------");
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
        super("shoot");
    }
}
