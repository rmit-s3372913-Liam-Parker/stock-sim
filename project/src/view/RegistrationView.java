package view;

import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * RegistrationView contains any UI code relevant to the registration screen.
 * */
public class RegistrationView extends BorderPane
{	
	public RegistrationView()
	{
		populate();
	}
	
	/**
	 * Builds the UI for this pane. UI elements and
	 * any call-backs for functionality should be registered
	 * here.   
	 * */
	public void populate()
	{
		// Setup title
		Text loginTitle = new Text("Registration Screen");
		loginTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		setCenter(loginTitle);
	}
}
