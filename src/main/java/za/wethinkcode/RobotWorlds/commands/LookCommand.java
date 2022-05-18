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


        for(int i = 0; i < Obstacles.getObstacles().size(); i++){
            SquareObstacle obs = Obstacles.getObstacles().get(i);
            int x = obs.getBottomLeftX();
            int y = obs.getBottomLeftY();

            if (x <= target.getPosition().getX() && target.getPosition().getX() <= x + 4 ||
                    y <= target.getPosition().getY() && target.getPosition().getY() <= y + 4){

                if ((x - target.getPosition().getX() <= 150)
                        && (x - target.getPosition().getX() >= -150)
                        && (y - target.getPosition().getY() <= 150)
                        && (y - target.getPosition().getY() >= -150)){

                    JSONObject obj = new JSONObject();
                    obj.put("direction", getAbsolutePosition(target, x, y));
                    obj.put("type", "OBSTACLE");

                    JSONArray positionState = new JSONArray();
                    positionState.put(obs.getBottomLeftX());
                    positionState.put(obs.getBottomLeftY());

                    obj.put("position", positionState);
                    objects.put(obj);

                    //output.append((String.format("There's an obstacle at position %s,%s (to %s,%s), %s",
                            //x, y, (x+4), (y+4), getAbsolutePosition(target, x, y))) + "\n");
                    //drawVisibleObstacle(x,y);
                }
            }
        }
        if(output.length() == 0){
            //target.setStatus("No obstacles.");
            data.put("objects", objects);
            data.put("message", "Done");
            reply.put("data", data);
            target.setStatus(reply.toString());
        }
        else{
            //target.setStatus(output.toString().trim());
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

//    public void drawVisibleObstacle(int x, int y){
//        StdDraw.setPenColor(Color.ORANGE);
//        StdDraw.line(0.5 + x/512.0, 0.5 + y/512.0, 0.5 + (x + 4)/512.0, 0.5 + y/512.0);
//        StdDraw.line(0.5 + x/512.0, 0.5 + y/512.0, 0.5 + x/512.0, 0.5 + (y + 4)/512.0);
//        StdDraw.line(0.5 + x/512.0, 0.5 + (y + 4)/512.0, 0.5 + (x + 4)/512.0, 0.5 + (y + 4)/512.0);
//        StdDraw.line(0.5 + (x + 4)/512.0, 0.5 + y/512.0, 0.5 + (x + 4)/512.0, 0.5 + (y + 4)/512.0);
//    }
    public LookCommand(){
        super("look");
    }
}
