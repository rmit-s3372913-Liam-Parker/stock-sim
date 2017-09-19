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
		title.setFont(StockApplication.APP_HEADING_FONT);
		
		CoreAPI core = StockApplication.getModel();
		ObservableList<PlayerStats> leaderboardList = FXCollections.observableArrayList();
		List<PlayerStats> info = core.getPlayerList();
		
		for(int i = 0; i < Math.min(10, info.size()); ++i)
		{
			leaderboardList.add(info.get(i));
		}
		
		final ListView<PlayerStats> leaderList = new ListView<>(leaderboardList);
		leaderList.setEditable(false);
		
		// Embed into main view.
		this.setTop(title);
		this.setCenter(leaderList);
		
	}
}
