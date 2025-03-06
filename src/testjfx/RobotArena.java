package testjfx;

import java.util.ArrayList;
import java.util.List;
//import java.util.stream.Collectors;
import javafx.scene.canvas.GraphicsContext;

/**
 * Manages robots and obstacles in the arena.
 */
public class RobotArena {
    private final List<ArenaItem> items; // Holds all robots and obstacles

    /**
     * Constructs a new RobotArena and initialises with default items.
     */
    public RobotArena() {
        this.items = new ArrayList<>();
        // Add a default obstacle
        items.add(new Obstacle(400, 300)); // Default obstacle
    }

    /**
     * Adds an item (robot or obstacle) to the arena.
     *
     * @param item The item to add.
     */
    public void addItem(ArenaItem item) {
        items.add(item);
    }

    /**
     * Removes the last obstacle from the arena.
     */
    public void removeLastObstacle() {
        for (int i = items.size() - 1; i >= 0; i--) {
            if (items.get(i) instanceof Obstacle) {
                items.remove(i); // Remove the obstacle
                break; // Remove only the last obstacle
            }
        }
    }

    /**
     * Returns all items in the arena.
     *
     * @return A list of all items in the arena.
     */
    public List<ArenaItem> getItems() {
        return items;
    }

    /**
     * Returns a list of all robots in the arena.
     *
     * @return A list of robots in the arena.
     */
  //  public List<Robot> getRobots() {
       // return items;
         
  //  }

    /**
     * Draws all items (robots and obstacles) in the arena.
     *
     * @param graphics The graphics context to draw on.
     */
    public void drawAll(GraphicsContext graphics) {
        for (ArenaItem item : items) {
            item.draw(graphics);
        }
    }

    public void removeItem(Robot robot) {
        // Loop through all items in the arena
        for (int i = 0; i < items.size(); i++) {
            ArenaItem item = items.get(i);
            
            // Checks if item is the robot that needs to be removed
            if (item == robot) {
                items.remove(i);  // Removes robot from list
                break;  
            }
        }
    }

}


