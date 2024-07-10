package za.wethinkcode.Tests.AcceptanceTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import za.wethinkcode.RobotWorlds.RobotWorldClient;
import za.wethinkcode.RobotWorlds.RobotWorldJsonClient;



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

}
