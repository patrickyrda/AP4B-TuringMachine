package model;
import java.util.ArrayList;


public class ProblemFactory {
	public static Problem getProblem(int prbNumber) {
		return switch(prbNumber) {
		case 1 -> new Problem(542, "This is the problem number 1 using criterias 1, 2 and 3", new ArrayList<CriteriaValidatorIf>() {{
																								add(CriteriaFactory.getValidator(3)); 
																								add(CriteriaFactory.getValidator(1)); 
																								add(CriteriaFactory.getValidator(2));}}, 1);
		default -> null;
		};
	}
}
