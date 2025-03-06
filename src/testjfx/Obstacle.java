package testjfx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

/**
 * Represents a circular obstacle in the robot arena.
 */
public class Obstacle extends ArenaItem {
    private final double sizing; // Radius of the obstacle

    /**
     * Creates a new obstacle at a random position in the arena.
     *
     * @param arenaWidth  The width of the arena.
     * @param arenaHeight The height of the arena.
     */
    public Obstacle(double arenaWidth, double arenaHeight) {
        super(randomCoordinate(arenaWidth), randomCoordinate(arenaHeight));
        this.sizing = 20; // Medium-sized circle
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(x - sizing, y - sizing, 2 * sizing, 2 * sizing);
    }

    /**
     * Generates a random coordinate within the arena boundaries.
     *
     * @param max The maximum allowable value for the coordinate.
     * @return A random coordinate within the range [20, max - 20].
     */
    private static double randomCoordinate(double max) {
        Random random = new Random();
        return 20 + random.nextDouble() * (max - 40); // Keep obstacles within boundaries
    }
}

