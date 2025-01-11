package model;
import java.util.List;
import java.util.ArrayList;

public class GameState extends Subject{
	private List<Player> players = new ArrayList<>(); //here i will have to change for observers
	private List<Player> kickedPlayers = new ArrayList<>();
	private int currentPlayeridx;
	private int round;
	private Problem problem;
	private String gameState;
	
	
	
	public GameState(int problemNumber) {
		round = 0;
		currentPlayeridx = 0;
		problem = ProblemFactory.getProblem(problemNumber);
	}
	//TESTED
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	public void printtest() {
		for (Player p : players) {
			System.out.println("Player name: "+p.getName()+" and code is : "+p.getPunchCode());
		}
	}
	
	public String getCurrentPlayerName() {
		return players.get(currentPlayeridx).getName();
	}
	
	public int getCurrentPlayerCode() {
		return players.get(currentPlayeridx).getPunchCode();
	}
	
	public String getCriteriaDescription(int criteriaNumber) {
		return problem.getCriteria(criteriaNumber).getDescription();
	}
	
	public String getProblemDescription() {
		return problem.getDesc();
	}
	//TESTED
	public int getCriteriaCount() {
		return problem.getCriteriaCount();
	}
	//from here functions only to help the test
	public int getidx() {
		return currentPlayeridx;
	}
	//valid
	public void setidx(int id) {
		currentPlayeridx = id;
	}
	
	public List<Player> getKickedPlayers() {
		return kickedPlayers;
	}
	//to here
	//valid
	public void nextPlayer() {
	
		if (getPlayerCount() == 0) {
			return;
		}
		currentPlayeridx = (currentPlayeridx + 1) % players.size();	
		notifyObservers("round/"+players.get(currentPlayeridx).getName());
	}
	
	public void nextRound() {
		round++;
		currentPlayeridx = 0;
		notifyObservers("roundnumber/"+round);
		if (round == 4) {
			endGame("LOST");
		}
	}
	//valid
	public int getRound() {
		return round;
	}
	//validated
	public int getPlayerCount() {
		return players.size();
	}
	
	//validated
	public void askCriteria(int criterianumber) {
		boolean actionresult = players.get(currentPlayeridx).askCriteria(problem.getCriteria(criterianumber));
		if (actionresult == true) {
			gameState = "Correct/"+criterianumber;
			notifyObservers(gameState);
		} else {
			gameState = "False/"+criterianumber;
			notifyObservers(gameState);
		}
	}
	//valid
	private void kickPlayer() {
		kickedPlayers.add(players.get(currentPlayeridx));
		players.remove(players.get(currentPlayeridx));
		if (currentPlayeridx == 0) {
			if (getPlayerCount() == 1) {
				--round;
			}
			return;
		}
		currentPlayeridx -=1;
		
	}
	//valid
	public void takeGuess(int guess) {
		boolean actionresult = players.get(currentPlayeridx).takeGuess(problem, guess);
		if (actionresult == true) {
			//gameState = "CorrectGuess";
			//notifyObservers(gameState);
			gameState = "RightGuess/"+players.get(currentPlayeridx).getName();
			kickPlayer();
			notifyObservers(gameState);
		} else {
			//publish other message, might need to handle here end of the round
			gameState = "WrongGuess/"+players.get(currentPlayeridx).getName();
			kickPlayer();
			if (getPlayerCount() == 0) {
				endGame("LOST");
			} else {
				notifyObservers(gameState);	
			}
		}
	}
	//validated
	
	public void prepareNextStep() {
		//TODO: Control case when is the last round!
		if (players.size() == 0) {
			endGame("LOST");
			return;
		}
		nextPlayer();
		if (players.size() == 1) {
			
			//endGame(players.get(currentPlayeridx).getName());
			nextRound();
			return;
		}
		if (currentPlayeridx == 0) {
			nextRound();
		}
	}
	
	//validated
	public void endGame(String winner) {
		
		gameState = "EndGame/"+winner;
		
		notifyObservers(gameState);
		
	}
	
	private void setNewProblem(int newProb) {
		problem = ProblemFactory.getProblem(newProb);
	}
	
	public void setupNewGame(int newProb) {
		if (players.isEmpty()) {
			endGame("LOST");
			return;
		}
		currentPlayeridx = 0;
		round = 0;
		setNewProblem(newProb);
	}
}
