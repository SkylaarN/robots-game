package za.wethinkcode.Tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SquareObstaclesTest {
    @Test
    void getBottomLeftXTest(){
        SquareObstacle obs = new SquareObstacle(5, 10);
        assertEquals(5, obs.getBottomLeftX());
    }

    @Test
    void getBottomLeftYTest(){
        SquareObstacle obs = new SquareObstacle(5, 10);
        assertEquals(10, obs.getBottomLeftY());
    }

    @Test
    void getSizeTest(){
        SquareObstacle obs = new SquareObstacle(7, 10);
        assertEquals(5, obs.getSize());
    }

    @Test
    void blocksPositionTest(){
        SquareObstacle obs = new SquareObstacle(5, 5);
        assertTrue(obs.blocksPosition(new Position(5, 5)));
        assertTrue(obs.blocksPosition(new Position(7, 7)));
        assertFalse(obs.blocksPosition(new Position(11, 11)));
    }

    @Test
    void blocksPathTest(){
        SquareObstacle obs = new SquareObstacle(5, 5);
        assertTrue(obs.blocksPath(new Position(0, 7), new Position(15, 7)));
        assertTrue(obs.blocksPath(new Position(7, 0), new Position(7, 15)));
        assertFalse(obs.blocksPath(new Position(0, 7), new Position(-15, 7)));
        assertFalse(obs.blocksPath(new Position(7, 0), new Position(7, -15)));
    }

    @Test
    void passObstacleTest(){
        SquareObstacle obs = new SquareObstacle(0, 0);
        assertTrue(obs.passObstacle(0, 15, 5, 9));
        assertFalse(obs.passObstacle(-15, 0, 5, 9));

    }
}
