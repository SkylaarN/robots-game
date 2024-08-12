package za.wethinkcode.RobotWorlds.commands;

import za.wethinkcode.RobotWorlds.worldLogic.Obstacles;
import za.wethinkcode.RobotWorlds.worldLogic.Robot;
import za.wethinkcode.RobotWorlds.Database.DbConnect;
import za.wethinkcode.RobotWorlds.worldLogic.SquareObstacle;

import java.sql.SQLException;

public class SaveCommand extends Command {

    private final String worldName;

    public SaveCommand(){
        super("save");
        this.worldName = "Default";
    }
    public SaveCommand(String worldName) {
        super("save");
        this.worldName = worldName;
    }

    @Override
    public boolean execute(Robot target) {
        String worldDataJson = target.getWorldData(); // Convert the robot's world state to a JSON string


//        DbConnect dbConnect = null;
        try {
            DbConnect dbConnect = new DbConnect();
            dbConnect.saveWorld(worldName, worldDataJson);
            target.setStatus("World '" + worldName + "' saved successfully.");
            System.out.println("World saved as: " + worldName);
            return false;
        } catch (SQLException e) {
            System.out.println("Tooslki");
            target.setStatus("Failed to save world: " + e.getMessage());
            System.err.println("Error saving world: " + e.getMessage());
            return false;
        }
    }
}
