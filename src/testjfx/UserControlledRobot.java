package testjfx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import java.util.List;

class UserControlledRobot extends Robot {

    private static final double DETECTION_CONE_ANGLE = 60; // Cone angle in degrees
    private static final double DETECTION_CONE_RANGE = 120; // Detection range

    // Constructor that sets the initial position and direction
    public UserControlledRobot(double x, double y) {
        super(x, y);
    }

    @Override
    public void draw(GraphicsContext graphics) {
        super.draw(graphics); // Draw the base robot

        // Draw the robot as a simple circle for visualisation
        graphics.setFill(Color.RED);
        graphics.fillOval(x - 20, y - 20, 40, 40);  // Draw robot at the position (x, y)

        // Draw detection cone for visualisation
        drawDetectionCone(graphics);
    }

    /**
     * Draws the detection cone in front of the robot.
     *
     * @param graphics The graphics context to draw on.
     */
    private void drawDetectionCone(GraphicsContext graphics) {
        graphics.setFill(Color.rgb(255, 0, 0, 0.3)); // Semi-transparent red
        graphics.beginPath();
        graphics.moveTo(x, y);

        // Left edge of the cone
        double leftAngle = Math.toRadians(direction - DETECTION_CONE_ANGLE / 2);
        double leftX = x + DETECTION_CONE_RANGE * Math.cos(leftAngle);
        double leftY = y + DETECTION_CONE_RANGE * Math.sin(leftAngle);

        // Right edge of the cone
        double rightAngle = Math.toRadians(direction + DETECTION_CONE_ANGLE / 2);
        double rightX = x + DETECTION_CONE_RANGE * Math.cos(rightAngle);
        double rightY = y + DETECTION_CONE_RANGE * Math.sin(rightAngle);

        // Draw the cone
        graphics.lineTo(leftX, leftY);
        graphics.lineTo(rightX, rightY);
        graphics.closePath();
        graphics.fill();
    }

    // Handle user input to move the robot
    public void handleKeyInput(KeyEvent event) {
        if (event.getCode() == KeyCode.W) {
            moveForward();  // Move the robot forward
        } else if (event.getCode() == KeyCode.A) {
            turnLeft();  // Turn the robot to the left
        } else if (event.getCode() == KeyCode.S) {
            moveBackward();  // Move the robot backward
        } else if (event.getCode() == KeyCode.D) {
            turnRight();  // Turn the robot to the right
        }
    }

    /**
     * Checks if other robots are within the detection cone and removes them.
     *
     * @param robots The list of robots to check.
     */
    public void detectAndRemoveRobots(List<ArenaItem> robots) {
        robots.removeIf(this::isInDetectionCone);
    }

    /**
     * Determines if a robot is within the detection cone.
     *
     * @param robot The robot to check.
     * @return True if the robot is within the detection cone, false otherwise.
     */
    private boolean isInDetectionCone(ArenaItem robot) {
        // Calculate the distance to the robot
        double dx = robot.getX() - x;
        double dy = robot.getY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Check if within range
        if (distance > DETECTION_CONE_RANGE) {
            return false;
        }

        // Calculate the angle to the robot
        double angleToRobot = Math.toDegrees(Math.atan2(dy, dx));
        double relativeAngle = (angleToRobot - direction + 360) % 360;

        // Check if within cone angle
        return relativeAngle >= 360 - DETECTION_CONE_ANGLE / 2 || relativeAngle <= DETECTION_CONE_ANGLE / 2;
    }

    // Move the robot forward
    private void moveForward() {
        x += 10 * Math.cos(Math.toRadians(direction));  // Move in the direction the robot is facing
        y += 10 * Math.sin(Math.toRadians(direction));
    }

    // Move the robot backward
    private void moveBackward() {
        x -= 10 * Math.cos(Math.toRadians(direction));  // Move in the opposite direction
        y -= 10 * Math.sin(Math.toRadians(direction));
    }

    // Turn the robot left (counter-clockwise)
    private void turnLeft() {
        direction -= 5;  // Turn by 5 degrees
        if (direction < 0) {
            direction += 360;  // Wrap around if necessary
        }
    }

    // Turn the robot right (clockwise)
    private void turnRight() {
        direction += 5;  // Turn by 5 degrees
        if (direction >= 360) {
            direction -= 360;  // Wrap around if necessary
        }
    }
    
}
