package view;

import controller.dashboard.ToolbarController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * DashboardView contains any UI code relevant to the dash board screen.
 * */
public class DashboardView extends BorderPane 
{
	// Each sub section of the dashboard is contained within these classes.
	private StockView stockView = new StockView();
	private LeaderboardView leaderboardView = new LeaderboardView();
	private PlayerStatsView statsView = new PlayerStatsView();
	private CompanyView companyView = new CompanyView();
	
	private static final float PADDING = 3.5f;
	
	public DashboardView()
	{
		companyView.addStockObserver(stockView);
		populate();
	}
	
	/**
	 * Builds the UI for this pane. UI elements and
	 * any call-backs for functionality should be registered
	 * here.   
	 * */
	private void populate()
	{
		// Populate tool bar
		Button logout = new Button("Log out");
		logout.setOnAction(new ToolbarController());
		
		ToolBar toolBar = new ToolBar(logout);
		
		// Populate main dashboard UI
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(PADDING));
		gridPane.setAlignment(Pos.TOP_LEFT);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(75);
        
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(50);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(50);
        
        gridPane.getColumnConstraints().addAll(col1,col2);
        gridPane.getRowConstraints().addAll(row1, row2);
        
		// Setup title
        gridPane.add(leaderboardView, 0, 0);
        gridPane.add(statsView,       0, 1);
        gridPane.add(stockView,       1, 0);
        gridPane.add(companyView,     1, 1);
		
        this.setTop(toolBar);
        this.setCenter(gridPane);
	}
}
