package ultilities;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NumberField {
	public static void numberField(TextField numfield)
	{
		numfield.textProperty().addListener(new ChangeListener<String>() {
			@Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	            	numfield.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    });
	}
}
