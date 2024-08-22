package za.wethinkcode.Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.wethinkcode.RobotWorlds.domain.ClientCommands.LookCommand;
import za.wethinkcode.RobotWorlds.domain.world.Robot;

import static org.junit.jupiter.api.Assertions.*;

class LookCommandTest {
    private Robot robot;
    private LookCommand lookCommand;

    @BeforeEach
    void setUp() {
        robot = new Robot("TestRobot");
        lookCommand = new LookCommand();
    }

    @Test
    void testExecute() {
        Boolean surroundings = lookCommand.execute(robot);
        assertNotNull(surroundings);
    }
}
