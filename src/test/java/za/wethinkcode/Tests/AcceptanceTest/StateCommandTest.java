package za.wethinkcode.Tests.AcceptanceTest;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.wethinkcode.RobotWorlds.Robot;
import za.wethinkcode.RobotWorlds.RobotWorldClient;
import za.wethinkcode.RobotWorlds.RobotWorldJsonClient;

import static org.junit.jupiter.api.Assertions.*;

public class StateCommandTest {
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
    void validStateShouldSucceed() {
        Launch();
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"state\"," +
                "  \"arguments\": []" +
                "}";
        JSONObject response = new JSONObject(serverClient.sendRequest(request));

        // Then I should get a valid response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.getString("result"));
        assertEquals("UP", response.getJSONObject("state").getString("direction"));
    }

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

