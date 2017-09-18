package view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import interfaces.StockSelectedCallback;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.CompanyInfo;
import model.CoreAPI;
import model.SortType;

public class CompanyView extends BorderPane
{
	Text title = new Text("Available Companies");
	VBox verticalLayout = new VBox();
	
	// List objects for filtering and display of list view items.
	SortType sortingType = SortType.Ascending;
	ObservableList<CompanyInfo> availableCompanies = FXCollections.observableArrayList();
	FilteredList<CompanyInfo> filteredCompanies = new FilteredList<>(availableCompanies);
	ListView<CompanyInfo> companyList = new ListView<>(filteredCompanies);
	
	// Callbacks for when a new stock object is called.
	List<StockSelectedCallback> stockChangeListeners = new ArrayList<>();
	
	private static final String ORDER_BTN_ASCEND = "Ascending";
	private static final String ORDER_BTN_DESCEND = "Descending";
	
	public CompanyView()
	{
		populate();
	}
	
	private void populate()
	{
		// Create title element
		title.setFont(StockApplication.APP_HEADING_FONT);
		verticalLayout.setSpacing(3.0f);
		
		// Build company list view element.
		CoreAPI core = StockApplication.getModel();
		List<CompanyInfo> info = core.getMarketInterface().getCurrentCompanyList();
		
		for(int i = 0; i < info.size(); ++i)
		{
			CompanyInfo curCompany = info.get(i);
			availableCompanies.add(curCompany);
		}
		
		//companyList = new ListView<String>(availableCompanies);
		
		// Setup company selected listener
		companyList.setEditable(false);
		companyList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CompanyInfo>() 
		{
		    @Override
		    public void changed(ObservableValue<? extends CompanyInfo> observable, CompanyInfo oldVal, CompanyInfo newVal) 
		    {
		        // Notify any listeners of stock change.
		        for(int i = 0; i < stockChangeListeners.size(); ++i)
		        {
		        	stockChangeListeners.get(i).stockSelected(newVal);
		        }
		    }
		});
		
		// Setup search bar.
		HBox filterBar = new HBox();
		Text filterTitle = new Text("Filter by:");
		
		final ToggleGroup toggleGroup = new ToggleGroup();
		final ToggleButton codeFilterBtn = new ToggleButton("Code");
		codeFilterBtn.setToggleGroup(toggleGroup);
		codeFilterBtn.setSelected(true);
		final ToggleButton nameFilterBtn = new ToggleButton("Name");
		nameFilterBtn.setToggleGroup(toggleGroup);
		
		TextField searchField = new TextField();
		searchField.textProperty().addListener(new ChangeListener<String>() 
		{
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) 
		    {
		    	String search = searchField.getText().toLowerCase();
		    	
		    	if(newVal.trim().isEmpty())
		    	{
		    		filteredCompanies.setPredicate(s -> true);
		    	}
		    	else
		    	{
		    		if(codeFilterBtn.isSelected())
		    		{
		    			filteredCompanies.setPredicate(s -> s.getStockCode().toLowerCase().contains(search));
		    		}
		    		else if(nameFilterBtn.isSelected())
		    		{
		    			filteredCompanies.setPredicate(s -> s.getCompanyName().toLowerCase().contains(search));
		    		}
		    	}
		    }
		});
		
		HBox.setHgrow(searchField, Priority.ALWAYS);
		
		Button orderBtn = new Button("Ascending");
		
		filterBar.getChildren().addAll(filterTitle, codeFilterBtn, nameFilterBtn, searchField, orderBtn);
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
