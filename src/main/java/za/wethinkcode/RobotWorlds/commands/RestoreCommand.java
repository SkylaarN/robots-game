package za.wethinkcode.RobotWorlds.commands;

import org.json.JSONArray;
import za.wethinkcode.RobotWorlds.worldLogic.Robot;
import za.wethinkcode.RobotWorlds.Database.DbConnect;

public class RestoreCommand extends Command{

    private final String worldName;

    public RestoreCommand(String worldName){
        super("restore");
        this.worldName = worldName;
    }

    @Override
    public boolean execute(Robot target){
        try{
            DbConnect.restoreWorld(worldName, target);
            System.out.printf("World restored: "+ worldName);
            return true;
        }catch(Exception e){
            System.err.println("Error restoring world: " + e.getMessage());
            return false;
        }
    }
}
