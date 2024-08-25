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
import za.wethinkcode.RobotWorlds.client.Client;
import za.wethinkcode.RobotWorlds.domain.Controller;
import za.wethinkcode.RobotWorlds.domain.world.Obstacles;
import za.wethinkcode.RobotWorlds.server.ActivateServer;
import za.wethinkcode.RobotWorlds.server.SimpleServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class Apitest {
    private static Api api;

    @BeforeAll
    public static void setUp() {

        api = new Api();
        ((Logger) LoggerFactory.getLogger("org.eclipse.jetty")).setLevel(Level.WARN);
        ((Logger) LoggerFactory.getLogger("io.javalin")).setLevel(Level.WARN);
        api.start(4000);

    }

    @AfterAll
    public static void tearDown() {
        api.stop();


    }

    @Test
    public void testRestoreWorld() {
//        System.out.println("-----------------1");

        HttpResponse<String> response = Unirest.get("http://localhost:4000/world/nathi")
                .asString();
//        System.out.println("status "+response.getStatus());


        assertEquals(200, response.getStatus());


        String responseBody = response.getBody();
//        System.out.println("Restore World Response: " + responseBody);
        assertTrue(!responseBody.isEmpty());

    }
    @Test
    public void testWorldNotInDatabase() {


//        System.out.println("-----------------2");

        HttpResponse<String> response = Unirest.get("http://localhost:4000/world/NotInDatabase")
                .asString();

        assertEquals(200, response.getStatus());
        Obstacles obstacles = new Obstacles();

        ArrayList restoredObstacles1 = obstacles.getObstacles();
        restoredObstacles1.clear();
        ;
//        System.out.println(restoredObstacles1);

        assertTrue(restoredObstacles1.isEmpty());
        Obstacles.generateObstacles();

        assertFalse(restoredObstacles1.isEmpty());
//        System.out.println(restoredObstacles1);
        restoredObstacles1.clear();

    }

    @Test
    public void testCurrentWorld() throws IOException {
//        System.out.println("-----------------3");
        HttpResponse<String> response = Unirest.get("http://localhost:4000/world")
                .asString();

        Obstacles.generateObstacles();


        assertEquals(200, response.getStatus());
        ArrayList restoredObstacles = Obstacles.getObstacles();

        assertFalse(restoredObstacles.isEmpty());
//        System.out.println(restoredObstacles);

    }



    @Test
    public void testLaunchRobot() throws IOException {
//        System.out.println("-----------------4");
        ServerSocket serverSocket = new ServerSocket(SimpleServer.PORT);
        ArrayList<Socket> listRobotsSockets = new ArrayList<>();
        new Thread(() -> {
            Obstacles.generateObstacles();
            while (!serverSocket.isClosed()) {

                try {
                    Socket socket = serverSocket.accept();
                    listRobotsSockets.add(socket);
                    Runnable r = new SimpleServer(socket);
                    Thread task = new Thread(r);
                    task.start();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

        HttpResponse<String> response = Unirest.post("http://localhost:4000/hal/launch/tank")
                .asString();
//        System.out.println(response.getStatus());

        assertEquals(200, response.getStatus());
//        serverSocket.close();


    }
}


