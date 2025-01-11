package model;

public interface CriteriaValidatorIf {
	
	boolean validate(int guessvalue);
	
	default String getDescription() {
		return "This is the default Criteria Validator";
	}
}
