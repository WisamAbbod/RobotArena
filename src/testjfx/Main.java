package testjfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Wisam's Robot Arena");

        BorderPane borderz = new BorderPane();
        ArenaCanvas arenaCanvas = new ArenaCanvas(700, 600); // ArenaCanvas without initial user-controlled robot
        InformationPanel infoPanel = new InformationPanel();
        ToolBar controlPanel = new ToolBar(primaryStage, arenaCanvas, infoPanel);

        // Add the user-controlled robot to the arena (only once)
     //   UserControlledRobot userRobot = new UserControlledRobot(350, 300);  // Add robot at the center
     //   arenaCanvas.addRobot(userRobot);  // Add the user-controlled robot to the arena

        // Set the arenaCanvas in the centre
        borderz.setCenter(arenaCanvas.getCanvas());

        // Set the fixed size information panel to the left side
        borderz.setLeft(infoPanel.getPanel());

        // Set the control panel at the bottom
        borderz.setBottom(controlPanel.getPanel());

        // Create the scene and set the primary stage
        Scene scene = new Scene(borderz, 1000, 700);
        primaryStage.setScene(scene);

        // Ensure the canvas receives focus for key events
        scene.setOnMouseClicked(e -> arenaCanvas.getCanvas().requestFocus());

        primaryStage.show();

        // Draw the arena
        arenaCanvas.drawArena();

        // Start the simulation if necessary
        arenaCanvas.startSimulation();  // Start the simulation (robot movements, etc.)
    }

    public static void main(String[] args) {
        launch(args);
    }
}

