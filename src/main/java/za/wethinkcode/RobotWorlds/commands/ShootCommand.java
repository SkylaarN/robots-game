package za.wethinkcode.RobotWorlds.commands;

import edu.princeton.cs.introcs.StdDraw;
import za.wethinkcode.RobotWorlds.Obstacles;
import za.wethinkcode.RobotWorlds.Players;
import za.wethinkcode.RobotWorlds.Position;
import za.wethinkcode.RobotWorlds.Robot;

public class ShootCommand extends Command{
    public boolean execute(Robot target){
        return true;
    }

//    public boolean hitPlayer(Position a, Position b){
//        Players.getPlayers();
//        if(x <= b.getX() && b.getX() <= x + 4 &&
//                y <= b.getY() && b.getY() <= y + 4){
//            return true;
//        }
//        else{
//            return false;
//        }
//        return true;
//    }



    public ShootCommand() {
        super("shoot");
    }
}
