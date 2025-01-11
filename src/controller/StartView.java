package controller;

import java.io.IOException;	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StartView extends Application{
	
	@Override
	public void start(Stage stage) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("StartView.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
