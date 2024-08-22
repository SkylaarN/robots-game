package za.wethinkcode.RobotWorlds.domain.world;

import org.json.JSONArray;
import org.json.JSONObject;
import za.wethinkcode.RobotWorlds.domain.ClientCommands.Command;
import za.wethinkcode.RobotWorlds.domain.world.configuration.Configuration;
import za.wethinkcode.RobotWorlds.server.Players;
import za.wethinkcode.RobotWorlds.server.SimpleServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Robot {

    private Position position;
    private Direction currentDirection;
    public String name;
    private String status;
    private String statusType = "NORMAL";
    private final Configuration configuration = new Configuration();

    public static ArrayList<Integer> listpos =new ArrayList<>(0);
    private int bullets = 8;
    private int visibility = 8;

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }


    /**
     * Restore the world with data saved on a database
     */
    public void setWorldData(String worldDataJson) {
        // Restore the robot's world state from a JSON string
        JSONObject worldData = new JSONObject(worldDataJson);
        JSONArray position = worldData.getJSONArray("position");
        setPosition(new Position(position.getInt(0), position.getInt(1)));
        setCurrentDirection(Direction.valueOf(worldData.getString("direction").toUpperCase()));
        setHealth(worldData.getInt("shields"));
        setBullets(worldData.getInt("shots"));
        // Restore other relevant state information
    }

    private void setCurrentDirection(Direction direction) {
            this.currentDirection = direction;
    }

    /**
     * This method gets the world's data or state
     * And formats it properly for database.
     */
    public String getWorldData() {
        // Convert the robot's world state to a JSON string
        JSONObject worldData = new JSONObject();
        worldData.put("position", getPositionState());
        worldData.put("direction", getDirectionString());
        worldData.put("shields", getHealth());
        worldData.put("shots", getBullets());
        return worldData.toString();
    }

    public enum Direction{
        UP, RIGHT, DOWN, LEFT
    }

    /**
     * Condition for moving the robot
     */
    public enum Conditions {

        SUCCESS, FAILED_OBSTACLE_DETECTED, FAILED_OUTSIDE_ROBOT_WORLD
    }


    /**
     * set up the robot
     * @param name is the name given to the robot
     */
    public Robot(String name){
        Position pos =  new Position(15,15);
        int x = pos.getX();


        int random_x = (int)(Math.random() * pos.getX()+1);
        listpos.add(random_x);

        if(random_x==listpos.getFirst()){
            if(random_x+5> pos.getX())
                random_x-=5;
            else{
                random_x+=5;
            }
        }
        int random_y = (int)(Math.random() * pos.getY()+1);


        this.position = new Position(random_x, random_y);
        this.currentDirection = Direction.UP;
        this.name = name;



        //StdDraw.setPenColor(random.nextInt(255), random.nextInt(255), random.nextInt(255));

    }

    /**
     * sets the status of the robot
     * @param status is the status of the robot
     */
    public void setStatus(String status){
        this.status = status;
    }

    /**
     * gets the status of the robot
     * @return status of the robot
     */
    public String getStatus(){
        return this.status;
    }

    /**
     * handles the commands sent to the robots
     * @param com instruction sent to the robot
     * @param args arguments of the command
     */
    public void handleCommand(String com, JSONArray args){
        //StdDraw.setPenColor(color1, color2, color3);
        Command command = Command.createCommand(com, args);

        command.execute(this);
    }

    /**
     * sets the position of the robot
     * @param pos position of the robot
     */
    public void setPosition(Position pos){
        this.position = pos;
    }

    /**
     * returns the position of the robot
     * @return position of the robot
     */
    public Position getPosition(){
        return this.position;
    }

    /**
     * get the current direction of the robot
     * @return robot direction
     */
    public Direction getCurrentDirection(){
        return this.currentDirection;
    }

    /**
     * gets the name of the robot
     * @return name of the robot
     */
    public String getName(){
        return this.name;
    }



    /**Function changes the current direction of the robot depending
     * on if it is told to move left or right
     *
     * @param turnRight boolean containing if the robot turns left or right
     */
    public void updateDirection(boolean turnRight) {


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

    /**Function gets current direction to check if the new Position is in
     * an unblocked position and if it is, move to the new position and draw
     * a line to the next position with the robotTurtle
     *
     * @param nrSteps int value of the number of steps to the next Position
     * @return UpdateResponse of the check to see if it moves or not
     */
    public Conditions updatePosition(int nrSteps) {


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

            this.position = newPosition;
            return Conditions.SUCCESS;
        }
        return Conditions.FAILED_OUTSIDE_ROBOT_WORLD;
    }

    /** Function checks to see if Position isIn the specified area
     *
     * @param position the Position of the new position of the robot
     * @return boolean if the Position is in the area
     */
    public boolean isNewPositionAllowed(Position position) {


        if (position.isIn(new Position(-150, 150),new Position(150, -150))) {
            return true;
        }
        else{
            return false;
        }
    }


    /**
     * Record the damages dealt to the robot
     */
    public void damage(Robot playerRobot){

        this.configuration.reduceHealth();
        if(getHealth() == 0){
            setStatusType("DEAD");
            Players.getPlayers().remove(playerRobot);
            SimpleServer.listRobots.remove(playerRobot.getName());
            try {
                Players.robotsSSSS.get(playerRobot.getName()).close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * records the health of the robot
     * @return remaining health of the robot
     */
    public int getHealth() {

        return this.configuration.getHealth();
    }

    /**
     * sets the health of the robot
     * @param health health status
     */
    public void setHealth(int health){


        this.configuration.setHealth(health);
    }

    /**
     * shoot bullets
     */
    public void fire(){
        this.bullets = this.bullets - 1;
    }

    /**
     * gets the robot bullets
     * @return gun bullets
     */
    public int getBullets(){
        return this.bullets;
    }

    /**
     * loads the bullets
     * @param bullets number
     */
    public void setBullets(int bullets){
        this.bullets = bullets;
    }

    /**
     * get robot position update
     * @return Json array
     */
    public JSONArray getPositionState(){
        JSONArray positionState = new JSONArray();
        positionState.put(getPosition().getX());
        positionState.put(getPosition().getY());
        return positionState;
    }

    /**
     * gets the direction of the robot
     * @return robot direction
     */
    public String getDirectionString(){
        switch(getCurrentDirection()){
            case UP:
                return "NORTH";
            case DOWN:
                return "SOUTH";
            case RIGHT:
                return "RIGHT";
            case LEFT:
                return "LEFT";
        }
        return  null;
    }

    /**
     * gets the reply from the server
     * @return reply JSON Object
     */
    public JSONObject getReply(){
        JSONObject reply = new JSONObject(getStatus());
        JSONObject state = new JSONObject();

        List<Object> num = new ArrayList<>();
        num.add(position.getX());
        num.add(position.getY());

        state.put("position", num.toString());
        state.put("direction", getDirectionString());
        state.put("shields", getHealth());
        state.put("shots", getBullets());
        state.put("status", this.statusType);

        Map<String, List<Object>> result;
        result = new HashMap<>();
        result.put("position", num);
        reply.put("state", state);
        return reply;
    }

    /**
     * sets the status type
     * @param statusType the status type string
     */
    public void setStatusType(String statusType){
        this.statusType = statusType;
    }

    /**
     * report status type
     * @return status types
     */
    public String getStatusType(){
        return this.statusType;
    }
}


