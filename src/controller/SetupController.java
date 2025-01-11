package controller;




import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SetupController {
	int probnumber;
	int playerscount;
	
	@FXML
	private RadioButton count1, count2, count3, count4;
	
	@FXML
	private Button prob1, prob2, prob3, prob4, valider;
		
	public void handleProbButtons(ActionEvent event) throws IOException {
		if (event.getSource() == prob1) {
			probnumber = 1;
		} else if (event.getSource() == prob2) {
			probnumber = 2;
		} else if (event.getSource() == prob3) {
			probnumber = 3;
		} else if (event.getSource() == prob4) {
			probnumber = 4;
		}
		
		if (count1.isSelected()) {
			playerscount = 1;
		} else if (count2.isSelected()) {
			playerscount = 2;
		} else if (count3.isSelected()) {
			playerscount = 3;
		} else if (count4.isSelected()) {
			playerscount = 4;
		}
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AddNameView.fxml"));
		AnchorPane secondPageRoot = loader.load();
		PlayersController next_controller = loader.getController();
		next_controller.setCountNumber(playerscount, probnumber);
		Scene secondscene = new Scene(secondPageRoot);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(secondscene);
		next_controller.setVisible();
	
		stage.show();
		
		System.out.println("Itworkd");
		
	} 

	
	
	
}
