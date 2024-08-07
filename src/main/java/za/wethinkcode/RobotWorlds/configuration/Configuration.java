package za.wethinkcode.RobotWorlds.configuration;

import za.wethinkcode.RobotWorlds.worldLogic.Position;

public class Configuration {

    private Position worldSize = new Position(-350, 350);
    private int health = 5;

    /**
     * get world size
     * @return world max and min coordinate
     */
    public Position getWorldSize() {

        return worldSize;
    }
    public void setWorldSize(int x, int y) {
        worldSize = new Position(x, y);
    }


    /**
     * reduce health
     */
    public void reduceHealth() {

        this.health -= 1;

        if (this.health < 0) {
            this.health = 0;
        }

    }

    /**
     * get robot health
     * @return health status
     */
    public int getHealth() {

        return this.health;
    }

    /**
     * set health
     * @param health status
     */
    public void setHealth(int health) {

        this.health = health;
    }
}