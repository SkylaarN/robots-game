package za.wethinkcode.RobotWorlds.configuration;

import za.wethinkcode.RobotWorlds.Position;

public class Configuration {

    private final Position worldSize = new Position(-350, 350);
    private int health = 5;

    public Position getWorldSize() {

        return worldSize;
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