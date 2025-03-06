package testjfx;


import javafx.scene.canvas.GraphicsContext;

/**
 * Represents a generic item in the arena.
 */
public abstract class ArenaItem {
    protected double x, y;

    public ArenaItem(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draw the item on the canvas.
     * @param graphics The graphics context for drawing.
     */
    public abstract void draw(GraphicsContext graphics);

    // Getters for position
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
