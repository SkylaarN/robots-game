package za.wethinkcode.RobotWorlds;
import edu.princeton.cs.introcs.StdDraw;
import za.wethinkcode.RobotWorlds.commands.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Robot {

    private Position position;
    private Direction currentDirection;
    public String name;
    private String status;

    public static Random random = new Random();
    int color1 = random.nextInt(255);
    int color2 = random.nextInt(255);
    int color3 = random.nextInt(255);

    enum Direction{
        UP, RIGHT, DOWN, LEFT
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

    void handleCommand(String com){
        StdDraw.setPenColor(color1, color2, color3);
        String[] args = com.toLowerCase().trim().split(" ");
        Position next;
        int num;
        Command command;
        switch(args[0]){

            case "forward":
                command = new ForwardCommand(args[1]);
                command.execute(this);
                break;

            case "back":
                command = new BackCommand(args[1]);
                command.execute(this);
                break;

            case "right":
                command = new RightCommand();
                command.execute(this);
                break;

            case "left":
                command = new LeftCommand();
                command.execute(this);
                break;

            default:
                System.out.println("Unsupported command: " + com);
                break;
        }
    }

    public void setPosition(Position pos){
        this.position = pos;
    }

    public Position getPosition(){
        return this.position;
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
    public String updatePosition(int nrSteps) {
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
                return "UpdateResponse.FAILED_OBSTRUCTED";
            }
            else if (Obstacles.blocksPath(this.position, newPosition)){
                return "UpdateResponse.FAILED_OBSTRUCTED";
            }
            else{

                StdDraw.line(0.5 + position.getX()/512.0,
                0.5 + position.getY()/512.0,
                0.5 + newPosition.getX()/512.0,
                0.5 + newPosition.getY()/512.0);

                }
                this.position = newPosition;
                return "UpdateResponse.SUCCESS";
            }
        return "UpdateResponse.FAILED_OBSTRUCTED";
    }

    public boolean isNewPositionAllowed(Position position) {
        /** Function checks to see if Position isIn the specified area
         *
         * @param position the Position of the new position of the robot
         * @return boolean if the Position is in the area
         */

//        if (position.isIn(TOP_LEFT,BOTTOM_RIGHT)) {
//            return true;
//        }
//        else{
//            return false;
//        }
        return true;
    }

    public static void drawBorder(){
        StdDraw.setPenRadius(0.003);
        StdDraw.setPenColor(StdDraw.MAGENTA);
        StdDraw.line(0.2, 0.2, 0.8, 0.2);
        StdDraw.line(0.2, 0.8, 0.8, 0.8);
        StdDraw.line(0.2, 0.2, 0.2, 0.8);
        StdDraw.line(0.8, 0.2, 0.8, 0.8);
    }

    public static void drawObstacles(){
        ArrayList<SquareObstacle> obstacles = Obstacles.getObstacles();
        StdDraw.setPenColor(Color.RED);
        for (int i = 0; i < obstacles.size(); i++) {
            SquareObstacle sqrObs = obstacles.get(i);
            int x = sqrObs.getBottomLeftX();
            int y = sqrObs.getBottomLeftY();
            StdDraw.line(0.5 + x/512.0, 0.5 + y/512.0, 0.5 + (x + 4)/512.0, 0.5 + y/512.0);
            StdDraw.line(0.5 + x/512.0, 0.5 + y/512.0, 0.5 + x/512.0, 0.5 + (y + 4)/512.0);
            StdDraw.line(0.5 + x/512.0, 0.5 + (y + 4)/512.0, 0.5 + (x + 4)/512.0, 0.5 + (y + 4)/512.0);
            StdDraw.line(0.5 + (x + 4)/512.0, 0.5 + y/512.0, 0.5 + (x + 4)/512.0, 0.5 + (y + 4)/512.0);

        }

    }
}
