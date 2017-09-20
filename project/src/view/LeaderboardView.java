package view;

import java.util.List;
import java.util.TimerTask;

import javax.swing.Timer;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.CoreAPI;
import model.PlayerStats;

public class LeaderboardView extends BorderPane 
{
	private final int DURATION = 10000;
	public LeaderboardView()
	{
		populate();
		final Timeline timeline = new Timeline(
		    new KeyFrame(
		        Duration.millis( DURATION ),
		        event -> {
		            populate();
		        }
		    )
		);
		timeline.setCycleCount( Animation.INDEFINITE );
		timeline.play();
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
