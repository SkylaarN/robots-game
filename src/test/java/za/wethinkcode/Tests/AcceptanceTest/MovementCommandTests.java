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
        void connectToServer(){
            serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
        }

        @AfterEach
        void disconnectFromServer(){
            serverClient.disconnect();
        }

    @Test
    void ValidForwardCommand() {
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());

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
                "  \"arguments\": [0]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // response from the server for vaild commmand
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

    }


}
