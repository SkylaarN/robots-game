package za.wethinkcode.RobotWorlds.domain.ClientCommands;

import org.json.JSONArray;
import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.domain.world.Obstacles;
import za.wethinkcode.RobotWorlds.domain.world.Robot;
import za.wethinkcode.RobotWorlds.domain.world.SquareObstacle;
import za.wethinkcode.RobotWorlds.server.Players;

import java.util.ArrayList;
import java.util.List;

public class LookCommand extends Command{

    private final JSONObject reply = new JSONObject();
    private final JSONObject data = new JSONObject();
    private final JSONArray objects = new JSONArray();

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

            if (x - 10 <= target.getPosition().getX() && target.getPosition().getX() <= x + 10 ||
                    y - 10 <= target.getPosition().getY() && target.getPosition().getY() <= y + 10){

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
        if(output.isEmpty()){

            List<Object> pos = new ArrayList<>();
            pos.add(target.getPosition().getX());
            pos.add(target.getPosition().getY());

            data.put("visibility", target.getVisibility());
            data.put("position", pos);
            data.put("objects", objects);
        }
        else{
            data.put("objects", objects);
            data.put("message", "Done");
        }
        reply.put("data", data);
        target.setStatus(reply.toString());
        return true;
    }

//AbsolutePosition
    public String getAbsolutePosition(Robot target, int x, int y){

        if(target.getPosition().getY() < y){
            return "NORTH";
        }
        else if (target.getPosition().getY() > y){
            return "SOUTH";
        }
        else if(target.getPosition().getX() < x){
            return "EAST";
        }
        else{
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

    //  Checks if there's an edge nearby
    public ArrayList<String> getEdge(Robot target){
        ArrayList<String> edgePosition = new ArrayList<>();
        int x = target.getPosition().getX();
        int y = target.getPosition().getY();
        JSONArray positionState = new JSONArray();

        if(x > 75 ){
            edgePosition.add("EAST");
            positionState.put(150);
            positionState.put(y);
        }
        if(x < -75){
            edgePosition.add("WEST");
            positionState.put(-150);
            positionState.put(y);
        }
        if(y > 75){
            edgePosition.add("NORTH");
            positionState.put(x);
            positionState.put(150);
        }
        if(y < -75){
            edgePosition.add("SOUTH");
            positionState.put(x);
            positionState.put(-150);
        }

        JSONObject obj = new JSONObject();

        for (String edgePos: edgePosition){
            obj.put("direction", edgePos);
        }
        obj.put("type", "ROBOT");

        for(Object positionS: positionState){
            obj.put("position", positionS);
        }

        objects.put(obj);
        return edgePosition;
    }

    public LookCommand(){
        super();
    }
}
