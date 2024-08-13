package za.wethinkcode.RobotWorlds.configuration;

import za.wethinkcode.RobotWorlds.worldLogic.Position;
import java.util.ArrayList;
import java.util.List;

public class Configuration {
    private Position worldSize = new Position(200, 200);  // Default world size (200x200)
    private int health = 5;
    private List<Position> obstacles = new ArrayList<>();  // List to store obstacle positions

    public Position getWorldSize() {
        return worldSize;
    }

    public void setWorldSize(int x, int y) {
        worldSize = new Position(x, y);
    }

    public void addObstacle(Position position) {
        obstacles.add(position);
    }

    public List<Position> getObstacles() {
        return obstacles;
    }

    public void reduceHealth() {
        this.health -= 1;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
