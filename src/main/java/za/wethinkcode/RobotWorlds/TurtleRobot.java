package za.wethinkcode.RobotWorlds;

import org.json.JSONArray;
import org.json.JSONObject;
import org.turtle.StdDraw;

public class TurtleRobot {
    private Position currentPosition;
    private Robot.Direction currentDirection;
    private int shields;
    private int shots;
    private String status;

    public TurtleRobot(){
        //code for the turtle and the border
    }

    public void handleReply(JSONObject command){
        handleStatus(command.getJSONObject("state"));



    }

    public void handleStatus(JSONObject state){
        int x = state.getJSONArray("position").getInt(0);
        int y = state.getJSONArray("position").getInt(1);
        this.currentPosition = new Position(x, y);

        this.currentDirection = getDirection(state.getString("direction"));

        this.shields = state.getInt("shields");

        this.shots = state.getInt("shots");

        this.status = state.getString("status");

    }

    public Robot.Direction getDirection(String direction){
        switch(direction){
            case "UP":
                return Robot.Direction.UP;
            case "DOWN":
                return Robot.Direction.DOWN;
            case "LEFT":
                return Robot.Direction.LEFT;
            case "RIGHT":
                return Robot.Direction.RIGHT;
            default:
                return Robot.Direction.UP;
        }
    }

    public void newPosition(Position position){
        //code to move turtle to new position
    }

    public void newDirection(Robot.Direction direction){
        //code to make turtle face new direction
    }

    public void drawLook(JSONArray objects){
        //code to draw the obstacles and enemy players
    }



}
