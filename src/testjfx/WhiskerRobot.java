package testjfx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Subclass of Robot with whisker sensors.
 */
class WhiskerRobot extends Robot {

    public WhiskerRobot(double x, double y) {
        super(x, y);
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc); // Draws the base robot

        // Giving whisker angle of 30 degrees
        double leftAngle = Math.toRadians(direction - 30);  // Left whisker
        double rightAngle = Math.toRadians(direction + 30); // Right whisker

        // Giving Whisker Size
        double leftWhiskerX = x + 75 * Math.cos(leftAngle); // whisker length of 75
        double leftWhiskerY = y + 75 * Math.sin(leftAngle);
        double rightWhiskerX = x + 75 * Math.cos(rightAngle);
        double rightWhiskerY = y + 75 * Math.sin(rightAngle);

        // Draws whiskers
        gc.setStroke(Color.LIGHTGREEN);
        gc.setLineWidth(3);  //whisk width of 3
        gc.strokeLine(x, y, leftWhiskerX, leftWhiskerY); // Left whisker
        gc.strokeLine(x, y, rightWhiskerX, rightWhiskerY); // Right whisker

        // Detecting borders for whiskers
        detectBorderCollision(leftWhiskerX, leftWhiskerY);
        detectBorderCollision(rightWhiskerX, rightWhiskerY);
    }

    /**
     * changes direction when whiskers hit border
     *
     * @param whiskerX The x-coordinate of whisker tip.
     * @param whiskerY The y-coordinate of whisker tip.
     */
    private void detectBorderCollision(double whiskerX, double whiskerY) {
        // making the arena size 700x600 
        double arenaWidth = 700;
        double arenaHeight = 600;

        if (whiskerX < 0 || whiskerX > arenaWidth || whiskerY < 0 || whiskerY > arenaHeight) {
            // If the whisker goes out of bounds, turn the robot
            direction = (direction + 45) % 360; // Change direction by 45 degrees
        }
    }

    /**
     * Adjusts direction when whiskers touch an obstacle.
     *
     * @param obstacle The obstacle to check against.
     */
    public void detectCollision(Obstacle obstacle) {
        double distance = Math.sqrt(Math.pow(x - obstacle.getX(), 2) + Math.pow(y - obstacle.getY(), 2));
        if (distance < 75) {
            direction = (direction + 45) % 360; // Change direction
        }
    }
}
