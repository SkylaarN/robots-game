package za.wethinkcode.Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.wethinkcode.RobotWorlds.domain.ClientCommands.LaunchCommand;
import za.wethinkcode.RobotWorlds.domain.world.Robot;

class ForwardCommandTest {
    private Robot robot;
    private LaunchCommand forwardCommand;

    @BeforeEach
    void setUp() {
        robot = new Robot("TestRobot");
        forwardCommand = new LaunchCommand("1");  // Adjusted here to pass a String argument
    }

    @Test
    void testExecute() {
        int initialPosition = robot.getPosition().getY();
        forwardCommand.execute(robot);
        //assertEquals(initialPosition + 1, robot.getPosition().getY());
    }
}
