package za.wethinkcode.Tests;

import za.wethinkcode.RobotWorlds.Robot;
import za.wethinkcode.RobotWorlds.commands.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class CommandTest{

    @Test
    void testForward() {

        Robot robot = new Robot("Madara");
        new ForwardCommand("10").execute(robot);

        assertTrue(robot.getStatus().contains("Moved forward by 10 steps."));
    }

    @Test
    void testBackward() {

        Robot robot = new Robot("Terminator");
        new BackCommand("5").execute(robot);

        assertTrue(robot.getStatus().contains("Moved back by 5 steps."));
    }

    @Test
    void testRight() {

        Robot robot = new Robot("Rambo");
        new RightCommand().execute(robot);

        assertTrue(robot.getStatus().contains("Turned right"));
    }

    @Test
    void TestLeft() {

        Robot robot = new Robot("Madara");
        new LeftCommand().execute(robot);

        assertTrue(robot.getStatus().contains("Turned left"));
    }

    @Test
    void TestLookGetAbsolutePosition() {
        Robot robot = new Robot("Killua");
        LookCommand look = new LookCommand();
        assertEquals(look.getAbsolutePosition(robot, 0, 9), "NORTH");
        assertEquals(look.getAbsolutePosition(robot, 0, -9), "SOUTH");
        assertEquals(look.getAbsolutePosition(robot, 9, 0), "EAST");
        assertEquals(look.getAbsolutePosition(robot, -9, 0), "WEST");

    }


}
