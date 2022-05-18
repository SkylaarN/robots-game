package za.wethinkcode.RobotWorlds;

import org.json.JSONArray;
import org.json.JSONObject;
import org.turtle.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TurtleRobot {
    private Position currentPosition;
    private Robot.Direction currentDirection;
    private int shields;
    private int shots;
    private String status;

    public TurtleRobot(){
        //code for the turtle and the border
        StdDraw.setScale(-300, 300);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.rectangle(0,0,256,256);

        Turtle robot = new Turtle(0, 0, 90);

        robot.left(150);
        robot.forward(5);
        robot.left(120);
        robot.forward(5);
        robot.left(120);
        robot.forward(5);


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

            case "DOWN":

                return Robot.Direction.DOWN;

            case "LEFT":

                return Robot.Direction.LEFT;

            case "RIGHT":

                return Robot.Direction.RIGHT;

            case "UP":
            default:
                return Robot.Direction.UP;
        }
    }

    public void newPosition(Position position){
        //code to move turtle to new position

    }

    public void newDirection(Robot.Direction direction){
        //code to make turtle face new direction
        switch (direction) {

            case UP:
                // set angle to 90
                break;

            case DOWN:

                // set angle to 270
                break;

            case RIGHT:

                // set angle to 0
                break;

            case LEFT:

                // set angle to 180
                break;


        }
    }

    public void drawLook(JSONArray objects){
        //code to draw the obstacles and enemy players
    }

    public static void robot(int x, int y, int angle, List<SquareObstacle> obstacles) {

        StdDraw.clear();
        StdDraw.enableDoubleBuffering();

        StdDraw.setScale(-300, 300);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.rectangle(0,0,256,256);

        // obstacles

        for (SquareObstacle square : obstacles) {

            StdDraw.filledSquare( square.getBottomLeftX()+2.5, square.getBottomLeftY() + 2.5, 2.5);
        }

        Turtle robot = new Turtle(x, y, angle);
        

        robot.left(150);
        robot.forward(5);
        robot.left(120);
        robot.forward(5);
        robot.left(120);
        robot.forward(5);


    }

    public void checkStatus(){
        if(this.status == "RELOAD"){
            System.out.println("Reloading...");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Reloaded");
            this.status = "NORMAL";
        }
        else if(this.status == "REPAIR"){
            System.out.println("Repairing...");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Repaired");
            this.status = "NORMAL";
        }
        else if (this.status == "DEAD"){
            System.out.println("Sorry, You are already Dead");
        }
    }


}
