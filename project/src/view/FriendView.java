package view;

import java.util.List;
import interfaces.CoreAPI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class FriendView extends BorderPane
{
	private static final String NO_USER = "No non-friend user was found or server error, please try again";
	private static final String NO_FRIEND = "No friend was found or server error, please try again";
	VBox verticalLayout = new VBox();
	
	// List objects for filtering and display of list view items.
	ObservableList<String> usernameList = FXCollections.observableArrayList();
	FilteredList<String> filteredUsernameList = new FilteredList<>(usernameList);
	ListView<String> displayUsernameList = new ListView<>(filteredUsernameList);
	
	private String userChangeListeners;
	
	public FriendView(boolean friend)
	{
		populate(friend);
	}
	
	private void populate(boolean friend)
	{
		// Build friend list view element.
		CoreAPI core = StockApplication.getModel();
		List<String> username;
		if (friend)
			username = core.getFriends();
		else
			username = core.getFriendRequest();
		if (username.isEmpty())
			if (!friend)
				this.setCenter(new Text(NO_USER));
			else
				this.setCenter(new Text(NO_FRIEND));
		else
		{
			for(int i = 0; i < username.size(); ++i)
			{
				String curUsername = username.get(i);
				usernameList.add(curUsername);
			}
			
			// Setup friend selected listener
			displayUsernameList.setEditable(false);
			displayUsernameList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() 
			{
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) 
			    {
		        	userChangeListeners = newVal;
			    }
			});
			
			// Setup search bar.
			HBox filterBar = new HBox();
			
			TextField searchField = new TextField();
			searchField.textProperty().addListener(new ChangeListener<String>() 
			{
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) 
			    {
			    	String search = searchField.getText().toLowerCase();
			    	
			    	if(newVal.trim().isEmpty())
			    	{
			    		filteredUsernameList.setPredicate(s -> true);
			    	}
			    	else
			    	{
		    			filteredUsernameList.setPredicate(s -> s.toLowerCase().contains(search));
			    	}
			    }
			});
			
			HBox.setHgrow(searchField, Priority.ALWAYS);
			
			filterBar.getChildren().addAll(searchField);
			filterBar.setSpacing(5.0f);
			
			// Embed into main view.
			verticalLayout.getChildren().addAll(filterBar, displayUsernameList);
			this.setCenter(verticalLayout);
		}
	}
	
	public String selected()
	{
		return userChangeListeners;
	}
}
