package view;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PlayerStatsView extends BorderPane 
{
	Text title = new Text("My Stats");
	Text earnings = new Text();
	
	public PlayerStatsView()
	{
		populate();
	}
	
	private void populate()
	{
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		this.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);
		
		this.setCenter(earnings);
	}
}
