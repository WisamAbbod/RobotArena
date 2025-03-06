package testjfx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Subclass of Robot that goes through obstacles.
 */
class GhostRobot extends Robot {

    public GhostRobot(double x, double y) {
        super(x, y);
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc); // Draws the base robot

     // Makes it grey like ghost
        gc.setFill(Color.GREY);
        gc.fillOval(x - 20, y - 20, 41, 41);
    }

    /**
     * Slows down next to obstacle.
     *
     * @param obstacle The obstacle to check against.
     */
    public void detectProximity(Obstacle obstacle) {
        double distance = Math.sqrt(Math.pow(x - obstacle.getX(), 2) + Math.pow(y - obstacle.getY(), 2));
        if (distance < 100) {
            speed = 0.5; // Slow down
        } else {
            speed = 1; // Reset speed
        }
    }
}
