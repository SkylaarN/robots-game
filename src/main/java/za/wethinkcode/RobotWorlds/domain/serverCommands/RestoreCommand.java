package za.wethinkcode.RobotWorlds.domain.serverCommands;

import za.wethinkcode.RobotWorlds.domain.ClientCommands.Command;
import za.wethinkcode.RobotWorlds.domain.world.Obstacles;
import za.wethinkcode.RobotWorlds.domain.world.Robot;
import za.wethinkcode.RobotWorlds.domain.world.SquareObstacle;
import za.wethinkcode.RobotWorlds.Database.DbConnect;

import java.sql.SQLException;
import java.util.List;

public class RestoreCommand extends Command {

    private final String worldName;

    public RestoreCommand(String worldName) {
        super();
        this.worldName = worldName;
    }

    @Override
    public boolean execute(Robot target) {
        try {
            DbConnect dbConnect = new DbConnect();
            List<SquareObstacle> restoredObstacles = dbConnect.restoreObstacles(worldName);
            if (restoredObstacles != null && !restoredObstacles.isEmpty()) {
                Obstacles.setObstacles(restoredObstacles);
                System.out.println("Obstacles restored for world: " + worldName);
                return true;
            } else {
                target.setStatus("Failed to restore obstacles: World not found or no obstacles saved.");
                return false;
            }
        } catch (SQLException e) {
            target.setStatus("Failed to restore obstacles: " + e.getMessage());
            System.err.println("Error restoring obstacles: " + e.getMessage());
            return false;
        }
    }
}
