package model;

public class Player {
	private String name;
	private final int punchcardscode;
	private boolean thumbup = false;
	
	public Player(int punchcardscode, String name) {
		this.punchcardscode = punchcardscode;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPunchCode() {
		return punchcardscode;
	}
	
	public void setthumbup() {
		thumbup = true;
	}
	
	public void setthumbdown() {
		thumbup = false;
	}
	
	public boolean getThumb() {
		return thumbup;
	}
	
	public boolean askCriteria(CriteriaValidatorIf criteria) {
		return criteria.validate(punchcardscode);
	}
	
	public boolean takeGuess(Problem problem, int guess) {
		if (guess < 111 || guess > 555) {
			throw new IllegalArgumentException("Invalid guess valid, it should be between 111 and 555!");
		}
		return problem.guessCode(guess);
	}
	
}
