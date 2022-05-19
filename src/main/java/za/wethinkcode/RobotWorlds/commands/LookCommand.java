package za.wethinkcode.RobotWorlds.commands;

import org.json.JSONArray;
import org.json.JSONObject;
import org.turtle.StdDraw;
import za.wethinkcode.RobotWorlds.Obstacles;
import za.wethinkcode.RobotWorlds.Players;
import za.wethinkcode.RobotWorlds.Robot;
import za.wethinkcode.RobotWorlds.SquareObstacle;

import java.awt.*;
import java.util.ArrayList;

public class LookCommand extends Command{

    public enum AbsolutePosition{
        NORTH, EAST, SOUTH, WEST
    }

    private JSONObject reply = new JSONObject();
    private JSONObject data = new JSONObject();
    private JSONArray objects = new JSONArray();

    public boolean execute(Robot target){
        StringBuilder output = new StringBuilder();

        //Array contains the robots
        ArrayList<Robot> robots = Players.getPlayers();

        reply.put("result", "OK");

        getOtherRobotsPosition(target, robots);

        for(int i = 0; i < Obstacles.getObstacles().size(); i++){
            SquareObstacle obs = Obstacles.getObstacles().get(i);
            int x = obs.getBottomLeftX();
            int y = obs.getBottomLeftY();

            if (x <= target.getPosition().getX() && target.getPosition().getX() <= x + 4 ||
                    y <= target.getPosition().getY() && target.getPosition().getY() <= y + 4){

                if ((x - target.getPosition().getX() <= 75)
                        && (x - target.getPosition().getX() >= -75)
                        && (y - target.getPosition().getY() <= 75)
                        && (y - target.getPosition().getY() >= -75)){

                    JSONObject obj = new JSONObject();
                    obj.put("direction", getAbsolutePosition(target, x, y));
                    obj.put("type", "OBSTACLE");

                    JSONArray positionState = new JSONArray();
                    positionState.put(obs.getBottomLeftX());
                    positionState.put(obs.getBottomLeftY());

                    obj.put("position", positionState);
                    objects.put(obj);

                }
            }
        }
        if(output.length() == 0){
            data.put("objects", objects);
            data.put("message", "Done");
            reply.put("data", data);
            target.setStatus(reply.toString());
        }
        else{

            data.put("objects", objects);
            data.put("message", "Done");
            reply.put("data", data);
            target.setStatus(reply.toString());
        }
        return true;
    }

//AbsolutePosition
    public String getAbsolutePosition(Robot target, int x, int y){

        if(target.getPosition().getY() < y){
//            return AbsolutePosition.NORTH;
            return "NORTH";
        }
        else if (target.getPosition().getY() > y){
//            return AbsolutePosition.SOUTH;
            return "SOUTH";
        }
        else if(target.getPosition().getX() < x){
//            return AbsolutePosition.EAST;
            return "EAST";
        }
        else{
//            return AbsolutePosition.WEST;
            return "WEST";
        }

    }

    public void getOtherRobotsPosition(Robot target, ArrayList<Robot> robots){
        for(Robot otherRobots: robots){
            if(target != otherRobots){
                int x = otherRobots.getPosition().getX();
                int y = otherRobots.getPosition().getY();

                if (x <= target.getPosition().getX() && target.getPosition().getX() <= x + 4 ||
                        y <= target.getPosition().getY() && target.getPosition().getY() <= y + 4){

                    if ((x - target.getPosition().getX() <= 75)
                            && (x - target.getPosition().getX() >= -75)
                            && (y - target.getPosition().getY() <= 75)
                            && (y - target.getPosition().getY() >= -75)){

                        JSONObject obj = new JSONObject();
                        obj.put("direction", getAbsolutePosition(target, x, y));
                        obj.put("type", "ROBOT");

                        JSONArray positionState = new JSONArray();
                        positionState.put(x);
                        positionState.put(y);

                        obj.put("position", positionState);
                        objects.put(obj);
                    }

                }
            }
        }
    }

    public LookCommand(){
        super("look");
    }
}
