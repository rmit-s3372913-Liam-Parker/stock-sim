package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.PlayerStats;

public class PlayerStatsView extends BorderPane 
{
	VBox mainBox = new VBox();
	Text mainTitle = new Text("My Stats");
	Text earningsTitle = new Text("Current Earnings:");
	Text earnings = new Text("$0000000");
	
	HBox btnBox = new HBox();
	Button curStocksBtn = new Button("Current Stocks");
	Button transactionBtn = new Button("Transaction History");
	
	final ListView<PlayerStats> statsList = new ListView<>();
	
	public PlayerStatsView()
	{
		populate();
	}
	
	private void populate()
	{
		mainTitle.setFont(StockApplication.APP_HEADING_FONT);
		earningsTitle.setFont(StockApplication.APP_DETAIL_FONT);
		BorderPane.setAlignment(mainBox, Pos.CENTER);
		
		btnBox.getChildren().addAll(curStocksBtn, transactionBtn);
		btnBox.setAlignment(Pos.CENTER);
		btnBox.setSpacing(5.0f);
		
		mainBox.getChildren().addAll(mainTitle, earningsTitle, earnings, btnBox, statsList);
		mainBox.setSpacing(3.0f);
		
		this.setCenter(mainBox);
	}
}
