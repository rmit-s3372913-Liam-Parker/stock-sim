package view;

import java.util.ArrayList;
import java.util.List;

import interfaces.StockSelectedCallback;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.CompanyInfo;
import model.CoreAPI;

public class CompanyView extends BorderPane
{
	Text title = new Text("Available Companies");
	VBox verticalLayout = new VBox();
	
	ObservableList<String> availableCompanies = FXCollections.observableArrayList();
	ListView<String> companyList;
	List<StockSelectedCallback> stockChangeListeners = new ArrayList<>();
	
	public CompanyView()
	{
		populate();
	}
	
	private void populate()
	{
		// Create title element
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		verticalLayout.setSpacing(3.0f);
		
		// Build company list view element.
		CoreAPI core = StockApplication.getModel();
		List<CompanyInfo> info = core.getMarketInterface().getCurrentCompanyList();
		
		for(int i = 0; i < info.size(); ++i)
		{
			CompanyInfo curCompany = info.get(i);
			availableCompanies.add(curCompany.getStockCode() + " - " + curCompany.getCompanyName());
		}
		
		companyList = new ListView<String>(availableCompanies);
		companyList.setEditable(false);
		
		// Setup company selected listener
		companyList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() 
		{
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) 
		    {
		    	String[] tokens = newVal.split(" -");
		        String code = tokens[0];
		        
		        // Notify any listeners of stock change.
		        for(int i = 0; i < stockChangeListeners.size(); ++i)
		        {
		        	stockChangeListeners.get(i).stockSelected(code);
		        }
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
		verticalLayout.getChildren().addAll(filterBar, companyList);
		this.setTop(title);
		this.setCenter(verticalLayout);
	}
	
	/**
	 * Adds a stock change observer to the notifyList.
	 */
	public void addStockObserver(StockSelectedCallback callback)
	{
		stockChangeListeners.add(callback);
	}

}
