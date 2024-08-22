package za.wethinkcode.Tests;

import org.junit.jupiter.api.Test;
import za.wethinkcode.RobotWorlds.domain.world.Position;
import za.wethinkcode.RobotWorlds.domain.world.Robot;
import za.wethinkcode.RobotWorlds.domain.ClientCommands.LaunchCommand;
import za.wethinkcode.RobotWorlds.domain.ClientCommands.LeftCommand;
import za.wethinkcode.RobotWorlds.domain.ClientCommands.RightCommand;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    @Test
    public void shouldKnowXandY() {
        Position p = new Position(10, 20);
        assertEquals(10, p.getX());
        assertEquals(20, p.getY());

    }
    @Test
    public void equality() {
        //assertEquals(new Position(-44, 63), new Position(-44, 63));
        assertNotEquals(new Position(-44, 63), new Position(0, 63));
        assertNotEquals(new Position(-44, 63), new Position(-44, 0));
        assertNotEquals(new Position(-44, 63), new Position(0, 0));
    }
    @Test
    public void insideRectangularRegion() {
        Position topLeft = new Position(-20, 20);
        Position bottomRight = new Position(20,-20);
        assertTrue((new Position(10,10)).isIn(topLeft, bottomRight), "should be inside");
        assertFalse((new Position(10,30)).isIn(topLeft, bottomRight), "should be beyond top boundary");
        assertFalse((new Position(10,-30)).isIn(topLeft, bottomRight), "should be beyond bottom boundary");
        assertFalse((new Position(30,10)).isIn(topLeft, bottomRight), "should be beyond right boundary");
        assertFalse((new Position(-30,10)).isIn(topLeft, bottomRight), "should be beyond left boundary");
    }

    @Test
    void testNorthDirection() {

        Robot robot = new Robot("North");

        new LaunchCommand("10").execute(robot);

//        assertEquals(0, robot.getPosition().getX());
//        assertEquals(0, robot.getPosition().getY());
    }

    @Test
    void testEastDirection() {

        Robot robot = new Robot("East");

        new RightCommand().execute(robot);
        new LaunchCommand("3").execute(robot);

       // assertEquals(3, robot.getPosition().getX());
//        assertEquals(0, robot.getPosition().getY());

    }

    @Test
    void testSouthDirection() {

        Robot robot = new Robot("South");

        new RightCommand().execute(robot);
        new RightCommand().execute(robot);
        new LaunchCommand("5").execute(robot);

//        assertEquals(0, robot.getPosition().getX());
//        assertEquals(0, robot.getPosition().getY());
    }

    @Test
    void testWestDirection() {

        Robot robot = new Robot("West");

        new LeftCommand().execute(robot);
        new LaunchCommand("10").execute(robot);

//        assertEquals(0, robot.getPosition().getX());
//        assertEquals(0, robot.getPosition().getY());
    }
}
