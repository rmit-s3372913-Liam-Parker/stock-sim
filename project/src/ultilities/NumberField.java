package ultilities;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NumberField {
	public static void numberField(TextField numfield)
	{
		//add a function to remove non-numbers and keep number at most 9 numbers
		numfield.textProperty().addListener(new ChangeListener<String>() 
		{
			@Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
			{
				//check if non-number exists
	            if (!newValue.matches("\\d")) 
	            {
	            	//remove non-numbers
	            	numfield.setText(newValue = newValue.replaceAll("[^\\d]", ""));
	            }
	            //check if length is more than 9
	            if (!newValue.matches("\\d{0,9}")) 
	            {
	            	//replace the text field with numbers before input
	            	numfield.setText(oldValue);
	            }
	        }
	    });
	}
}
