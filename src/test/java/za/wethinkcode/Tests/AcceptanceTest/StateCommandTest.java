//package za.wethinkcode.Tests.AcceptanceTest;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class StateCommandTest {
//
//
//    private final static int DEFAULT_PORT = 6000;
//    private final static String DEFAULT_IP = "localhost";
//    private final RobotWorldClient serverClient = new RobotWorldJsonClient();
//
//    @BeforeEach
//    void connectToServer(){
//        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
//    }
//
//    @AfterEach
//    void disconnectFromServer(){
//        serverClient.disconnect();
//    }
//
//    @Test
//    void validStateShouldSucceed() {
//        Launch();
//        String request = "{" +
//                "  \"robot\": \"HAL\"," +
//                "  \"command\": \"state\"," +
//                "  \"arguments\": []" +
//                "}";
//        JsonNode response = serverClient.sendRequest(request);
//
//        // Then I should get a valid response from the server
//        assertNotNull(response.get("result"));
//        assertEquals("OK", response.get("result").asText());
//        assertEquals("NORTH", response.get("state").get("direction").asText());
//    }
//
//    void Launch() {
//        assertTrue(serverClient.isConnected());
//        String request = "{" +
//                "  \"robot\": \"HAL\"," +
//                "  \"command\": \"launch\"," +
//                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
//                "}";
//        serverClient.sendRequest(request);
//    }
//}
//
