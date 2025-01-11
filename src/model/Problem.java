package model;
import java.util.List;


public class Problem {
	private final int problemNumber;
	private final int secretCode;
	private String problemDescription;  
	private List<CriteriaValidatorIf> criterias;
	
	
	Problem(int secretCode, String problemDescription, List<CriteriaValidatorIf> criterias, int problemNumber) {
		this.secretCode = secretCode;
		this.problemDescription = problemDescription;
		this.criterias = criterias;
		this.problemNumber = problemNumber;
	}
	
	public int getProblemNumber() {
		return problemNumber;
	}
	
	public String getDesc() {
		return problemDescription;
	}
	
	public CriteriaValidatorIf getCriteria(int idx) {
		if (idx > criterias.size() || idx < 0) {
			throw new IllegalArgumentException("Invalid index passed, it should be between 0 and "+criterias.size());
		}
		return criterias.get(idx);
	}
	
	public boolean guessCode(int guess) {
		return guess == secretCode;	
	}
	
	public int getSecCode() {
		return secretCode;
	}
	
	public int getCriteriaCount() {
		return criterias.size();
	}
	
}
