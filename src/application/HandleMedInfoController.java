package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class HandleMedInfoController {
	
	@FXML
	private TextField nameOfMedTextField;

	@FXML
	private TextField dosageOfMedTextField;

	@FXML
	private ChoiceBox <String> dosageUnitChoiceBox;

	@FXML
	private TextField dailyScheduleTextField;

	@FXML
	private TextField timelyScheduleTextField;

	@FXML
	private Label formHeadingLabel;
	
	@FXML
	public void toggleMainView(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("MainDisplayPane.fxml"));
		Stage addMedWindow = (Stage)((Node)e.getSource()).getScene().getWindow() ;
		Scene addMedView = new Scene(root);
		addMedWindow.setScene(addMedView) ;
		addMedWindow.show();
	}

}
