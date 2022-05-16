package za.wethinkcode.RobotWorlds.commands;

import za.wethinkcode.RobotWorlds.Obstacles;
import za.wethinkcode.RobotWorlds.Robot;
import za.wethinkcode.RobotWorlds.SquareObstacle;

public class LookCommand extends Command{

    public enum AbsolutePosition{
        NORTH, EAST, SOUTH, WEST
    }

    public boolean execute(Robot target){
        StringBuilder output = new StringBuilder();

        for(int i = 0; i < Obstacles.getObstacles().size(); i++){
            SquareObstacle obs = Obstacles.obstacles.get(i);
            int x = obs.getBottomLeftX();
            int y = obs.getBottomLeftY();

            if (x <= target.getPosition().getX() && target.getPosition().getX() <= x + 4 ||
                    y <= target.getPosition().getY() && target.getPosition().getY() <= y + 4){

                if ((x - target.getPosition().getX() <= 150)
                        && (x - target.getPosition().getX() >= -150)
                        && (y - target.getPosition().getY() <= 150)
                        && (y - target.getPosition().getY() >= -150)){

                    output.append((String.format("There's an obstacle at position %s,%s (to %s,%s), %s",
                            x, y, (x+4), (y+4), getAbsolutePosition(target, x, y))) + "\n");
                }
            }
            System.out.println(String.format("-############ At position %s,%s (to %s,%s)",
                    x, y, (x+4), (y+4)));
        }
        if(output.length() == 0){
            target.setStatus("No obstacles.");
        }
        else{
            target.setStatus(output.toString().trim());
        }
        return true;
    }

    public AbsolutePosition getAbsolutePosition(Robot target, int x, int y){

        if(target.getPosition().getY() < y){
            return AbsolutePosition.NORTH;
        }
        else if (target.getPosition().getY() > y){
            return AbsolutePosition.SOUTH;
        }
        else if(target.getPosition().getX() < x){
            return AbsolutePosition.EAST;
        }
        else{
            return AbsolutePosition.WEST;
        }

    }
    public LookCommand(){
        super("look");
    }
}
