package za.wethinkcode.Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.wethinkcode.RobotWorlds.domain.ClientCommands.RightCommand;
import za.wethinkcode.RobotWorlds.domain.world.Robot;

import static org.junit.jupiter.api.Assertions.*;

class RightCommandTest {
    private Robot robot;
    private RightCommand rightCommand;

    @BeforeEach
    void setUp() {
        robot = new Robot("TestRobot");
        rightCommand = new RightCommand();
    }

    @Test
    void testExecute() {
        int initialPosition = robot.getPosition().getY();
        rightCommand.execute(robot);
        assertEquals(initialPosition , robot.getPosition().getY());
    }
}
