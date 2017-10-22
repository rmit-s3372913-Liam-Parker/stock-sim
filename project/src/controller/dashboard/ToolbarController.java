package controller.dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Message;
import javafx.scene.control.*;
import ultilities.NumberField;
import view.*;

/**
 * Responsible for handling interaction with the tool-bar on the main dash-board.
 */
public class ToolbarController extends Controller
{
	private static final String LOG_OUT_CONFIRMATION_MESSAGE = "Are you sure you want to log out?";

	private Stage friendsWindow = new Stage();
	private DashboardView view;
	
	public ToolbarController(DashboardView view)
	{
		this.view = view;

		friendsWindow.setTitle("Friends");
		friendsWindow.setScene(new Scene(new FriendsView(), 600, 450));
		friendsWindow.setResizable(false);
	}
	
	//check which button was pressed then execute appropriate function
	@Override
	public void handle(ActionEvent event) 
	{
		if(event.getSource() == view.getLogOutButton())
		{
			if(this.displayConfirmationModal("Logout Confirmation", LOG_OUT_CONFIRMATION_MESSAGE))
			{
				this.getModel().endSession();
				this.switchView(new LoginView());
			}
		}
		else if(event.getSource() == view.getFriendsButton())
		{
			displayFriendsWindow();
		}

		else if(event.getSource() == view.getNotificationsButton())
		{
			displayMessageModal();
		}
	}

	private void displayFriendsWindow()
	{
		if(!friendsWindow.isShowing())
			friendsWindow.show();
		else
			friendsWindow.toFront();
	}
	
	private void displayMessageModal()
	{
		// Create frame for modal window
		Stage dialog = new Stage();
		dialog.setResizable(false);
		dialog.setTitle("Send Message");
		
		BorderPane pane = new BorderPane();
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(10.0f);
		HBox btnBox = new HBox();
		btnBox.setAlignment(Pos.CENTER);
		btnBox.setSpacing(5.0f);
		Scene scene = new Scene(pane, 600, 300);
		
		
		//setting up label
		Label message = new Label("Message");
		Label receiver = new Label("With");
		Text alert = new Text();

		//add user search table
		List<Message> messages = getModel().getMessages();
		Map<Integer, List<Message>> conversation = new HashMap<Integer, List<Message>>();
		for (int i=0; i<messages.size(); i++)
		{
			List<Message> list = new ArrayList<Message>();
			if (messages.get(i).getReceiverUserId()==getModel().getSessionDetails().getUserId())
			{
				if (conversation.get(messages.get(i).getSenderUserId())!=null)
					list = conversation.get(messages.get(i).getSenderUserId());
				list.add(messages.get(i));
				conversation.put(messages.get(i).getSenderUserId(), list);
			}
			else
			{
				if (conversation.get(messages.get(i).getReceiverUserId())!=null)
					list = conversation.get(messages.get(i).getReceiverUserId());
				list.add(messages.get(i));
				conversation.put(messages.get(i).getReceiverUserId(), list);
			}
		}
		MessageView view = null;
		for (Integer key : conversation.keySet())
		{
			view = new MessageView(conversation.get(key));
		}
		vBox.getChildren().addAll(alert, receiver, message, view);
		pane.setCenter(vBox);
		
		
		// Configure modal functionality and display
		dialog.setScene(scene);
		dialog.initOwner(this.getStage());
		dialog.initModality(Modality.APPLICATION_MODAL); 
		dialog.showAndWait();
	}
}
