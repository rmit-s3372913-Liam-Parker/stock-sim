package ultilities;

import java.util.regex.Matcher;

public class InputValidation {
	//validation for text field input
	public static boolean inputValidation(String input){
		//pattern for input: number, letter a to z in upper and lower case, symbols ',', '.', ';', ':', '_', '?'
		String inputPattern = "^[0-9a-zA-Z,.;:?_-escape$]{0,15}$";
		//replace 'escape' string with actual escape symbol '\' to avoid special meaning in String 
		inputPattern.replace("escape", Matcher.quoteReplacement("\\"));
		//check if input is allowed then return the result
		return input.matches(inputPattern);
	}
	
	public static boolean emailValidation(String email){
		//pattern for email field input
		String emailPattern = "^[0-9a-zA-Z][0-9a-zA-Z._]*@[0-9a-zA-Z]+([.][a-zA-Z]+)+$";
		//check if email is allowed then return the result
		return email.matches(emailPattern);
	}
	

	public static boolean pinValidation(String input){
		//pattern for pin field input
		String inputPattern = "^[0-9]{5}$";
		//check if input is allowed then return the result
		return input.matches(inputPattern);
	}
}