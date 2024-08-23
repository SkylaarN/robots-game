package za.wethinkcode.RobotWorlds.api;

import io.javalin.Javalin;

public interface ApiHandler {

    void restore_world();

    void Launch_robot();
    public Javalin start(int port);
    public Javalin stop();

}
