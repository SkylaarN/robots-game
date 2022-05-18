package za.wethinkcode.RobotWorlds.configuration;

import za.wethinkcode.RobotWorlds.Position;

public class Configuration {

    private final Position worldSize = new Position(-350, 350);

    public Position getWorldSize() {

        return worldSize;
    }
}