package view;

import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.CoreAPI;
import model.PlayerStats;

public class LeaderboardView extends BorderPane 
{
	Text title = new Text("Leaderboard");
	CoreAPI core = StockApplication.getModel();
	ObservableList<PlayerStats> leaderboardList = FXCollections.observableArrayList();
	List<PlayerStats> info;
	final ListView<PlayerStats> leaderList = new ListView<>(leaderboardList);
	
	private final int DURATION = 10000;
	
	public LeaderboardView()
	{
		populate();
		final Timeline timeline = new Timeline(
		    new KeyFrame(
		        Duration.millis( DURATION ),
		        event -> {
		        	refreshLeaderboardList();
		        }
		    )
		);
		timeline.setCycleCount( Animation.INDEFINITE );
		timeline.play();
	}
	
	private void populate()
	{
		title.setFont(StockApplication.APP_HEADING_FONT);
		BorderPane.setAlignment(title, Pos.CENTER);
		leaderList.setEditable(false);
		
		// Embed into main view.
		this.setTop(title);
		this.setCenter(leaderList);
		
		refreshLeaderboardList();
		
	}
	
	private void refreshLeaderboardList()
	{
		info = core.getPlayerList();
		leaderboardList.clear();
		for(int i = 0; i < Math.min(10, info.size()); ++i)
		{
			leaderboardList.add(info.get(i));
		}
	}
}
