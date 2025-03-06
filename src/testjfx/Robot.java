package testjfx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents a generic robot in the arena.
 */
public class Robot extends ArenaItem {
    protected double speed; // Speed of the robot
    protected double direction; // Direction in degrees (0-360)

    /**
     * Constructs a generic robot with a position.
     *
     * @param x The x-coordinate of the robot.
     * @param y The y-coordinate of the robot.
     */
    public Robot(double x, double y) {
        super(x, y);
        this.speed = 1; // Default speed
        this.direction = Math.random() * 360; // Random initial direction
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save(); // Save the current state of the GraphicsContext

        // Translate to the robot's position and rotate
        gc.translate(x, y);
        gc.rotate(direction);

        // Draw the robot body (centred at 0,0 after translation)
        gc.setFill(Color.LIGHTGREEN);
        gc.fillOval(-20, -20, 40, 40);

        // Draw the wheels
        gc.setFill(Color.BLACK);
        gc.fillRect(-10, -25, 25, 10); // Left wheel
        gc.fillRect(-10, 15, 25, 10); // Right wheel

        // Optionally draw a marker to indicate the front
        gc.setFill(Color.RED);
        gc.fillOval(-5, -5, 10, 10);

        gc.restore(); // Restore the original state of the GraphicsContext
    }


    /**
     * Sets the speed of the robot.
     *
     * @param speed The new speed.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Moves the robot based on its speed and direction.
     */
    public void move() {
        x += speed * Math.cos(Math.toRadians(direction)); // Update x-coordinate
        y += speed * Math.sin(Math.toRadians(direction)); // Update y-coordinate

        // Bounce off walls
        if (x < 30 || x > 670) direction = 180 - direction; // Reverse horizontal direction
        if (y < 30 || y > 570) direction = -direction; // Reverse vertical direction
    }
}


