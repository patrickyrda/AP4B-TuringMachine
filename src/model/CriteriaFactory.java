package model;

public class CriteriaFactory {
	public static CriteriaValidatorIf getValidator(int criteriaNumber) {
		return switch(criteriaNumber) {
        case 1 -> new CriteriaValidator(1, "This validator tests if the triangle number is even!", (val) -> iseventriangle(val));
        case 2 -> new CriteriaValidator(2, "This validator tests if the square number is smaller than 2, equals to it or bigger than it!", (val) -> comparetolt2square(val));
        case 3 -> new CriteriaValidator(3, "This validator tests if a specific shape is equal to 5", (val) -> equalto5circle(val));
        default -> throw new IllegalArgumentException("This Criteria Card toes not exist!");
        };
    }
	
	private static boolean comparetolt2square(int criteriaNumber) {
		//extracting square number
		int number = criteriaNumber % 10;
		return number < 2;
	}
	
	private static boolean  equalto5circle(int criteriaNumber) {
		//extracting circle number
		int number = criteriaNumber / 100;
		return number == 5;
	}
	
	private static boolean iseventriangle(int criteriaNumber) {
		//extracting triangle number
		int number = (criteriaNumber / 10) % 10;
		return number % 2 == 0;
	}
	
	
}
