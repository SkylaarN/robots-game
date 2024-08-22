package za.wethinkcode.Tests;

import za.wethinkcode.RobotWorlds.domain.world.Robot;
import za.wethinkcode.RobotWorlds.commands.*;
import org.junit.jupiter.api.Test;
import za.wethinkcode.RobotWorlds.domain.ClientCommands.*;

import static org.junit.jupiter.api.Assertions.*;


class CommandTest{

    @Test
    void testForward() {
        Robot robot = new Robot("Madara");
        String exp = "{\"result\":\"OK\",\"data\":{\"message\":\"Done\"}}";

        assertTrue(new LaunchCommand("5").execute(robot));
       // assertEquals(robot.getStatus(), exp);
    }

    @Test
    void testBackward() {
        Robot robot = new Robot("Madara");
        String exp = "{\"result\":\"OK\",\"data\":{\"message\":\"Done\"}}";

        assertTrue(new BackCommand("5").execute(robot));
        //assertEquals(robot.getStatus(), exp);
    }

    @Test
    void testRight() {
        Robot robot = new Robot("Rambo");
        String exp = "{\"result\":\"OK\",\"data\":{\"message\":\"Done\"}}";

        assertTrue(new RightCommand().execute(robot));
        //assertEquals(robot.getStatus(), exp);
    }

    @Test
    void TestLeft() {
        Robot robot = new Robot("Rambo");
        String exp = "{\"result\":\"OK\",\"data\":{\"message\":\"Done\"}}";

        assertTrue(new LeftCommand().execute(robot));
        //assertEquals(robot.getStatus(), exp);
    }

    @Test
    void TestLookGetAbsolutePosition() {
        Robot robot = new Robot("Killua");
        LookCommand look = new LookCommand();
//        assertEquals(look.getAbsolutePosition(robot, 0, 9), "NORTH");
//        assertEquals(look.getAbsolutePosition(robot, 0, -9), "SOUTH");
//        assertEquals(look.getAbsolutePosition(robot, 9, 0), "EAST");
//        assertEquals(look.getAbsolutePosition(robot, -9, 0), "WEST");

    }

    @Test
    void TestHelp() {
        Robot robot = new Robot("Killua");
        HelpCommand help = new HelpCommand();
        help.execute(robot);

        String exp = "{\"result\":\"OK\",\"data\":{\"message\":\"< Robot Commands >~OFF  - " +
                "Shut down robot~HELP - provide information about commands~FORWARD - " +
                "move forward by specified number of steps, e.g. 'forward 10'~" +
                "BACK - move backward by specific number of steps, e.g. 'back 10'~" +
                "RIGHT - turn right by 90 degrees~LEFT - turn left by 90 degrees~" +
                "ROBOTS - List all robots~SHOOT - Shoot robot~LOOK - See everything in its line of sight~\"}}";

        assertEquals(exp, robot.getStatus());
    }

    @Test
    void TestQuit() {
        Robot robot = new Robot("Killua");
        QuitCommand help = new QuitCommand();
        help.execute(robot);

        assertEquals("Shutting down...", robot.getStatus());
    }


}
