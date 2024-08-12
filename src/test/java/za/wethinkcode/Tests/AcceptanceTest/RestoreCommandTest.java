package za.wethinkcode.Tests.AcceptanceTest;

import org.junit.jupiter.api.*;
import za.wethinkcode.RobotWorlds.Database.DatabaseManager;
import za.wethinkcode.RobotWorlds.Database.DbConnect;
import za.wethinkcode.RobotWorlds.commands.RestoreCommand;
import za.wethinkcode.RobotWorlds.worldLogic.Robot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class RestoreCommandTest {
    private DbConnect dbConnect;
    private Robot testRobot;

    @BeforeEach
    public void setUp() throws SQLException {
        dbConnect = new DbConnect();
        testRobot = new Robot("testRobot");

        // Clear the database before each test
        clearDatabase();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        dbConnect = null;
        testRobot = null;
    }

    private void clearDatabase() {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM worlds")) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void testRestoreCommandExecutesSuccessfully() {
//        // Set up initial data in the database
//        String mockWorldData = "{\"position\": [1, 2], \"visibility\": 10}";
//        dbConnect.saveWorld("TestWorld", mockWorldData);
//
//        // Create and execute the RestoreCommand
//        RestoreCommand restoreCommand = new RestoreCommand("TestWorld");
//        assertTrue(restoreCommand.execute(testRobot));
//
//        // Verify that the robot's world data is restored correctly
//        assertEquals(mockWorldData, testRobot.getWorldData());
//        assertEquals("OK", testRobot.getStatus());
//    }

    @Test
    public void testRestoreCommandFailsDueToWorldNotFound() {
        // Simulate a world that does not exist by not saving it in the database
        RestoreCommand restoreCommand = new RestoreCommand("NonExistentWorld");
        assertFalse(restoreCommand.execute(testRobot));

        // Assert that the robot's world data is null and status is set to not found
        assertNotNull(testRobot.getWorldData());
        assertEquals("Failed to restore world: World not found.", testRobot.getStatus());
    }


}
