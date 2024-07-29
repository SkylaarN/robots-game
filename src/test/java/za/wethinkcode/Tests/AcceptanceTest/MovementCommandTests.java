package za.wethinkcode.Tests.AcceptanceTest;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import za.wethinkcode.RobotWorlds.RobotWorldClient;
import za.wethinkcode.RobotWorlds.RobotWorldJsonClient;

import static org.junit.jupiter.api.Assertions.*;


public class MovementCommandTests {
    /**
     * I want to launch my robot in the online robot world
     * Then move my robot
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
    void ValidForwardCommand() {
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());
        //System.out.println(serverClient.isConnected());

        // When I send a valid launch request to the server
        String requestLaunch = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode responseLaunch = serverClient.sendRequest(requestLaunch);

        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"forward\"," +
                "  \"arguments\": [\"20\"]" +
                "}";
        JsonNode response_forward = serverClient.sendRequest(request);
        //System.out.println(response_forward);

        // response from the server for vaild commmand
        assertNotNull(response_forward.get("result"));
        assertEquals("OK", response_forward.get("result").asText());
        //assertEquals("OK", response_forward.get("At the NORTH edge").asText());

        assertNotNull(response_forward.get("data"));
        //assertNotNull(response_forward.get("data").get("position"));
        assertEquals(0, response_forward.get("data").get("position").get(0).asInt());
        assertEquals(20, response_forward.get("data").get("position").get(1).asInt());

    }

    @Test
    void InvaildForwardCommand() {
//            checks if connection is made
        assertTrue(serverClient.isConnected());
//            launching robot
        String requestLaunch = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
//        get response from server after launching
        JsonNode response = serverClient.sendRequest(requestLaunch);

//        place invalid forward command
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"forward 10\"," +
                "  \"arguments\": [0]" +
                "}";
        JsonNode response_forward = serverClient.sendRequest(request);

//        running error for a invaild forward command returns "ERROR"
        assertNotNull(response_forward.get("result").asText());
        assertEquals("ERROR", response_forward.get("result").asText());

    }
}
