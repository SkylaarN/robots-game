package za.wethinkcode.RobotWorlds.commands;


import za.wethinkcode.RobotWorlds.worldLogic.Robot;

/**
 * The Make class represents the creation of a specific type of robot.
 * It assigns attributes such as shield, ammo, range, and state based on the robot type.
 */
public class Make {

    private final Robot player;
    private final String robotType;

    /**
     * Constructs a Make object with the specified robot type and player.
     *
     * @param make   The type of robot to create.
     * @param player The player for whom the robot is being created.
     */
    public Make(String make, Robot player) {
        this.robotType = make;
        this.player = player;
        launchRobot();
    }

    /**
     * Launches the creation process of the specified robot type.
     */
    public void launchRobot() {
        this.player.setStatusType("NORMAL");
        switch (this.robotType) {
            case "sniper":
                sniper();
                break;
//            case "operative":
//                operative();
//                break;
//            case "tank":
//                tank();
//                break;
//            case "defender":
//                defender();
//                break;
            default:
//                commando();
                break;
        }
    }

    /**
     * Creates a sniper robot.
     */
    public void sniper() {
        this.player.setHealth(1);
        this.player.setBullets(5);
//        this.player.setRange(5);
//        this.player.setRobotType("Sniper");

    }

    /**
     * Creates an assassin robot.
//     */
//    public void operative() {
//        this.player.setHealth(2);
//        this.player.setBullets(4);
//        this.player.setRange(4);
//        this.player.setRobotType("Operative");
//    }
//
//    /**
//     * Creates a Ranger robot.
//     */
//    public void commando() {
//        this.player.setHealth(3);
//        this.player.setBullets(3);
//        this.player.setRange(3);
//        this.player.setRobotType("Commando");
//    }
//
//    /**
//     * Creates a cowboy robot.
//     */
//    public void tank() {
//        this.player.setHealth(4);
//        this.player.setBullets(2);
//        this.player.setRange(2);
//        this.player.setRobotType("Tank");
//    }
//
//    /**
//     * Creates a priest robot.
//     */
//    public void defender() {
//        this.player.setHealth(5);
//        this.player.setBullets(1);
//        this.player.setRange(1);
//        this.player.setRobotType("Defender");
//    }
}
