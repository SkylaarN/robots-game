package za.wethinkcode.Tests;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import kong.unirest.GenericType;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.junit.jupiter.api.*;
import org.slf4j.LoggerFactory;
import za.wethinkcode.RobotWorlds.api.Api;
import za.wethinkcode.RobotWorlds.api.ApiHandler;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Apitest {
    private static Api api;

    @BeforeEach
    public void setUp() {

        api = new Api();
        ((Logger) LoggerFactory.getLogger("org.eclipse.jetty")).setLevel(Level.WARN);
        ((Logger) LoggerFactory.getLogger("io.javalin")).setLevel(Level.WARN);
        api.start(3000);

    }

    @AfterEach
    public void tearDown() {

        api.stop();
    }

    @Test
    public void testRestoreWorld() {

        HttpResponse<String> response = Unirest.get("http://localhost:3000/worlds/nathi")
                .asString();


        assertEquals(200, response.getStatus());


        String responseBody = response.getBody();
        System.out.println("Restore World Response: " + responseBody);
        assertTrue(!responseBody.isEmpty());
    }

//    @Test
//    public void testLaunchRobot() {
//
//        try {
//            HttpResponse<String> response = Unirest.post("http://localhost:3000/hal/launch/tank")
//                    .asString();
//
//            System.out.println("Status: " + response.getStatus());
//            System.out.println("Body: " + response.getBody());
//            assertEquals(200, response.getStatus());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//
//
//
////        String responseBody = response.getBody();
////        System.out.println("Launch Robot Response: " + responseBody);
//
//    }
}


