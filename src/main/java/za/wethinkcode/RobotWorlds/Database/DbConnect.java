package za.wethinkcode.RobotWorlds.Database;

import za.wethinkcode.RobotWorlds.domain.world.SquareObstacle;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbConnect {
    private static final String DB_URL = "jdbc:sqlite:robotworlds.sqlite";
    private Connection conn;

    public DbConnect() throws SQLException {
        this.conn = DriverManager.getConnection(DB_URL);
    }

    public void createTables() throws SQLException {
        String createWorldsTable = "CREATE TABLE IF NOT EXISTS RobotWorlds (" +
                "world_name TEXT PRIMARY KEY," +
                "world_data TEXT);";  // Added world_data

        String createObstaclesTable = "CREATE TABLE IF NOT EXISTS Obstacles (" +
                "obstacle_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "world_name TEXT," +
                "position_x INTEGER NOT NULL," +
                "position_y INTEGER NOT NULL," +
                "size INTEGER NOT NULL," +
                "FOREIGN KEY (world_name) REFERENCES RobotWorlds(world_name) ON DELETE CASCADE);";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createWorldsTable);
            stmt.execute(createObstaclesTable);
        }
    }

    public void saveWorld(String worldName, String worldData, List<SquareObstacle> obstacles) {
        // Save the world (name and data) to the RobotWorlds table
        String worldQuery = "INSERT INTO RobotWorlds (world_name, world_data) VALUES (?, ?) " +
                "ON CONFLICT(world_name) DO UPDATE SET world_data = excluded.world_data";
        try (PreparedStatement pstmtWorld = conn.prepareStatement(worldQuery)) {
            pstmtWorld.setString(1, worldName);
            pstmtWorld.setString(2, worldData);  // Save world data
            pstmtWorld.executeUpdate();

            // Save the obstacles for this world
            saveObstacles(worldName, obstacles);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void saveObstacles(String worldName, List<SquareObstacle> obstacles) throws SQLException {
        String query = "INSERT INTO Obstacles (world_name, position_x, position_y, size) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (SquareObstacle obstacle : obstacles) {
                pstmt.setString(1, worldName);
                pstmt.setInt(2, obstacle.getBottomLeftX());
                pstmt.setInt(3, obstacle.getBottomLeftY());
                pstmt.setInt(4, obstacle.getSize());
                pstmt.executeUpdate();
            }
        }
    }

    public  List<SquareObstacle> restoreObstacles(String worldName) throws SQLException {
        List<SquareObstacle> obstacles = new ArrayList<>();
        String query = "SELECT position_x, position_y, size FROM Obstacles WHERE world_name = ?";

        Map<Integer, String> obstacleMap = new HashMap<>();
        List<SquareObstacle> obstacles1 = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, worldName);
            int i = 1;
            try (ResultSet rs = pstmt.executeQuery()) {
//
                while (rs.next()) {
                    int x = rs.getInt("position_x");
                    int y = rs.getInt("position_y");
                    int size = rs.getInt("size");

                    String obstacleData = "(x: " + x + ", y: " + y + "), ";
//
                    obstacleMap.put(i, obstacleData);


                    i++;  // Increment the counter

                    obstacles.add(new SquareObstacle(x, y)); // Assuming size is always the same
                }

                System.out.println(obstacleMap);
            }
        }
        return obstacles;
    }


    public String restoreWorld(String worldName) {
        String query = "SELECT world_data FROM RobotWorlds WHERE world_name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, worldName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("world_data");
                } else {
                    System.out.println("World not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
