//package za.wethinkcode.Tests.AcceptanceTest;
//
//import org.junit.jupiter.api.*;
//import za.wethinkcode.RobotWorlds.Database.DatabaseManager;
//import za.wethinkcode.RobotWorlds.Database.DbConnect;
//import za.wethinkcode.RobotWorlds.commands.SaveCommand;
//import za.wethinkcode.RobotWorlds.worldLogic.Robot;
//import za.wethinkcode.RobotWorlds.commands.RestoreCommand;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class RestoreCommandTest {
//    private DbConnect dbConnect;
//    private Robot robot;
//    private Robot testRobot;
//
//
//    @BeforeEach
//    public void setUp() throws SQLException {
//        dbConnect = new DbConnect();
//        testRobot = new Robot("testRobot");
//
//        // Clear the database before each test
//        clearDatabase();
//    }
//
//    @AfterEach
//    public void tearDown() throws SQLException {
//        dbConnect = null;
//        testRobot = null;
//    }
//
//    private void clearDatabase() {
//        try (Connection conn = DatabaseManager.getConnection();
//             PreparedStatement stmt = conn.prepareStatement("DELETE FROM worlds")) {
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    @Test
//    public void testRestoreCommandExecutesSuccessfully() {
//        // Mock data to simulate world restoration
//        String mockWorldData = "{\"position\": [1, 2], \"visibility\": 10}";
//
//        // Assuming restoreWorld is static and changes the robot's world data directly
//        DbConnect.restoreWorld("TestWorld", robot);
//
//        // Assert that the robot's world data is set
//        assertNotNull(robot.getWorldData());
//        assertEquals("World 'TestWorld' restored successfully.", robot.getStatus());
//    }
//
//    @Test
//    public void testRestoreCommandFailsDueToWorldNotFound() {
//        // Simulate a world that does not exist
//        DbConnect.restoreWorld("NonExistentWorld", robot);
//
//        // Assert that the robot's world data is null and status is set to not found
//        assertNull(robot.getWorldData());
//        assertEquals("Failed to restore world: World not found.", robot.getStatus());
//    }
//
//    @Test
//    public void testRestoreCommand() {
//        // Set up initial data in the database
//        dbConnect.saveWorld("TestWorld", "{\"position\": [1, 1], \"status\": \"OK\"}");
//
//        // Create and execute the RestoreCommand
//        RestoreCommand restoreCommand = new RestoreCommand("TestWorld");
//        assertTrue(restoreCommand.execute(testRobot));
//
//        // Verify that the robot's world data is restored correctly
//        String expectedData = "{\"position\": [1, 1], \"status\": \"OK\"}";
//        assertEquals(expectedData, testRobot.getWorldData());
//    }
//}
