package za.wethinkcode.Tests.AcceptanceTest;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.fasterxml.jackson.databind.JsonNode;

class LookCommandTests {


    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServer() {
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer() {
        serverClient.disconnect();
    }

    @Test
    void worldIsEmpty() {
        // Given I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // And the robot "HAL" is launched at position (0, 0)
        LaunchRobot();

        // When I send a valid "look" command
        String lookRequest = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"look\"" +
                "}";
        JsonNode lookResponse = serverClient.sendRequest(lookRequest);

        // Then the response should be "OK"
        assertEquals("OK", lookResponse.get("result").asText());

        // And there should be no obstacles
        JsonNode obstacles = lookResponse.get("data").get("obstacles");
        assertTrue(obstacles == null || (obstacles.isArray() && obstacles.size() == 0));

        // And there should be no items
        JsonNode items = lookResponse.get("data").get("items");
        assertTrue(items == null || (items.isArray() && items.size() == 0));
    }

//    @Test
//    void robotDetectsObstacleAtDistanceOne() {
//        /**
//        - Given the world of size 2x2
//        - and the world has an obstacle at coordinate[0,1]
//        - and I have successfully launched 8 robots into the world
//        - when I ask the first robot to look
//        - then I should get a response back with
//        - one object being an OBSTACLE that is one step away
//        - and three objects should be ROBOTS that is one step away
//        **/
//
//        // Given I am connected to a running Robot Worlds server
//        assertTrue(serverClient.isConnected());
//
//        // Launch the robot at position (0, 0)
//        LaunchRobot();
//
//        // Send a valid "look" command
//        String lookRequest = "{" +
//                "  \"robot\": \"HAL\"," +
//                "  \"command\": \"look\"" +
//                "}";
//        JsonNode lookResponse = serverClient.sendRequest(lookRequest);
//
//        // Print the response for debugging
//        System.out.println(lookResponse.toString());
//
//        // Check response is "OK"
//        assertEquals("OK", lookResponse.get("result").asText());
//
//        // Validate obstacle detection
//        JsonNode data = lookResponse.get("data");
//        assertNotNull(data, "Data is missing in the response");
//
//        JsonNode objects = data.get("objects");
//        assertNotNull(objects, "Objects found");
//        assertTrue(objects.isArray(), "Objects data is an array");
//
//        boolean obstacleDetected = false;
//        for (JsonNode object : objects) {
//            String type = object.get("type").asText();
//            int distance = object.get("distance").asInt();
//            if ("OBSTACLE".equals(type) && distance == 1) {
//                obstacleDetected = true;
//                break;
//            }
//        }
//        assertTrue(obstacleDetected, "Obstacle at distance 1 is detected.");
//    }


    @Test
    void invalidLookCommand() {
        // Given I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // And the robot "HAL" is launched at position (0, 0)
        LaunchRobot();

        // When I send an invalid "looook" command
        String invalidLookRequest = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"looook\"" +
                "}";
        JsonNode invalidLookResponse = serverClient.sendRequest(invalidLookRequest);



        // Then the response should be "ERROR"
        assertEquals("ERROR", invalidLookResponse.get("result").asText());

        // And the message should be "Unsupported command"
        assertTrue(invalidLookResponse.get("data").get("message").asText().contains("Unsupported command"));
    }


    void LaunchRobot(){
        assertTrue(serverClient.isConnected());
        String launchRequest = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode launchResponse = serverClient.sendRequest(launchRequest);
        assertEquals("OK", launchResponse.get("result").asText());
    }


}