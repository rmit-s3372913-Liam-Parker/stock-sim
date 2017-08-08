package view;

import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * DashboardView contains any UI code relevant to the dash board screen.
 * */
public class DashboardView extends BorderPane 
{
	public DashboardView()
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
		Text loginTitle = new Text("Dashboard View");
		loginTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		setCenter(loginTitle);
	}
}
