package za.wethinkcode.Tests.AcceptanceTest;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.fasterxml.jackson.databind.JsonNode;
import za.wethinkcode.RobotWorlds.RobotWorldClient;
import za.wethinkcode.RobotWorlds.RobotWorldJsonClient;

class LookCommandTests {
    /**

     * I want to launch my robot in the online robot world
     * Then look around my world

     */

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
        String launchRequest = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode launchResponse = serverClient.sendRequest(launchRequest);
        assertEquals("OK", launchResponse.get("result").asText());

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

    @Test
    void invalidLookCommand() {
        // Given I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // And the robot "HAL" is launched at position (0, 0)
        String launchRequest = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode launchResponse = serverClient.sendRequest(launchRequest);
        assertEquals("OK", launchResponse.get("result").asText());

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

}