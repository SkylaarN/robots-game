package za.wethinkcode.Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.wethinkcode.RobotWorlds.commands.LeftCommand;
import za.wethinkcode.RobotWorlds.worldLogic.Robot;

import static org.junit.jupiter.api.Assertions.*;

class LeftCommandTest {
    private Robot robot;
    private LeftCommand leftCommand;

    @BeforeEach
    void setUp() {
        robot = new Robot("TestRobot");
        leftCommand = new LeftCommand();
    }

    @Test
    void testExecute() {
        int initialPosition = robot.getPosition().getX();
        leftCommand.execute(robot);
        assertEquals(initialPosition, robot.getPosition().getX());
    }
}
