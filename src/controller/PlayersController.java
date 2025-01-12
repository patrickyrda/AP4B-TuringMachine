package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PlayersController {
	private int playerscount;
	private int probnumber;
	private GameController maincontroller;
	@FXML
	private TextField player1input, player2input, player3input, player4input, codep1, codep2, codep3, codep4;
	@FXML
	private Text textp1, textp2, textp3, textp4;
	
	
	@FXML
	public void initialize() {
        applyInputValidation(codep1);
        applyInputValidation(codep2);
        applyInputValidation(codep3);
        applyInputValidation(codep4);
    }
	
	private void applyInputValidation(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            
            if (newValue.length() > 3) {
                textField.setText(oldValue); 
            } else if (newValue.matches("[1-5]{1}[1-5]{1}[1-5]{1}")) {
                int value = Integer.parseInt(newValue);
                if (value < 111 || value > 555) {
                    textField.setText(oldValue); 
                }
            } else if (!newValue.isEmpty() && !newValue.matches("[1-5]*")) {
                textField.setText(oldValue); 
            }
        });
    }
	
	public void setVisible() {
		player2input.setVisible(playerscount >= 2);
        player3input.setVisible(playerscount >= 3);
        player4input.setVisible(playerscount == 4);
        codep2.setVisible(playerscount >= 2);
        codep3.setVisible(playerscount >= 3);
        codep4.setVisible(playerscount == 4);
        textp2.setVisible(playerscount >= 2);
        textp3.setVisible(playerscount >= 3);
        textp4.setVisible(playerscount == 4);
        
	}
	
	public void setCountNumber(int playerscount, int probnumber) {
		this.playerscount = playerscount;
		this.probnumber = probnumber;
	}
	
	public void handlePlayer(ActionEvent event) throws IOException {
		
		if (!isAllFieldsFilled()) {
            showWarning("Veuillez bien completer les valeurs.");
            return; 
        }
		
		List<String> names = new ArrayList<>();
		List<Integer> codes = new ArrayList<>();
		
		 if (playerscount >= 1) {
		        names.add(player1input.getText());
		        codes.add(Integer.parseInt(codep1.getText())); 
		 }
		 if (playerscount >= 2) {	
			 names.add(player2input.getText());
			 codes.add(Integer.parseInt(codep2.getText()));
		 }
		 if (playerscount >= 3) {
			 names.add(player3input.getText());
			 codes.add(Integer.parseInt(codep3.getText()));
		 }
		 if (playerscount == 4) {
			 names.add(player4input.getText());
			 codes.add(Integer.parseInt(codep4.getText()));
		 }
		 
		 maincontroller = new GameController();
		 maincontroller.gameInitializeGUI(probnumber, playerscount, names, codes);
		 
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
		 loader.setController(maincontroller);
		 AnchorPane thirdPageRoot = loader.load();
		 Scene thirdscene = new Scene(thirdPageRoot);
		 Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		 stage.setScene(thirdscene);
		
		 stage.show();
		 
		 
	}
	
	private boolean isAllFieldsFilled() {
        if (playerscount >= 1 && (player1input.getText().isEmpty() || codep1.getText().isEmpty() || codep1.getText().length() < 3)) {
            return false;
        }
        if (playerscount >= 2 && (player2input.getText().isEmpty() || codep2.getText().isEmpty() || codep2.getText().length() < 3)) {
            return false;
        }
        if (playerscount >= 3 && (player3input.getText().isEmpty() || codep3.getText().isEmpty() || codep3.getText().length() < 3)) {
            return false;
        }
        if (playerscount == 4 && (player4input.getText().isEmpty() || codep4.getText().isEmpty() || codep4.getText().length() < 3)) {
            return false;
        }
        return true;
    }
	
	private void showWarning(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Erreur dans les valeurs insérés");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	
	
}
