package za.wethinkcode.RobotWorlds;

import edu.princeton.cs.introcs.StdDraw;

public class Robot {

    private Position position;

    public Robot(){
        this.position = new Position(0, 0);
        StdDraw.setPenRadius(0.001);
        StdDraw.setPenColor(StdDraw.MAGENTA);
        StdDraw.line(0.2, 0.2, 0.8, 0.2);
        StdDraw.line(0.2, 0.8, 0.8, 0.8);
        StdDraw.line(0.2, 0.2, 0.2, 0.8);
        StdDraw.line(0.8, 0.2, 0.8, 0.8);

    }

    void handleCommand(String com){
        StdDraw.setPenColor(StdDraw.BLUE);
        String[] args = com.toLowerCase().trim().split(" ");
        Position next;
        int num;
        switch(args[0]){

            case "forward":
                System.out.println("Robot moved forward");
                num = Integer.parseInt(args[1]);
                next = nextPosition(0, num);
                StdDraw.line(0.5 + this.position.getX()/512.0,
                        0.5 + this.position.getY()/512.0,
                        0.5 + next.getX()/512.0,
                        0.5 +next.getY()/512.0);
                this.position = next;
                break;

            case "back":
                System.out.println("Robot moved back");
                num = Integer.parseInt(args[1]);
                next = nextPosition(0, num * -1);
                StdDraw.line(0.5 + this.position.getX()/512.0,
                        0.5 + this.position.getY()/512.0,
                        0.5 + next.getX()/512.0,
                        0.5 +next.getY()/512.0);
                this.position = next;
                break;

            case "right":
                System.out.println("Robot turned right");
                num = Integer.parseInt(args[1]);
                next = nextPosition(num, 0);
                StdDraw.line(0.5 + this.position.getX()/512.0,
                        0.5 + this.position.getY()/512.0,
                        0.5 + next.getX()/512.0,
                        0.5 +next.getY()/512.0);
                this.position = next;
                break;

            case "left":
                System.out.println("Robot turned left");
                num = Integer.parseInt(args[1]);
                next = nextPosition(num * -1, 0);
                StdDraw.line(0.5 + this.position.getX()/512.0,
                        0.5 + this.position.getY()/512.0,
                        0.5 + next.getX()/512.0,
                        0.5 +next.getY()/512.0);
                this.position = next;
                break;

            default:
                System.out.println("Unsupported command: " + com);
                break;
        }
    }

    public Position nextPosition(int xChange, int yChange){
        return new Position(this.position.getX() + xChange, this.position.getY() + yChange);
    }
}
