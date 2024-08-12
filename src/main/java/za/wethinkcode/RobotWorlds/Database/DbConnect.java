package za.wethinkcode.RobotWorlds.Database;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


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
    public static final String IN_MEMORY_DB_URL = "jdbc:sqlite::memory:";
    private static final String DB_URL = "jdbc:sqlite:robotworlds.sqlite";
    private Connection conn;

    //    static {
////        try {
////            Class.forName("org.sqlite.JDBC"); // Load the SQLite JDBC driver
////        } catch (ClassNotFoundException e) {
////            System.err.println("Error loading SQLite JDBC Driver: " + e.getMessage());
////        }
////    }
    public static void main(String[] args) throws SQLException {
        DbConnect dbConnect = new DbConnect();

        // Example world name
        String worldName = "TestWorld";


        String worldDataJson = "";

        // Saving the world data
        dbConnect.saveWorld(worldName, worldDataJson);
        System.out.println("World data saved successfully.");

        // Restoring the world data
        String restoredWorldDataJson = dbConnect.restoreWorld(worldName);

        // Check if the world data was successfully retrieved
        if (restoredWorldDataJson != null) {
            System.out.println("Restored World Data: " + restoredWorldDataJson);

            // Further processing the restored JSON data if necessary
            JSONObject worldData = new JSONObject(restoredWorldDataJson);
            JSONArray robots = worldData.getJSONArray("robots");
            JSONArray obstacles = worldData.getJSONArray("obstacles");

            System.out.println("Number of Robots: " + robots.length());
            System.out.println("Number of Obstacles: " + obstacles.length());

            // You can use the restored data to reconstruct the world or initialize game elements here

        } else {
            System.out.println("Failed to restore the world data for: " + worldName);
        }

        // Close the database connection if needed
        dbConnect.close();
    }


    public DbConnect() throws SQLException {
        this.conn = DriverManager.getConnection(DB_URL);
    }

    public void saveWorld(String worldName, String worldDataJson) {
        String query = "INSERT INTO RobotWorlds (world_name, world_data) VALUES (?, ?) " +
                "ON CONFLICT(world_name) DO UPDATE SET world_data = excluded.world_data";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, worldName);
            pstmt.setString(2, worldDataJson);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
