package za.wethinkcode.RobotWorlds.commands;


import org.json.JSONArray;
import za.wethinkcode.RobotWorlds.worldLogic.Robot;
import za.wethinkcode.RobotWorlds.Database.DbConnect;

import java.sql.SQLException;


public class SaveCommand extends Command {

    private final String worldName;

    public SaveCommand(String worldName){
        super("save");
        this.worldName = worldName;
    }



    @Override
    public boolean execute(Robot target) {
        String worldDataJson = target.getWorldData(); // Convert the robot's world state to a JSON string

        try {
            DbConnect dbConnect = new DbConnect();
            dbConnect.saveWorld(worldName, worldDataJson);
            target.setStatus("World '" + worldName + "' saved successfully.");
            System.out.println("World saved as: " + worldName);
            return true;
        } catch (SQLException e) {
            target.setStatus("Failed to save world: " + e.getMessage());
            System.err.println("Error saving world: " + e.getMessage());
            return false;
        }
    }
}
