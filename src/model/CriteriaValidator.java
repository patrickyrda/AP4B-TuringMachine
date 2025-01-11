package model;


public class CriteriaValidator implements CriteriaValidatorIf{
	private final int criteriaNumber;
	private final String validatorDescription;
	private final CriteriaValidatorIf criteria;
	
	CriteriaValidator(int criteriaNumber, String description, CriteriaValidatorIf criteria) {
		this.criteriaNumber = criteriaNumber;
		validatorDescription = description;
		this.criteria = criteria;
	}

	@Override
	public boolean validate(int guess) {
		return criteria.validate(guess);
	}
	
	@Override
	public String getDescription() {
		return validatorDescription;
	}
	
	public int getCriteriaNumber() {
		return criteriaNumber;
	}
}
