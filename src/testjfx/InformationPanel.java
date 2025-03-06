package testjfx;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class InformationPanel {
    private final VBox panel;
    private final Label label;
    private static final int MAX_INFO = 6;  // Maximum number of information items to keep

    public InformationPanel() {
        label = new Label("Robot Info:");
        panel = new VBox(10, label);

        // Setting style, colour and other
        panel.setStyle("-fx-padding: 10; -fx-background-color: #4CAF50; -fx-text-fill: white;  " +
                       " -fx-border-width: 2;");
        panel.setMinWidth(200);  // Setting fixed width for the InformationPanel
        panel.setMinHeight(600); // Setting fixed height for the InformationPanel
    }

    public VBox getPanel() {
        return panel;
    }

    public void updateInfo(String info) {
        // Create a new Text object for the new info
        Text newInfo = new Text(info + "\n");

        // Add the new info to the VBox
        panel.getChildren().add(newInfo);

        // If the VBox has more than MAX_INFO items, remove the oldest one
        if (panel.getChildren().size() > MAX_INFO) {
            panel.getChildren().remove(1);  // Removes the first item after the info label
        }
    }
}
