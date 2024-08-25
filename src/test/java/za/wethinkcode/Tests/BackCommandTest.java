package za.wethinkcode.Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.wethinkcode.RobotWorlds.domain.ClientCommands.BackCommand;
import za.wethinkcode.RobotWorlds.domain.world.Robot;

import static org.junit.jupiter.api.Assertions.*;

class BackCommandTest {
    private Robot robot;
    private BackCommand backCommand;

    @BeforeEach
    void setUp() {
        robot = new Robot("TestRobot");
        backCommand = new BackCommand("10");
    }

    @Test
    void testExecute() {
        int initialPosition = robot.getPosition().getY();
        backCommand.execute(robot);
//        assertEquals(initialPosition - 10, robot.getPosition().getY());
    }
}
