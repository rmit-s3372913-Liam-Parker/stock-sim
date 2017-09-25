package view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableStringValue;
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
	Text earningsText = new Text("$0000000");
	
	HBox btnBox = new HBox();
	Button curStocksBtn = new Button("Current Stocks");
	Button transactionBtn = new Button("Transaction History");
	
	final ListView<PlayerStats> statsList = new ListView<>();
	
	private static DoubleProperty currentEarnings = new SimpleDoubleProperty();
	
	public PlayerStatsView()
	{
		populate();
		
		// HACK:
		PlayerStatsView.SetCurrentEarnings(StockApplication.getModel().getCloudDatabase().getCurrentPlayerStats(
				StockApplication.getModel().getSessionDetails()).getCurrentEarnings());
		
		earningsText.textProperty().bind(Bindings.convert(currentEarnings));
	}
	
	private void populate()
	{
		mainTitle.setFont(StockApplication.APP_HEADING_FONT);
		earningsTitle.setFont(StockApplication.APP_DETAIL_FONT);
		BorderPane.setAlignment(mainBox, Pos.CENTER);
		
		btnBox.getChildren().addAll(curStocksBtn, transactionBtn);
		btnBox.setAlignment(Pos.CENTER);
		btnBox.setSpacing(5.0f);
		
		mainBox.getChildren().addAll(mainTitle, earningsTitle, earningsText, btnBox, statsList);
		mainBox.setSpacing(3.0f);
		
		this.setCenter(mainBox);
	}
	
	public static void SetCurrentEarnings(double earnings)
	{
		currentEarnings.set(earnings);
	}
}
