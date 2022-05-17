package za.wethinkcode.RobotWorlds;
import org.json.JSONArray;
import org.json.JSONObject;
import org.turtle.*;
import za.wethinkcode.RobotWorlds.commands.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Robot {

    private Position position;
    private Direction currentDirection;
    public String name;
    private String status;

    public static Random random = new Random();
    int color1 = random.nextInt(255);
    int color2 = random.nextInt(255);
    int color3 = random.nextInt(255);

    private int health = 5;
    private int bullets = 8;

    public enum Direction{
        UP, RIGHT, DOWN, LEFT
    }

    public enum Conditions {

        SUCCESS, FAILED_OBSTACLE_DETECTED, FAILED_OUTSIDE_ROBOT_WORLD
    }


    public Robot(String name){
        this.position = new Position(0, 0);
        this.currentDirection = Direction.UP;
        this.name = name;



        //StdDraw.setPenColor(random.nextInt(255), random.nextInt(255), random.nextInt(255));

    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }

    void handleCommand(String com, JSONArray args){
        StdDraw.setPenColor(color1, color2, color3);
        Command command = Command.createCommand(com, args);

        if (command == null){
            setStatus("Sorry, I did not understand '" + com + "'.");
        }
        else{
            command.execute(this);
        }
    }

    public void setPosition(Position pos){
        this.position = pos;
    }

    public Position getPosition(){
        return this.position;
    }

    public Direction getCurrentDirection(){
        return this.currentDirection;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public Position nextPosition(int xChange, int yChange){
        return new Position(this.position.getX() + xChange, this.position.getY() + yChange);
    }

    public void updateDirection(boolean turnRight) {
        /**Function changes the current direction of the robot depending
         * on if it is told to move left or right
         *
         * @param turnRight boolean containing if the robot turns left or right
         */

        switch(this.currentDirection){
            case UP:
                if(turnRight){
                    this.currentDirection = Direction.RIGHT;
                }
                else{
                    this.currentDirection = Direction.LEFT;
                }
                break;
            case RIGHT:
                if(turnRight){
                    this.currentDirection = Direction.DOWN;
                }
                else{
                    this.currentDirection = Direction.UP;
                }
                break;
            case DOWN:
                if(turnRight){
                    this.currentDirection = Direction.LEFT;
                }
                else{
                    this.currentDirection = Direction.RIGHT;
                }
                break;
            case LEFT:
                if(turnRight){
                    this.currentDirection = Direction.UP;
                }
                else{
                    this.currentDirection = Direction.DOWN;
                }
                break;

        }
    }

    //UpdateResponse
    public Conditions updatePosition(int nrSteps) {
        /**Function gets current direction to check if the new Position is in
         * an unblocked position and if it is, move to the new position and draw
         * a line to the next position with the robotTurtle
         *
         * @param nrSteps int value of the number of steps to the next Position
         * @return UpdateResponse of the check to see if it moves or not
         */

        int newX = this.position.getX();
        int newY = this.position.getY();

        if (Direction.UP.equals(this.currentDirection)) {
            newY = newY + nrSteps;
        }
        else if (Direction.DOWN.equals(this.currentDirection)){
            newY = newY - nrSteps;
        }
        else if (Direction.RIGHT.equals(this.currentDirection)){
            newX = newX + nrSteps;
        }
        else if (Direction.LEFT.equals(this.currentDirection)){
            newX = newX - nrSteps;
        }

        Position newPosition = new Position(newX, newY);

        if (isNewPositionAllowed(newPosition)){

            if (Obstacles.blocksPosition(newPosition)){
                return Conditions.FAILED_OBSTACLE_DETECTED;
            }
            else if (Obstacles.blocksPath(this.position, newPosition)){
                return Conditions.FAILED_OBSTACLE_DETECTED;
            }
            else{

                StdDraw.line(0.5 + position.getX()/512.0,
                0.5 + position.getY()/512.0,
                0.5 + newPosition.getX()/512.0,
                0.5 + newPosition.getY()/512.0);

                }
                this.position = newPosition;
                return Conditions.SUCCESS;
            }
        return Conditions.FAILED_OUTSIDE_ROBOT_WORLD;
    }

    public boolean isNewPositionAllowed(Position position) {
        /** Function checks to see if Position isIn the specified area
         *
         * @param position the Position of the new position of the robot
         * @return boolean if the Position is in the area
         */

        if (position.isIn(new Position(-150, 150),new Position(150, -150))) {
            return true;
        }
        else{
            return false;
        }
    }

    public static void drawBorder(){
//        StdDraw.setPenRadius(0.003);
//        StdDraw.setPenColor(StdDraw.MAGENTA);
//        StdDraw.line(0.2, 0.2, 0.8, 0.2);
//        StdDraw.line(0.2, 0.8, 0.8, 0.8);
//        StdDraw.line(0.2, 0.2, 0.2, 0.8);
//        StdDraw.line(0.8, 0.2, 0.8, 0.8);
        new TurtleRobot();
    }

    public static void drawObstacles(){
        ArrayList<SquareObstacle> obstacles = Obstacles.getObstacles();
        StdDraw.setPenColor(Color.RED);
        for (int i = 0; i < obstacles.size(); i++) {
            SquareObstacle sqrObs = obstacles.get(i);
            int x = sqrObs.getBottomLeftX();
            int y = sqrObs.getBottomLeftY();
//            StdDraw.line(0.5 + x/512.0, 0.5 + y/512.0, 0.5 + (x + 4)/512.0, 0.5 + y/512.0);
//            StdDraw.line(0.5 + x/512.0, 0.5 + y/512.0, 0.5 + x/512.0, 0.5 + (y + 4)/512.0);
//            StdDraw.line(0.5 + x/512.0, 0.5 + (y + 4)/512.0, 0.5 + (x + 4)/512.0, 0.5 + (y + 4)/512.0);
//            StdDraw.line(0.5 + (x + 4)/512.0, 0.5 + y/512.0, 0.5 + (x + 4)/512.0, 0.5 + (y + 4)/512.0);
            StdDraw.filledSquare(x +2.5, y + 2.5, 2.5);

        }

    }

    public void damage(){
        this.health = this.health - 1;
//        if(this.health == 0){
//            System.exit(0);
//        }
    }

    public int getHealth(){
        return this.health;
    }

    public void setHealth(int health){

        this.health = health;
//        if(health == 0){
//            System.exit(0);
//        }
    }

    public void fire(){
        this.bullets = this.bullets - 1;
    }

    public int getBullets(){
        return this.bullets;
    }

    public void setBullets(int bullets){
        this.bullets = bullets;
    }

    public JSONArray getPositionState(){
        JSONArray positionState = new JSONArray();
        positionState.put(getPosition().getX());
        positionState.put(getPosition().getY());
        return positionState;
    }

    public String getDirectionString(){
        switch(getCurrentDirection()){
            case UP:
                return "UP";
            case DOWN:
                return "DOWN";
            case RIGHT:
                return "RIGHT";
            case LEFT:
                return "LEFT";
        }
        return  null;
    }

    public JSONObject getReply(){
        JSONObject reply = new JSONObject(getStatus());
        JSONObject state = new JSONObject();

        state.put("position", getPositionState());
        state.put("direction", getDirectionString());
        state.put("shields", getHealth());
        state.put("shots", getBullets());

        reply.put("state", state);
        return null;
    }
}

