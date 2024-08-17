package za.wethinkcode.RobotWorlds.commands;


import za.wethinkcode.RobotWorlds.worldLogic.Robot;

/**
 * The Make class represents the creation of a specific type of robot.
 * It assigns attributes such as shield, ammo, range, and state based on the robot type.
 */
public class Make {

    private final Robot player;
    private final String robotMake;

    /**
     * Constructs a Make object with the specified robot type and player.
     *
     * @param make   The type of robot to create.
     * @param player The player for whom the robot is being created.
     */
    public Make(String make, Robot player) {
        this.robotMake = make;
        this.player = player;
        launchRobot();
    }

    /**
     * Launches the creation process of the specified robot type.
     */
    public void launchRobot() {
        this.player.setStatusType("NORMAL");
        switch (this.robotMake) {
            case "sniper":
                sniper();
                break;
            case "operative":
                operative();
                break;
            case "tank":
                tank();
                break;
            case "shooter":
                defender();
                break;
            default:
                commando();
                break;
        }
    }

    /**
     * Creates a sniper robot.
     */
    public void sniper() {
        this.player.setHealth(1);
        this.player.setBullets(5);
        this.player.setVisibility(5);

    }

    /**
     * Creates an operative robot.
     //     */
    public void operative() {
        this.player.setHealth(2);
        this.player.setBullets(4);
        this.player.setVisibility(4);
    }

    /**
     * Creates a commando robot.
     */
    public void commando() {
        this.player.setHealth(3);
        this.player.setBullets(3);
        this.player.setVisibility(3);
    }

    /**
     * Creates a tank robot.
     */
    public void tank() {
        this.player.setHealth(4);
        this.player.setBullets(2);
        this.player.setVisibility(2);
    }

    /**
     * Creates a defender robot.
     */
    public void defender() {
        this.player.setHealth(5);
        this.player.setBullets(1);
        this.player.setVisibility(1);
    }
}
