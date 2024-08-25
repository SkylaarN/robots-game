package za.wethinkcode.Tests.AcceptanceTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;

class LookCommandTests {
    /**
     * As a player
     * I want to launch my robot in the online robot world
     * So that I can break the record for the most robot kills
     * <p>
     * Scenario: See an obstacle
     * Scenario: See robots and obstacles
     */


    private final static int DEFAULT_PORT = 8000;
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

//    @Test
//    void worldIsEmpty() {
//        // Given I am connected to a running Robot Worlds server
//        assertTrue(serverClient.isConnected());
//
//        // And the robot "HAL" is launched at position (0, 0)
//        LaunchRobot();
//
//        // When I send a valid "look" command
//        String lookRequest = "{" +
//                "  \"robot\": \"HAL\"," +
//                "  \"command\": \"look\"" +
//                "  \"arguments\": ["+"]" +
//                "}";
//        JsonNode lookResponse = serverClient.sendRequest(lookRequest);
//
//        // Then the response should be "OK"
//        assertEquals("OK", lookResponse.get("result").asText());
//
//        // And there should be no obstacles
//        JsonNode obstacles = lookResponse.get("data").get("obstacles");
//        assertTrue(obstacles == null || (obstacles.isArray() && obstacles.isEmpty()));
//
//        // And there should be no items
//        JsonNode items = lookResponse.get("data").get("items");
//        assertTrue(items == null || (items.isArray() && items.isEmpty()));
//    }

//    @Test
//    void LookObstacle() throws JsonProcessingException {
//        // Given I am connected to a running Robot Worlds server
//        assertTrue(serverClient.isConnected());
//        // And the robot "HAL" is launched at position (0, 0)
//        LaunchRobot();
//        // When I send a valid "look" command
//        String lookRequest = "{" +
//                "  \"robot\": \"HAL\"," +
//                "  \"command\": \"look\"" +
//                "}";
//        JsonNode lookResponse = serverClient.sendRequest(lookRequest);
//        // Then the response should be "OK"
//        assertEquals("OK", lookResponse.get("result").asText());
//        // And there should be no obstacles
//        JsonNode obstacles = lookResponse.get("data").get("objects");
//        System.out.println(obstacles);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonString = objectMapper.writeValueAsString(obstacles);
//
//        try {
//            // Parse the JSON array
//            JsonNode arrayNode = objectMapper.readTree(jsonString);
//            // Check that the array has at least one element
//            if (arrayNode.isArray() && arrayNode.size() > 0) {
//                // Get the first element of the array
//                JsonNode firstElement = arrayNode.get(0);
//
//                // Extract the value of the "type" field
//                String type = firstElement.path("type").asText();
//
//                // Print the value
//                System.out.println(type);
//
//                assertEquals("OBSTACLE",type);
//            } else {
//                System.out.println("The JSON array is empty or not an array.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    @Test
    void LookObstacleBot() throws IOException {
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
        JsonNode obstacles = lookResponse.get("data").get("objects");
        System.out.println(obstacles.getClass().getName());
    }


        void LaunchRobot () {
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
