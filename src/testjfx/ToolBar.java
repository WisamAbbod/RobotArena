package testjfx;

import javax.swing.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.*;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides user controls for interacting with the robot arena.
 */
public class ToolBar {
    private final HBox panel;
    private final MenuBar menu;

    /**
     * Constructs the control panel with buttons and menus for user interaction.
     *
     * @param primaryStage The primary stage of the application.
     * @param arenaCanvas  The canvas managing the robot arena.
     * @param infoPanel    The panel displaying robot information.
     */
    public ToolBar(Stage primaryStage, ArenaCanvas arenaCanvas, InformationPanel infoPanel) {

        // Add two initial bump robots to the arena
        arenaCanvas.addRobot(new RegularRobot(getRandomX(), getRandomY()));
        arenaCanvas.addRobot(new RegularRobot(getRandomX(), getRandomY()));

        // Buttons
        Button startButton = new Button("Start");
        Button pauseButton = new Button("Pause");
        Button addObstacleButton = new Button("Add Obstacle");
        Button deleteObstacleButton = new Button("Delete Obstacle");
        Button resetButton = new Button("New Arena");
        Button quitButton = new Button("Quit");
        Button aboutButton = new Button("About&Help");

        // Apply custom style to buttons
        String buttonStyle = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;";
        startButton.setStyle(buttonStyle);
        pauseButton.setStyle(buttonStyle);
        addObstacleButton.setStyle(buttonStyle);
        deleteObstacleButton.setStyle(buttonStyle);
        resetButton.setStyle(buttonStyle);
        aboutButton.setStyle(buttonStyle);
        quitButton.setStyle(buttonStyle);

        // Button Actions
        startButton.setOnAction(e -> {
            arenaCanvas.startSimulation();
            infoPanel.updateInfo("Started the simulation.");
        });

        pauseButton.setOnAction(e -> {
            arenaCanvas.pauseSimulation();
            infoPanel.updateInfo("Simulation paused.");
        });

        addObstacleButton.setOnAction(e -> {
            arenaCanvas.addObstacle(); // Add a new obstacle
            infoPanel.updateInfo("Added random obstacle.");
        });

        deleteObstacleButton.setOnAction(e -> {
            arenaCanvas.removeLastObstacle(); // Remove the last obstacle
            infoPanel.updateInfo("Last added obstacle Deleted.");
        });

        resetButton.setOnAction(e -> {
            arenaCanvas.setArena(new RobotArena());  // Clear the arena and set a new RobotArena
            arenaCanvas.addRobot(new RegularRobot(getRandomX(), getRandomY())); // Add initial robots
            arenaCanvas.addRobot(new RegularRobot(getRandomX(), getRandomY()));
            infoPanel.updateInfo("Arena reset to initial state.");
        });

        quitButton.setOnAction(e -> System.exit(0)); // Quit the application

        aboutButton.setOnAction(e -> {
            Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
            aboutDialog.setTitle("About Wisam's Robot Arena");
            aboutDialog.setHeaderText("Welcome to Wisam's Robot Arena!");
            aboutDialog.setContentText("""
                    This application allows you to simulate robots interacting in an arena.
                    Features:
                    - Add various types of robots (Regular Robot, Whisker Robot, Ghost Robot).
                    - Place and remove obstacles dynamically.
                    - Control a user-controlled robot with WASD keys.
                    - Simulate robot movements and interactions in real-time.

                    Enjoy experimenting with the arena!
                    """);
            aboutDialog.showAndWait();
        });

        // Assemble panel with buttons
        HBox buttonPanel = new HBox(10, startButton, pauseButton, addObstacleButton, deleteObstacleButton, resetButton, aboutButton, quitButton);
        buttonPanel.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        // File Menu
        Menu fileMenu = new Menu("File");
        MenuItem saveOption = new MenuItem("Save");
        MenuItem loadOption = new MenuItem("Load");
        MenuItem quitOption = new MenuItem("Quit");

        saveOption.setOnAction(e -> saveArenaState(arenaCanvas));
        loadOption.setOnAction(e -> loadArenaState(arenaCanvas));
        quitOption.setOnAction(e -> primaryStage.close()); // Close the application
        fileMenu.getItems().addAll(saveOption, loadOption, quitOption);

        // Robot Menu
        Menu robotMenu = new Menu("Select Robot");
        MenuItem bumpRobot = new MenuItem("Regular Robot");
        MenuItem whiskerRobot = new MenuItem("Whisker Robot");
        MenuItem ghostRobot = new MenuItem("Ghost Robot");

        bumpRobot.setOnAction(e -> arenaCanvas.addRobot(new RegularRobot(getRandomX(), getRandomY())));
        whiskerRobot.setOnAction(e -> arenaCanvas.addRobot(new WhiskerRobot(getRandomX(), getRandomY())));
        ghostRobot.setOnAction(e -> arenaCanvas.addRobot(new GhostRobot(getRandomX(), getRandomY())));
        robotMenu.getItems().addAll(bumpRobot, whiskerRobot, ghostRobot);

        // Initialise MenuBar
        menu = new MenuBar(fileMenu, robotMenu);
        menu.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        // Assemble final tool bar with both button and menu bar
        HBox menuPanel = new HBox(menu);  // Place menu bar in its own HBox
        menuPanel.setStyle("-fx-padding: 10; -fx-alignment: center-right;");  // Align the menu to the right

        // Final tool bar layout (buttons on the left, menu bar on the right)
        panel = new HBox(10, buttonPanel, menuPanel);
        panel.setStyle("-fx-padding: 10; -fx-alignment: center;");
    }

    /**
     * Returns the control panel as an HBox.
     *
     * @return The control panel.
     */
    public HBox getPanel() {
        return panel;
    }

    private int getRandomX() {
        return ThreadLocalRandom.current().nextInt(0, 650);
    }

    private int getRandomY() {
        return ThreadLocalRandom.current().nextInt(0, 550);
    }

    private void saveArenaState(ArenaCanvas arenaCanvas) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Arena State");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arena Files", "arena"));
        int userChoice = fileChooser.showSaveDialog(null);

        if (userChoice == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().endsWith(".arena")) {
                file = new File(file.getAbsolutePath() + ".arena");
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(arenaCanvas.getArena());
                System.out.println("Arena saved to: " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadArenaState(ArenaCanvas arenaCanvas) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load Arena State");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arena Files", "arena"));
        int userChoice = fileChooser.showOpenDialog(null);

        if (userChoice == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                RobotArena arena = (RobotArena) ois.readObject();
                arenaCanvas.setArena(arena);
                arenaCanvas.drawArena();
                System.out.println("Arena loaded from: " + file.getAbsolutePath());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

