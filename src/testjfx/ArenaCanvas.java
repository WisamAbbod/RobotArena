package testjfx;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

/**
 * Manages the drawing and interactions within the robot arena.
 */
public class ArenaCanvas {
    private AnimationTimer time;   //attributes
    private final Canvas canvas;   
    private RobotArena arena;
    private final GraphicsContext graphics;
    private boolean isRunning = false;

    private final UserControlledRobot userRobot;  // Declare the user-controlled robot

    public ArenaCanvas(double width, double height) {
        this.canvas = new Canvas(width, height);
        this.graphics = canvas.getGraphicsContext2D();
        this.arena = new RobotArena();

        // Initialise the user-controlled robot at the centre of the arena
        userRobot = new UserControlledRobot(width / 2, height / 2);

        // Initialise with some default obstacles
        initialiseObstacles(5); // Example: 5 default obstacles

        // Set the canvas focusable to receive key events
        canvas.setFocusTraversable(true);

        // Handle key events for user control
        canvas.setOnKeyPressed(this::handleKeyPress);
    }

    /**
     * Initialises the arena with random obstacles.
     *
     * @param count The number of obstacles to add.
     */
    private void initialiseObstacles(int count) {
        for (int i = 0; i < count; i++) {
            arena.addItem(new Obstacle(canvas.getWidth(), canvas.getHeight()));
        }
        drawArena(); // Redraw the arena after adding obstacles
    }

    /**
     * Gets the canvas to be displayed in the UI.
     *
     * @return The canvas.
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Draws arena, including robots and obstacles.
     */
    public void drawArena() {
        // Clear the canvas
        graphics.setFill(Color.WHITE);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw the arena border
        graphics.setStroke(Color.BLACK);
        graphics.setLineWidth(5);
        graphics.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw all robots and obstacles in the arena
        arena.drawAll(graphics);
        userRobot.draw(graphics);  // Draw the user-controlled robot
    }

    /**
     * Removes the last added obstacle and redraws the arena.
     */
    public void removeLastObstacle() {
        arena.removeLastObstacle(); // Remove the last obstacle added by the user
        drawArena(); // Redraw the arena
    }

    /**
     * Adds a robot to the arena and redraws it.
     *
     * @param robot The robot to add.
     */
    public void addRobot(Robot robot) {
        arena.addItem(robot); // Add the robot to the arena
        drawArena(); // Redraw the arena
    }

    /**
     * Adds a random obstacle to the arena and redraws it.
     */
    public void addObstacle() {
        Obstacle obstacle = new Obstacle(canvas.getWidth(), canvas.getHeight());
        arena.addItem(obstacle); // Add the new obstacle to the arena
        drawArena(); // Redraw the arena
    }


    /**
     * Starts the simulation, enabling robot movement and behaviours.
     */
    public void startSimulation() {
        if (isRunning) return; // Avoid starting multiple times
        isRunning = true;

        time = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Move robots and handle interactions
                for (ArenaItem item : arena.getItems()) {
                    if (item instanceof Robot robot) {
                        robot.move(); // Move the robot

                        // Check for collisions with obstacles
                        for (ArenaItem obstacle : arena.getItems()) {
                            if (obstacle instanceof Obstacle) {
                                if (robot instanceof RegularRobot bumpRobot) {
                                    bumpRobot.detectCollision((Obstacle) obstacle);
                                } else if (robot instanceof WhiskerRobot whiskerRobot) {
                                    whiskerRobot.detectCollision((Obstacle) obstacle);
                                } else if (robot instanceof GhostRobot ghostRobot) {
                                    ghostRobot.detectProximity((Obstacle) obstacle);
                                }
                            }
                        }
                    }
                }

                // Detect and remove robots in the user-controlled robot's detection cone
                userRobot.detectAndRemoveRobots(arena.getItems());

                drawArena(); // Redraw the arena after updates
            }
        };
        time.start();
    }

    /**
     * Pauses the simulation, stopping robot movement.
     */
    public void pauseSimulation() {
        if (!isRunning) return; // Avoid pausing if not running
        isRunning = false;
        time.stop();
    }

    /**
     * Returns the RobotArena instance managed by this canvas.
     *
     * @return The RobotArena object.
     */
    public RobotArena getArena() {
        return arena;
    }
    
    /**
     * Sets the arena state.
     *
     * @param arena The new arena state to set.
     */
    public void setArena(RobotArena arena) {
        this.arena = arena;  // Set the new arena
        drawArena();  // Redraw the arena
    }

    /**
     * Handles key press events to control the user-controlled robot.
     *
     * @param event The key event triggered by the user.
     */
    private void handleKeyPress(KeyEvent event) {
        userRobot.handleKeyInput(event);  // Delegate key press handling to the user-controlled robot
    }
}
