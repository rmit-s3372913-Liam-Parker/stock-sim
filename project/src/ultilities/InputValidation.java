package ultilities;

import java.util.regex.Matcher;

public class InputValidation {
	public static boolean inputValidation(String input){
		String inputPattern = "^[0-9a-zA-Z,.;:?_-escape$]{0,15}$";
		inputPattern.replace("escape", Matcher.quoteReplacement("\\"));
		//check if input is allowed
		return input.matches(inputPattern);
	}
	
	public static boolean emailValidation(String email){
		String emailPattern = "^[0-9a-zA-Z][0-9a-zA-Z._]*@[0-9a-zA-Z]+([.][a-zA-Z]+)+$";
		//check if email is allowed
		return email.matches(emailPattern);
	}
	

	public static boolean pinValidation(String input){
		String inputPattern = "^[0-9]{5}$";
		//check if input is allowed
		return input.matches(inputPattern);
	}
}