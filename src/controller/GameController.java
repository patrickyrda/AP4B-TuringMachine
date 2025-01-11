package controller;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;	
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.Node;

import model.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameController implements Observer{
	
	private GameState game;
	private boolean running;
	private int problemnb;
	private int selectedCriteriaCount = 0;
	private int winnerscount = 0;
	
	//for the GUI
	@FXML
	private Text roundnumber, circlecode, trianglecode, squarecode, roundplayername, criteriatext;
	@FXML 
	private CheckBox criteria1, criteria2, criteria3, criteria4, criteria5;
	@FXML
	private Button guessbutton, skipbutton, yesguess, noguess, continuebutton;
	@FXML
	private TextField guessinput;
	@FXML
	private Circle criteria1answer, criteria2answer, criteria3answer, criteria4answer, criteria5answer;
	@FXML
	private void initialize() {
		criteria1.setOnAction(event -> handleCheckboxSelection(event));
		criteria2.setOnAction(event -> handleCheckboxSelection(event));
        criteria3.setOnAction(event -> handleCheckboxSelection(event));
        criteria4.setOnAction(event -> handleCheckboxSelection(event));
        criteria5.setOnAction(event -> handleCheckboxSelection(event));  
        skipbutton.setOnAction(event -> handleSkipButton(event));
        continuebutton.setOnAction(event -> handleContinueButton(event));
        guessbutton.setOnAction(event -> handleGuessButton());
        roundnumber.setText("Round number 1");
        setCurrentPlayerData();
        updateCriteriaVisibility();
        guessbutton.setVisible(false);
        yesguess.setVisible(false);
        noguess.setVisible(false);
        guessinput.setVisible(false);
        continuebutton.setVisible(false);
        
       
	}
	
	private void setCurrentPlayerData() {
		int currentPlayerCode = game.getCurrentPlayerCode();
        int circle = currentPlayerCode / 100;
        int triangle = (currentPlayerCode / 10) % 10;
        int square = currentPlayerCode % 10;
        circlecode.setText(String.valueOf(circle));
        trianglecode.setText(String.valueOf(triangle));
        squarecode.setText(String.valueOf(square));
        roundplayername.setText(game.getCurrentPlayerName());
	}
	
	private void updateCriteriaVisibility() {
	    // Get the criteria count from the game instance
	    int criteriaCount = game.getCriteriaCount();

	    // Set visibility based on the criteria count
	    criteria1.setVisible(criteriaCount >= 1);
	    criteria2.setVisible(criteriaCount >= 2);
	    criteria3.setVisible(criteriaCount >= 3);
	    criteria4.setVisible(criteriaCount >= 4);
	    criteria5.setVisible(criteriaCount >= 5);
	    criteria1answer.setVisible(criteriaCount >= 1);
	    criteria2answer.setVisible(criteriaCount >= 2);
	    criteria3answer.setVisible(criteriaCount >= 3);
	    criteria4answer.setVisible(criteriaCount >= 4);
	    criteria5answer.setVisible(criteriaCount >= 5);
	    
	    if (criteriaCount >= 1) {
	        criteria1.setText(game.getCriteriaDescription(0));
	    }
	    if (criteriaCount >= 2) {
	        criteria2.setText(game.getCriteriaDescription(1));
	    }
	    if (criteriaCount >= 3) {
	        criteria3.setText(game.getCriteriaDescription(2));
	    }
	    if (criteriaCount >= 4) {
	        criteria4.setText(game.getCriteriaDescription(3));
	    }
	    if (criteriaCount >= 5) {
	        criteria5.setText(game.getCriteriaDescription(4));
	    }
	    criteria1.setSelected(false);
	    criteria2.setSelected(false);
	    criteria3.setSelected(false);
	    criteria4.setSelected(false);
	    criteria5.setSelected(false);
	}
	
	
	private void handleCheckboxSelection(Event event) {
		selectedCriteriaCount = 0;
        if (criteria1.isSelected()) selectedCriteriaCount++;
        if (criteria2.isSelected()) selectedCriteriaCount++;
        if (criteria3.isSelected()) selectedCriteriaCount++;
        if (criteria4.isSelected()) selectedCriteriaCount++;
        if (criteria5.isSelected()) selectedCriteriaCount++;
        
        if (selectedCriteriaCount >= 3) {
            if (!criteria1.isSelected()) criteria1.setDisable(true);
            if (!criteria2.isSelected()) criteria2.setDisable(true);
            if (!criteria3.isSelected()) criteria3.setDisable(true);
            if (!criteria4.isSelected()) criteria4.setDisable(true);
            if (!criteria5.isSelected()) criteria5.setDisable(true);
        } else {
            criteria1.setDisable(false);
            criteria2.setDisable(false);
            criteria3.setDisable(false);
            criteria4.setDisable(false);
            criteria5.setDisable(false);
        }
        
        String checkboxId = ((CheckBox) event.getSource()).getId(); 
        int criteriaNumber = Integer.parseInt(checkboxId.replaceAll("\\D", ""));
        game.askCriteria(criteriaNumber - 1);
	}
	
	private void handleSkipButton(Event event) {
		skipbutton.setVisible(false);
		continuebutton.setVisible(true);
		criteria1.setVisible(false);
	    criteria2.setVisible(false);
	    criteria3.setVisible(false);
	    criteria4.setVisible(false);
	    criteria5.setVisible(false);
	    criteriatext.setVisible(false);
	    criteria1answer.setVisible(false);
	    criteria2answer.setVisible(false);
	    criteria3answer.setVisible(false);
	    criteria4answer.setVisible(false);
	    criteria5answer.setVisible(false);
	    guessinput.setVisible(true);
	    guessbutton.setVisible(true);
	}
	
	private void handleContinueButton(Event event) {
		game.prepareNextStep();
		setCurrentPlayerData();
		continuebutton.setVisible(false);
		guessinput.setVisible(false);
	    guessbutton.setVisible(false);
	    skipbutton.setVisible(true);
	    updateCriteriaVisibility();
	    criteriatext.setVisible(true);
	    criteria1answer.setFill(Color.WHITE);
	    criteria2answer.setFill(Color.WHITE);
	    criteria3answer.setFill(Color.WHITE);
	    criteria4answer.setFill(Color.WHITE);
	    criteria5answer.setFill(Color.WHITE);
	    
	}
	
	
	public GameController() {
		running = true;
	}
	//valid
	public void update(String msg) {
		
		Pattern pattern = Pattern.compile("([^/]+)/(.+)");
		Matcher matcher = pattern.matcher(msg);
		String message;
		String specific;
		
		if (matcher.matches()) {
			message = matcher.group(1);
			specific = matcher.group(2);
			
			switch(message) {
			case "EndGame":
				running = false;
				if (specific.equals("LOST")) {
					System.out.println("Everyone Lost");
					//here have to control case when theres a winner already, when there is i only need to say that they lost, but they dont lose points!
					endPopUp();
				}
				break;
			case "WrongGuess":
				System.out.println("Bossman "+specific+" you lost!");
				showWinPopup("Vous avez perdu "+specific);
				break;
			case "RightGuess":
				winnerscount += 1;
				if (game.getPlayerCount() < 1) {
					winnerscount = 2;
					
				}
				if (winnerscount == 2) {
					showWinPopup("Vous avez reussi! Rencontrez le prof dans la salle B + votre code! Malheuresmenet personne peut plus gagner!");
					System.exit(0);
				} else {
					showWinPopup("Vous avez reussi! Rencontrez le prof dans la salle B + votre code!");
				}
				break;
			case "Correct":
				System.out.println("KRA, the guess of criteria "+specific+" is right");
				int criteriaint = Integer.parseInt(specific);
				switch(criteriaint) {
				case 0:
					criteria1answer.setFill(Color.GREEN);
					break;
				case 1:
					criteria2answer.setFill(Color.GREEN);
					break;
				case 2:
					criteria3answer.setFill(Color.GREEN);
					break;
				case 3:
					criteria4answer.setFill(Color.GREEN);
					break;
				case 4:
					criteria5answer.setFill(Color.GREEN);
					break;
				default:
					System.out.println("Error in criteria number!");
					break;
				}
				break;
			case "False":
				System.out.println("KRA, the guess of criteria "+specific+" is wrong");
				int criteriaintfalse = Integer.parseInt(specific);
				switch(criteriaintfalse) {
				case 0:
					criteria1answer.setFill(Color.RED);
					break;
				case 1:
					criteria2answer.setFill(Color.RED);
					break;
				case 2:
					criteria3answer.setFill(Color.RED);
					break;
				case 3:
					criteria4answer.setFill(Color.RED);
					break;
				case 4:
					criteria5answer.setFill(Color.RED);
					break;
				default:
					System.out.println("Error in criteria number!");
					break;
				}
				break;
			case "roundnumber":
				System.out.println("Round number "+specific+" is starting");
				int specificinte = Integer.parseInt(specific);
				specificinte += 1;
				roundnumber.setText("Round "+specificinte);
				break;
			case "round":
				System.out.println("Player is "+specific);
				break;
			default: 
				System.out.println("ABEG, i do not know this one sef");
				break;
				
			};
		} else {
			System.out.println("Bro i do not know what happened!");
		}
		
		
	}
	
	public void gameInitializeGUI(int probnumber, int playerscount, List<String> names, List<Integer> codes) { 
		game = new GameState(probnumber);
		game.addObserver(this);
		for (int i = 0; i < playerscount; ++i) {
			game.addPlayer(new Player(codes.get(i), names.get(i)));
		}
		problemnb = probnumber;
		
		game.printtest();
		
	}
	
	private void handleGuessButton() {
		int guessvalue = Integer.parseInt(guessinput.getText());
		game.takeGuess(guessvalue);
	}
	
	
	
	//Valid
	public void gameInitialize() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Abeg enter the problem number");
		int probnumber = scanner.nextInt();
		game = new GameState(probnumber);
		game.addObserver(this);
		System.out.println("Abeg now how many players");
		int playerscount = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < playerscount; ++i) {
			System.out.println("Abeg what be ur name");
			String name = scanner.nextLine();
			System.out.println("Abeg what be your code");
			int code = scanner.nextInt();
			scanner.nextLine();
			game.addPlayer(new Player(code, name));
		}
		//scanner.close();
		/* USED FOR TEST REASONS
		System.out.println(game.getProblemDescription());
		System.out.println("Criteria count: "+game.getCriteriaCount());
		System.out.println("Player count: "+game.getPlayerCount()); */
	}
	//Valid
	public void guessOption() {
		//TODO: Maybe take out the logic for wanting to guess or not like i did wth askOption and handle outside of the funciton !
		Scanner scanner = new Scanner(System.in);
		String answer = "y";
		if (game.getRound() < 3) {
			System.out.println("Abeg do u want to guess? (y/n)");
			answer = scanner.nextLine();
		}
		if (answer.equals("y")) {
			System.out.println("Abeg what be your code");
			int guesscode = scanner.nextInt();
			game.takeGuess(guesscode);
			//scanner.close();
		} else if (answer.equals("n")) {
			System.out.println("Passing to next player");
		} else {
			System.out.println(answer);
		}
	}
	//valid TODO: Have to handle case not to repeat asking same criteria !maybe!
	
	
	
	
	
	public void askOption() {
		Scanner scanner = new Scanner(System.in);
		for (int i = 0; i < game.getCriteriaCount(); ++i) {
			System.out.println((i+1)+". "+game.getCriteriaDescription(i));
		}
		//TODO: ask here if the person wants to ask or not and return if not !!!! HAVE TO ADD THIS IN THE GUI 
		System.out.println("Check the criterias you fit ask");
		int idx = 1;
		while(idx < 4) { 
			System.out.println("Abeg bro make u choose an option");
			scanner.nextLine();
			int criteriaNumber = scanner.nextInt();
			scanner.nextLine();
			game.askCriteria(criteriaNumber);
			String answer = "n";
			if (idx < 3) {
				System.out.println("Do u want to ask another (y/n)");
				answer = scanner.nextLine().trim();
			}
			if (answer.equals("y")) {
				++idx;
			} else {
				idx = 4;
			}
			
		}
		//scanner.close();
	}
	
	public void play() {
		//Scanner scanner = new Scanner(System.in);
		while (running) {
			int playercount = game.getPlayerCount();
			System.out.println("Player count is : "+playercount);
			for (int i = 0; i < playercount; ++i) {	
				
				//TODO: Handle here if person wants to ask question !!
				askOption();
				
				guessOption();
				game.nextPlayer();
				selectedCriteriaCount = 0;
				
			}
			//avoid sending a no one won warning if players get correct on last round
			if (running) { 
				game.nextRound();
			}
			//Left tests to do : i have to check if in last round winning wont triiger the one above AND check if one winning does not stop others from also winning
		}
	}
	
	private void endPopUp() {
		// Create a new Stage (popup window)
	    Stage popupStage = new Stage();
	    popupStage.setTitle("Warning");

	    // Create a Label for the warning message
	    Label warningMessage = new Label("VOUS AVEZ TOUS PERDU, -5 POUR TOUS!!!!!");
	    warningMessage.setStyle("-fx-font-size: 16px; -fx-text-fill: red; -fx-font-weight: bold;");
	    warningMessage.setWrapText(true);
	    warningMessage.setTextAlignment(TextAlignment.CENTER);

	    // Create the "End" button
	    Button endButton = new Button("End");
	    endButton.setStyle("-fx-font-size: 14px;");
	    endButton.setOnAction(event -> {
	        System.exit(0); // Terminate the program
	    });

	    // Layout for the popup
	    VBox popupLayout = new VBox(20, warningMessage, endButton);
	    popupLayout.setAlignment(Pos.CENTER);
	    popupLayout.setPadding(new Insets(20));

	    // Create a Scene for the popup
	    Scene popupScene = new Scene(popupLayout, 300, 150);
	    popupStage.setScene(popupScene);

	    // Make the popup modal (blocks interaction with other windows)
	    popupStage.initModality(Modality.APPLICATION_MODAL);

	    // Show the popup
	    popupStage.showAndWait();
	}
	
	private void showWinPopup(String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("Félicitations!");
	    alert.setHeaderText("Bien joué!");
	    alert.setContentText(msg);
	    alert.showAndWait();
	}
		
}
