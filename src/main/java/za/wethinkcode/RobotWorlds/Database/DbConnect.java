package za.wethinkcode.RobotWorlds.Database;

import java.sql.*;

import za.wethinkcode.RobotWorlds.worldLogic.Robot;
/**
 * DbTest is a small command-line tool used to check that we can connect to a SQLite database.
 *
 * By default (without any command-line arguments) it attempts to create a SQLite table in an in-memory database.
 * If it succeeds, we assume that all the working parts we need to use SQLite databases are in place and working.
 *
 * The only command-line argument this app understands is
 *  `-f <filename>`
 *  which tells that application to create the test table in a real (disk-resident) database named by the given
 *  filename. Note that the application _does not delete_ the named file, but leaves it in the filesystem
 *  for later examination if desired.
 */

public class DbConnect {
    private static final String DB_URL = "jdbc:sqlite:robotworlds.db";
    private Connection conn;

    public static void initialize(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS RobotWorlds (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "world_name TEXT NOT NULL UNIQUE, " +
                            "world_data TEXT NOT NULL)")) {
                stmt.execute();
            }
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }



    public DbConnect() throws SQLException {
        this.conn = DatabaseManager.getConnection();
    }

    public void saveWorld(String worldName, String worldDataJson) {
        String query = "INSERT INTO worlds (world_name, world_data) VALUES (?, ?) " +
                "ON CONFLICT(world_name) DO UPDATE SET world_data = excluded.world_data";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, worldName);
            pstmt.setString(2, worldDataJson);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void restoreWorld(String worldName, Robot robot) {
        Connection conn = null;
        String query = "SELECT world_data FROM worlds WHERE world_name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, worldName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String worldData = rs.getString("world_data");
                    robot.setWorldData(worldData);
                } else {
                    System.out.println("World not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}