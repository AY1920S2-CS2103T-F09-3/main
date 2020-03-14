package igrad.ui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * A (permanent) top-panel that displays course status information.
 */
public class StatusBar extends UiPart<Region> {

    private static final String FXML = "StatusBar.fxml";

    @FXML
    private StackPane pane;

    @FXML
    private Label course;

    @FXML
    private Label capMcDisplay;

    public StatusBar() {
        super(FXML);

        pane = new StackPane();

        course.setText("Course: ");
        capMcDisplay.setText("CAP: 4.24     Target: 4.00     Required: 3.90/Sem");

        StackPane.setAlignment(course, Pos.CENTER_LEFT);
        StackPane.setAlignment(capMcDisplay, Pos.CENTER_RIGHT);
        pane.getChildren().addAll(course, capMcDisplay);
    }

    public void setCourseName(String courseName) {
        course.setText("Course: " + courseName);
    }

    public StackPane getPane() {
        return this.pane;
    }
}
