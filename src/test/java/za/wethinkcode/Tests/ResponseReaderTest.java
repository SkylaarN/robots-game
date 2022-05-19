package za.wethinkcode.Tests;

import static org.junit.jupiter.api.Assertions.*;

import za.wethinkcode.RobotWorlds.ResponseReader;
import za.wethinkcode.RobotWorlds.Robot;
import org.junit.jupiter.api.Test;

class ResponseReaderTest {
    @Test
    void testGetDirection(){
        ResponseReader resp = new ResponseReader();
        assertEquals(Robot.Direction.UP ,resp.getDirection("UP"));
        assertEquals(Robot.Direction.DOWN ,resp.getDirection("DOWN"));
        assertEquals(Robot.Direction.LEFT ,resp.getDirection("LEFT"));
        assertEquals(Robot.Direction.RIGHT ,resp.getDirection("RIGHT"));

    }

}
