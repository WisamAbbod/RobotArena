package testjfx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Subclass of Robot with bump sensors.
 */
class RegularRobot extends Robot {

    public RegularRobot(double x, double y) {
        super(x, y);
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc); // Draw the base robot

        // Draws it as a light blue robot
        gc.setFill(Color.LIGHTBLUE);
        gc.fillOval(x - 20, y - 20, 41, 41);
    }

    /**
     * Detects collisions with obstacles and adjusts direction.
     *
     * @param obstacle is the obstacle to check against.
     */
    public void detectCollision(Obstacle obstacle) {
        double distance = Math.sqrt(Math.pow(x - obstacle.getX(), 2) + Math.pow(y - obstacle.getY(), 2));
        if (distance < 40) {
            direction = (direction + 180) % 360; // Reverse direction
        }
    }
}

