package view;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import model.CoreAPI;
import model.PlayerStats;

public class LeaderboardView extends BorderPane 
{
	public LeaderboardView()
	{
		populate();
	}
	
	private void populate()
	{
		Text title = new Text("Leaderboard");
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		
		ObservableList<String> leaderboardList = FXCollections.observableArrayList();
		CoreAPI core = StockApplication.getModel();
		List<PlayerStats> info = core.getPlayerList();
		
		for(int i = 0; i < Math.min(10, info.size()); ++i)
		{
			PlayerStats curPlayer = info.get(i);
			leaderboardList.add(curPlayer.getUsername() + " - " + curPlayer.getCurrentEarnings());
		}
		
		final ListView<String> leaderList = new ListView<String>(leaderboardList);
		leaderList.setEditable(false);
		
		// Embed into main view.
		this.setTop(title);
		this.setCenter(leaderList);
		
	}
}
