package za.wethinkcode.RobotWorlds.commands;

import za.wethinkcode.RobotWorlds.worldLogic.Robot;
import za.wethinkcode.RobotWorlds.Database.DbConnect;

import java.sql.SQLException;

public class RestoreCommand extends Command {

    private final String worldName;

    public RestoreCommand(String worldName) {
        super("restore");
        this.worldName = worldName;
    }

    @Override
    public boolean execute(Robot target) {
        try {
            DbConnect dbConnect = new DbConnect();
            String worldData = dbConnect.restoreWorld(worldName);
            if (worldData != null) {
                target.setWorldData(worldData); // Update robot's state
                System.out.printf("World restored: " + worldName);
                return true;
            } else {
                target.setStatus("Failed to restore world: World not found.");
                return false;
            }
        } catch (SQLException e) {
            target.setStatus("Failed to restore world: " + e.getMessage());
            System.err.println("Error restoring world: " + e.getMessage());
            return false;
        }
    }
}
