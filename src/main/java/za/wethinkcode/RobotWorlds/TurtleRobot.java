package za.wethinkcode.RobotWorlds;

import org.json.JSONArray;
import org.json.JSONObject;
import org.turtle.*;
import za.wethinkcode.RobotWorlds.configuration.Configuration;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TurtleRobot {

    private Position currentPosition;
    private Robot.Direction currentDirection;
    private int shields;
    private int shots;
    private String status;
    private final Configuration configuration = new Configuration();
    private int angle;

    private int directionIndex = 0;

    public TurtleRobot(){
        //code for the turtle and the border
        terminator(0,0,90);



    }

    public void handleReply(JSONObject command, JSONObject request){
        if(command.getString("result") == "OK"){
            handleStatus(command.getJSONObject("state"));
            if(request.getString("command") == "forward" || request.getString("command") == "back"){
                if(command.getJSONObject("data").getString("message") == "Done"){
                    System.out.println(request.getString("robot") + " > " + "[" + currentPosition.getX() +
                            " , " + currentPosition.getY() + "] Moved " + request.getString("command") +
                            " by " + request.getJSONArray("arguments").getInt(0) + " steps.");
                }
            }
            System.out.println(command.getJSONObject("data").getString("message"));
        }




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
        StdDraw.clear();
        StdDraw.enableDoubleBuffering();

        // the angle needs to be updated
        terminator(position.getX(), position.getY(), 90);



    }

    public void newDirection() {

        switch (this.directionIndex) {

            case 0:

                this.currentDirection = Robot.Direction.UP;
                break;

            case 1:

                this.currentDirection = Robot.Direction.RIGHT;
                break;

            case 2:

                this.currentDirection = Robot.Direction.DOWN;
                break;

            case 3:

                this.currentDirection = Robot.Direction.LEFT;
                break;
        }

    }

    public void drawLook(JSONArray objects){
        //code to draw the obstacles and enemy players
        for (int i = 0; i < objects.length(); i++) {
            int x = objects.getJSONObject(i).getJSONArray("position").getInt(0);
            int y = objects.getJSONObject(i).getJSONArray("position").getInt(1);
            if(objects.getJSONObject(i).getString("type") == "OBSTACLE"){
                //set line colour to black
                //Turtle box drawing code
            }
            else if(objects.getJSONObject(i).getString("type") == "ROBOT"){
                //set line colour to red
                //Turtle box drawing code
            }


        }
    }


    public void checkStatus(){
        if(Objects.equals(this.status, "RELOAD")){
            System.out.println("Reloading...");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Reloaded");
            this.status = "NORMAL";
        }
        else if(Objects.equals(this.status, "REPAIR")){
            System.out.println("Repairing...");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Repaired");
            this.status = "NORMAL";
        }
        else if (Objects.equals(this.status, "DEAD")){
            System.out.println("Sorry, You are already Dead");
        }
    }

    public void terminator(int x, int y, int a) {

        StdDraw.setScale(configuration.getWorldSize().getX(), configuration.getWorldSize().getY());
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.rectangle(0,0,256,256);

        Turtle robot = new Turtle(x, y, a);

        robot.left(150);
        robot.forward(5);
        robot.left(120);
        robot.forward(5);
        robot.left(120);
        robot.forward(5);
    }

    public void rightTurn() {

        this.directionIndex += 1;

        if (directionIndex > 3) {

            this.directionIndex = 0;
        }
    }

    public void leftTurn() {

        this.directionIndex -= 1;

        if (this.directionIndex < 0) {

            this.directionIndex = 3;
        }
    }


}
