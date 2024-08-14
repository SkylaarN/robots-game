package za.wethinkcode.RobotWorlds.commands;

import za.wethinkcode.RobotWorlds.worldLogic.Obstacles;
import za.wethinkcode.RobotWorlds.worldLogic.Robot;
import za.wethinkcode.RobotWorlds.Database.DbConnect;
import za.wethinkcode.RobotWorlds.worldLogic.SquareObstacle;

import java.sql.SQLException;
import java.util.List;

public class SaveCommand extends Command {

    private final String worldName;

    public SaveCommand() {
        super("save");
        this.worldName = "Default";
    }

    public SaveCommand(String worldName) {
        super("save");
        this.worldName = worldName;
    }

    @Override
    public boolean execute(Robot target) {
        // Get the list of obstacles from the world
        List<SquareObstacle> obstacles = Obstacles.getObstacles();

        // Convert world data to JSON or other format if needed
        String worldData = ""; // Placeholder, adjust as necessary for your use case

        try {
            // Create a new DbConnect instance
            DbConnect dbConnect = new DbConnect();
            dbConnect.createTables();

            // Save the world name and its obstacles
            dbConnect.saveWorld(worldName, worldData, obstacles); // Ensure correct method signature

            // Set status and indicate success
            target.setStatus("World '" + worldName + "' and obstacles saved successfully.");
            System.out.println("World and obstacles saved as: " + worldName);

            return true;

        } catch (SQLException e) {
            // Handle SQL exceptions
            target.setStatus("Failed to save world: " + e.getMessage());
            System.err.println("Error saving world: " + e.getMessage());
            return false;
        }
    }
}
