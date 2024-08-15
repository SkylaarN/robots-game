//package za.wethinkcode.Tests.AcceptanceTest;
//
//
//import org.junit.jupiter.api.*;
//import za.wethinkcode.RobotWorlds.Database.DbConnect;
//import za.wethinkcode.RobotWorlds.worldLogic.Robot;
//import za.wethinkcode.RobotWorlds.commands.SaveCommand;
//
//import java.sql.SQLException;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class SaveCommandTest {
//
//    private DbConnect dbConnect;
//    private Robot robot;
//    private SaveCommand saveCommand;
//
//    @BeforeEach
//    public void setUp() {
//        // Mock the database connection
//        dbConnect = mock(DbConnect.class);
//
//        // Create a new robot instance
//        robot = new Robot("TestRobot");
//
//        // Create the SaveCommand with the mock dbConnect
//        saveCommand = new SaveCommand("TestWorld") {
//            @Override
//            public boolean execute(Robot target) {
//                String worldDataJson = target.getWorldData();
//
//                try {
//                    dbConnect.saveWorld("TestWorld", worldDataJson);
//                    target.setStatus("World '" + "TestWorld" + "' saved successfully.");
//                    return true;
//                } catch (Exception e) {
//                    target.setStatus("Failed to save world: " + e.getMessage());
//                    return false;
//                }
//            }
//        };
//    }
//
//    @Test
//    public void testSaveCommandExecutesSuccessfully() throws SQLException {
//        // Mock the behavior of saveWorld method to do nothing (simulate success)
//        doNothing().when(dbConnect).saveWorld(eq("TestWorld"), anyString());
//
//        // Execute the command
//        boolean result = saveCommand.execute(robot);
//
//        // Verify the interaction with the database
//        verify(dbConnect, times(1)).saveWorld(eq("TestWorld"), anyString());
//
//        // Assert the command executed successfully
//        assertTrue(result);
//        assertEquals("World 'TestWorld' saved successfully.", robot.getStatus());
//    }
//
////    @Test
////    public void testSaveCommandFailsDueToDatabaseError() throws SQLException {
////        // Mock the behavior of saveWorld method to throw an SQLException
////        doThrow(new SQLException("Database error")).when(dbConnect).saveWorld(eq("TestWorld"), anyString());
////
////        // Execute the command
////        boolean result = saveCommand.execute(robot);
////
////        // Verify the interaction with the database
////        verify(dbConnect, times(1)).saveWorld(eq("TestWorld"), anyString());
////
////        // Assert the command failed
////        assertFalse(result);
////        assertEquals("Failed to save world: Database error", robot.getStatus());
////    }
//
//    @AfterEach
//    public void tearDown() {
//
//    }
//}
