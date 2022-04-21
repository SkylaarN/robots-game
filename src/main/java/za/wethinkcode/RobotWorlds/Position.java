package za.wethinkcode.RobotWorlds;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        /**Function checks to see if 2 position objects hve the same x and y position
         *
         * @param o the Object the position is being compared to
         * @return boolean If they are equal
         */
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }


    public boolean isIn(Position topLeft, Position bottomRight) {
        /**Function checks to see if the Position is withing the border of the maze
         *
         * @param topLeft the Position of the top left point of the maze
         * @param bottomRight the Position of the bottom right point of the maze
         * @return boolean If the Position is within the area between the 2 points
         */

        boolean withinTop = this.y <= topLeft.getY();
        boolean withinBottom = this.y >= bottomRight.getY();
        boolean withinLeft = this.x >= topLeft.getX();
        boolean withinRight = this.x <= bottomRight.getX();
        return withinTop && withinBottom && withinLeft && withinRight;
    }
}
