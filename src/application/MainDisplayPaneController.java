package application;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.util.Duration;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainDisplayPaneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label currentTime;

    @FXML
    void initialize() {
    	//using a time-based animation to display the current time
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalTime time = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            String formattedTime = time.format(formatter);
            currentTime.setText(formattedTime);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
