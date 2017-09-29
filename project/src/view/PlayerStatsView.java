package view;

import controller.playerstats.PlayerStatsController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.UserDetails;

/**
 * PlayerStatsView contains all UI related code for the stats view in the bottom left of the dashboard.
 * @author newel
 *
 */
public class PlayerStatsView extends BorderPane 
{
	private VBox mainBox = new VBox();
	private Text mainTitle = new Text("My Stats");
	private Text earningsTitle = new Text("Current Earnings:");
	private Text earningsText = new Text("$0000000");
	
	private HBox btnBox = new HBox();
	private ToggleButton curStocksBtn = new ToggleButton("Current Stocks");
	private ToggleButton transactionBtn = new ToggleButton("Transaction History");
	
	ObservableList<String> listItems = FXCollections.observableArrayList();
	private final ListView<String> statsList = new ListView<>(listItems);
	
	private final PlayerStatsController controller = new PlayerStatsController(listItems, transactionBtn, curStocksBtn);
	
	private static DoubleProperty currentEarnings = new SimpleDoubleProperty();
	
	public PlayerStatsView()
	{
		populate();
		
		// TODO: Find a better way to do this
		UserDetails details = StockApplication.getModel().getSessionDetails();
		PlayerStatsView.SetCurrentEarnings(StockApplication.getModel()
				.getCurrentPlayerStats(details).getCurrentEarnings());
		
		earningsText.textProperty().bind(Bindings.convert(currentEarnings));
	}
	
	/**
	 * Builds the UI for this pane. UI elements and
	 * any call-backs for functionality should be registered
	 * here.   
	 * */
	private void populate()
	{
		mainTitle.setFont(StockApplication.APP_HEADING_FONT);
		earningsTitle.setFont(StockApplication.APP_DETAIL_FONT);
		BorderPane.setAlignment(mainBox, Pos.CENTER);
		
		final ToggleGroup toggleGroup = new ToggleGroup();
		curStocksBtn.setToggleGroup(toggleGroup);
		curStocksBtn.setSelected(true);
		curStocksBtn.setOnAction(controller);
		transactionBtn.setToggleGroup(toggleGroup);
		transactionBtn.setOnAction(controller);
		
		btnBox.getChildren().addAll(curStocksBtn, transactionBtn);
		btnBox.setAlignment(Pos.CENTER);
		btnBox.setSpacing(5.0f);
		
		mainBox.getChildren().addAll(mainTitle, earningsTitle, earningsText, btnBox, statsList);
		mainBox.setSpacing(3.0f);
		
		this.setCenter(mainBox);
	}
	
	/**
	 * Sets the current earning amount visible to the player.
	 * @param earnings
	 */
	public static void SetCurrentEarnings(double earnings)
	{
		currentEarnings.set(earnings);
	}
}
