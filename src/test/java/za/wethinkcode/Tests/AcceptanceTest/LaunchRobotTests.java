//package za.wethinkcode.Tests.AcceptanceTest;
//import com.fasterxml.jackson.databind.JsonNode;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class LaunchRobotTests {
//    /**
//     * As a player
//     * I want to launch my robot in the online robot world
//     * So that I can break the record for the most robot kills
//     *
//     * Scenario: Robot with the same name is already in the world
//     *
//     * Scenario: Can launch another robot
//     * Scenario: World without obstacles is full
//     * Scenario: Launch robots into a world with an obstacle
//     * Scenario: World with an obstacle is full
//     */
//
//        private final static int DEFAULT_PORT = 6000;
//        private final static String DEFAULT_IP = "localhost";
//        private final RobotWorldClient serverClient = new RobotWorldJsonClient();
//
//        @BeforeEach
//        void connectToServer(){
//            System.out.println("-------------------");
//            serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
//            System.out.println(serverClient.isConnected());
//            System.out.println("-------------------");
//        }
//
//        @AfterEach
//        void disconnectFromServer(){
//            serverClient.disconnect();
//        }
//
//    public JsonNode sendCommand(String name, String command, String arguments){
//        if (!serverClient.isConnected()){
//            connectToServer();
//        }
//        String launchRequest = "{" +
//                "  \"robot\": \""+name+"\"," +
//                "  \"command\": \""+command+"\"," +
//                "  \"arguments\": [\""+arguments+"\"]" +
//                "}";
//        return serverClient.sendRequest(launchRequest);
//    }
//
//    @Test
//        void validLaunchShouldSucceed(){
//        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
//
//
//            // Given that I am connected to a running Robot Worlds server
//            // And the world is of size 1x1 (The world is configured or hardcoded to this size)
//
//            // When I send a valid launch request to the server
//
//            JsonNode response = sendCommand("HAL", "launch", "shooter");
//
//            // Then I should get a valid response from the server
//            assertNotNull(response.get("result"));
//            assertEquals("OK", response.get("result").asText());
//
//            // And the position should be (x:0, y:0)
//            assertNotNull(response.get("data"));
//            assertNotNull(response.get("data").get("position"));
//
//            // And I should also get the state of the robot
//            assertNotNull(response.get("state"));
//            disconnectFromServer();
//        }
//
//
//    @Test
//    void launchAnotherBot() {
//            JsonNode robot1 = sendCommand("Hal", "launch", "shooter");
//            assertNotNull(robot1.get("result"));
//            assertEquals("OK", robot1.get("result").asText());
//
//
//            JsonNode robot2 = sendCommand("via", "launch", "shooter");
//            assertNotNull(robot2.get("result"));
//            assertEquals("OK", robot2.get("result").asText());
//    }
//
//    @Test
//    void launchBotWithSameName() {
//        // Given that I am connected to a running Robot Worlds server
//
//        // When I send a valid launch request for another robot
//        String request = "{" +
//                "  \"robot\": \"HAL\"," +
//                "  \"command\": \"launch\"," +
//                "  \"arguments\": [\"shooter\"]" +
//                "}";
//        JsonNode response = serverClient.sendRequest(request);
//
//        // Launch the first robot to ensure it doesn’t affect this test
//        String launchRequest = "{" +
//                "  \"robot\": \"HAL\"," +
//                "  \"command\": \"launch\"," +
//                "  \"arguments\": [\"shooter\"]" +
//                "}";
//
//        // Then I should get a valid response from the server
//        assertNotNull(response.get("result"));
//        System.out.println( response.get("result").asText());
//        assertEquals("OK", response.get("result").asText());
//
//        JsonNode launchResponse = serverClient.sendRequest(launchRequest);
//        System.out.println(launchResponse.get("result").asText());
////        assertEquals("ERROR", launchResponse.get("result").asText());
//        System.out.println(launchResponse.get("data").get("message").asText());
////        assertEquals("Too many of you in this world", launchResponse.get("data").get("message").asText());
//    }
//
//    @Test
//    void launchBotInWorldWithObstacle() {
//        // Given that I am connected to a running Robot Worlds server
//
//        // When I send a valid launch request to the server
//        String request = "{" +
//                "  \"robot\": \"HAL\"," +
//                "  \"command\": \"launch\"," +
//                "  \"arguments\": [\"shooter\"]" +
//                "}";
//        JsonNode response = serverClient.sendRequest(request);
//
//        // Then I should get a valid response from the server
//        assertNotNull(response.get("result"));
//        assertEquals("OK", response.get("result").asText());
//
//        // And the position should be (x:0, y:0)
//        assertNotNull(response.get("data"));
//        assertNotNull(response.get("data").get("position"));
////        assertEquals(0, response.get("data").get("position").get(0).asInt());
//        // Uncomment if needed
//        // assertEquals(0, response.get("data").get("position").get(1).asInt());
//
//        // And I should also get the state of the robot
//        assertNotNull(response.get("state"));
//    }
//
//        @Test
//        void invalidLaunchShouldFail(){
//            // Given that I am connected to a running Robot Worlds server
//            assertTrue(serverClient.isConnected());
//
//            // When I send a invalid launch request with the command "luanch" instead of "launch"
//            String request = "{" +
//                    "\"robot\": \"HAL\"," +
//                    "\"command\": \"luanch\"," +
//                    "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
//                    "}";
//            JsonNode response = serverClient.sendRequest(request);
//
//            // Then I should get an error response
//            assertNotNull(response.get("result"));
//            assertEquals("ERROR", response.get("result").asText());
//
//            // And the message "Unsupported command"
//            assertNotNull(response.get("data"));
//            assertNotNull(response.get("data").get("message"));
//            assertTrue(response.get("data").get("message").asText().contains("Unsupported command"));
//        }
//
//}
