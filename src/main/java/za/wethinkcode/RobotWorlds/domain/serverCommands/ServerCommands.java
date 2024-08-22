package za.wethinkcode.RobotWorlds.domain.serverCommands;

import za.wethinkcode.RobotWorlds.domain.ClientCommands.RobotsCommand;
import za.wethinkcode.RobotWorlds.domain.world.Robot;
import za.wethinkcode.RobotWorlds.server.Players;
import za.wethinkcode.RobotWorlds.server.SimpleServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static za.wethinkcode.RobotWorlds.server.ActivateServer.listRobotsSockets;

public class ServerCommands {
    public ServerCommands(){}

    public void executeCommand(String command, ServerSocket serverSocket) throws IOException {
        Scanner scanner = new Scanner(System.in);
        switch (command.split(" ")[0].toLowerCase()){
            case "quit":
                for (Socket socket : listRobotsSockets){
                    socket.close();
                }
                serverSocket.close();
                break;
            case "save":
                System.out.print("Enter world name to save: ");
                String worldName = scanner.nextLine();

                // Assuming you have a default Robot to save the world data
                Robot robot = getRobotToSave();

                SaveCommand saveCommand = new SaveCommand(worldName);
                saveCommand.execute(robot);

                System.out.println(robot.getStatus());
                break;
            case "restore":
                System.out.print("Enter world name to restore: ");
                worldName = scanner.nextLine();

                robot = getRobotToRestore(" ");

                RestoreCommand restoreCommand = new RestoreCommand(worldName);
                restoreCommand.execute(robot);

                System.out.println(robot.getStatus());
                break;
            case "dump":
                robot = getRobotToSave();
                DumpCommand dumpCommand = new DumpCommand();
                dumpCommand.execute(robot);
                break;
            case "robots":
                robot = getRobotToSave();
                RobotsCommand robotsCommand = new RobotsCommand();
                robotsCommand.execute(robot);
                break;
            case "purge":
                if (command.split(" ").length == 2){
                    String purgedRobot = command.split(" ")[1];
                    System.out.println("purging" + " " + purgedRobot);
                    Robot vito = Players.getRobot(purgedRobot);
                    Players.getPlayers().remove(vito);
                    SimpleServer.listRobots.remove(purgedRobot);
                    Players.robotsSSSS.get(purgedRobot).close();

                }
                else {
                    System.out.println("Enter purge and the name of the robot you want to purge.\n eg.(purge John)");
                }
                break;
        }
    }

    private static Robot getRobotToSave() {
        // Retrieve or create the Robot whose world state you want to save
        return Players.getRobot("robot1");  // Replace with the actual robot name or retrieval logic
    }

    static Robot getRobotToRestore(String robotName) {
        return new Robot(robotName);
    }
}


