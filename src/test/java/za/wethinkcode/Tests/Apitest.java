package za.wethinkcode.Tests;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import za.wethinkcode.RobotWorlds.api.Api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Apitest {
    private static Api api;

    @BeforeAll
    public static void setUp() {
        // Initialize and start the API server
        api = new Api();
        api.start(7000); // Start the server on port 7000
    }

    @AfterAll
    public static void tearDown() {
        // Stop the API server after tests
        api.stop();
    }

    @Test
    public void testRestoreWorld() {
        // Simulate a GET request to the restore world endpoint
        HttpResponse<String> response = Unirest.get("http://localhost:7000/worlds/nathi")
                .asString();


        assertEquals(200, response.getStatus());


        String responseBody = response.getBody();
        System.out.println("Restore World Response: " + responseBody);
        assertTrue(!responseBody.isEmpty());
    }

//    @Test
//    public void testLaunchRobot() {
//
//        HttpResponse<String> response = Unirest.post("http://localhost:7000/HAL/launch/tank")
//                .asString();
//
//
//        assertEquals(200, response.getStatus());
//
//
//        String responseBody = response.getBody();
//        System.out.println("Launch Robot Response: " + responseBody);
//
//    }
}


