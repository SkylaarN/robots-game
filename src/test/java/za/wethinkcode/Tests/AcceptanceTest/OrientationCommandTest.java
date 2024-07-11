package za.wethinkcode.Tests.AcceptanceTest;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.wethinkcode.RobotWorlds.RobotWorldClient;
import za.wethinkcode.RobotWorlds.RobotWorldJsonClient;

import static org.junit.jupiter.api.Assertions.*;

public class OrientationCommandTest {

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

    /**
     * Test to ensure that a valid orientation command should succeed.
     *
     * Scenario:
     * - Given that I am connected to a Robot Worlds server
     * - And another robot is inside the world
     * - When I send an orientation request
     * - Then I should get a valid response from the server
     * - And the message "north/south/east/west."
     */
    @Test
    void validOrientationShouldSucceed() {
        Launch();
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"Orientation\"," +
                "  \"arguments\": []" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get a valid response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());
        assertEquals("NORTH", response.get("data").get("direction").asText());
    }

    /**
     * Helper method to launch the robot into the world before running the test.
     */
    void Launch() {
        assertTrue(serverClient.isConnected());
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient.sendRequest(request);
    }
}

