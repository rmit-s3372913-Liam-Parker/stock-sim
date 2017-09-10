package view;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.CompanyInfo;
import model.CoreAPI;
import model.PlayerStats;

/**
 * DashboardView contains any UI code relevant to the dash board screen.
 * */
public class DashboardView extends GridPane 
{
	public DashboardView()
	{
		setAlignment(Pos.TOP_LEFT);
		setHgap(10);
		setVgap(10);
		populate();
	}
	
	/**
	 * Builds the UI for this pane. UI elements and
	 * any call-backs for functionality should be registered
	 * here.   
	 * */
	private void populate()
	{
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(75);
        
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(50);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(50);
        
        this.getColumnConstraints().addAll(col1,col2);
        this.getRowConstraints().addAll(row1, row2);
        
		// Setup title
        buildLeaderboardView();
        buildStockView();
        buildCompanyView();
		
	}
	
	private void buildLeaderboardView()
	{
		VBox verticalLayout = new VBox();
		
		Text title = new Text("Leaderboard (MOCK DATA)");
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		
		ObservableList<String> leaderboardList = FXCollections.observableArrayList();
		CoreAPI core = StockApplication.getModel();
		List<PlayerStats> info = core.getPlayerList();
		
		for(int i = 0; i < Math.min(10, info.size()); ++i)
		{
			PlayerStats curPlayer = info.get(i);
			leaderboardList.add("Undefined Player - " + curPlayer.getCurrentEarnings());
		}
		
		final ListView<String> leaderList = new ListView<String>(leaderboardList);
		leaderList.setEditable(false);
		
		// Embed into main view.
		verticalLayout.getChildren().addAll(title, leaderList);
		add(verticalLayout, 0, 0);
	}
	
	
	private void buildStockView()
	{
		VBox verticalLayout = new VBox();
		
		Text title = new Text("Selected Stock");
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		
		// Embed into main view.
		verticalLayout.getChildren().addAll(title);
		add(verticalLayout, 1, 0);
	}
	
	private void buildCompanyView()
	{
		VBox verticalLayout = new VBox();
		
		// Create title element
		Text title = new Text("Available Companies");
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		
		// Build company list view element.
		ObservableList<String> availableCompanies = FXCollections.observableArrayList();
		CoreAPI core = StockApplication.getModel();
		List<CompanyInfo> info = core.getMarketInterface().getCurrentCompanyList();
		
		for(int i = 0; i < info.size(); ++i)
		{
			CompanyInfo curCompany = info.get(i);
			availableCompanies.add(curCompany.getStockCode() + " - " + curCompany.getCompanyName());
		}
		
		final ListView<String> companyList = new ListView<String>(availableCompanies);
		companyList.setEditable(false);
		
		// Setup company selected listener
		companyList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() 
		{
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) 
		    {
		    	String[] tokens = newVal.split(" -");
		        String code = tokens[0];
		    }
		});
		
		// Setup search bar.
		HBox filterBar = new HBox();
		Text filterTitle = new Text("Filter by:");
		Button filterBtnOne = new Button("Code");
		Button filterBtnTwo = new Button("Name");
		TextField searchField = new TextField();
		HBox.setHgrow(searchField, Priority.ALWAYS);
		Button orderBtn = new Button("Ascending");
		filterBar.getChildren().addAll(filterTitle, filterBtnOne, filterBtnTwo, searchField, orderBtn);
		filterBar.setSpacing(5.0f);
		
		// Embed into main view.
		verticalLayout.getChildren().addAll(title, filterBar, companyList);
		add(verticalLayout, 1, 1);
	}
}
