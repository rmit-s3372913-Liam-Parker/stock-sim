package controller.dashboard;

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
import javafx.scene.control.*;
import ultilities.NumberField;
import view.DashboardView;
import view.FriendView;
import view.LoginView;

/**
 * Responsible for handling interaction with the tool-bar on the main dash-board.
 */
public class ToolbarController extends Controller
{
	private static final String LOG_OUT_CONFIRMATION_MESSAGE = "Are you sure you want to log out?";
	
	private DashboardView view;
	public TextField winningField= new TextField();
	public TextField messageField= new TextField();
	
	public ToolbarController(DashboardView view)
	{
		this.view = view;
	}
	
	//check which button was pressed then execute appropriate function
	@Override
	public void handle(ActionEvent event) 
	{
		if(event.getSource() == view.getLogOutButton())
		{
			if(this.displayQuestionModal(LOG_OUT_CONFIRMATION_MESSAGE))
			{
				this.getModel().endSession();
				this.switchView(new LoginView());
			}
		} 
		else
		{
			if(event.getSource() == view.getSendMoneyButton())
			{
				displaySendMoneyModal();
			}
			else
			{
				if (event.getSource() == view.getSendMessageButton())
					displaySendMessageModal();
				else
					displaySendFriendRequestModal();
			}
		}
	}

	private void displaySendMoneyModal()
	{
		// Create frame for modal window
		Stage dialog = new Stage();
		dialog.setResizable(false);
		dialog.setTitle("Send Winning");
		
		BorderPane pane = new BorderPane();
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(10.0f);
		HBox btnBox = new HBox();
		btnBox.setAlignment(Pos.CENTER);
		btnBox.setSpacing(5.0f);
		Scene scene = new Scene(pane, POPUP_WIDTH, 600);
		
		//setting up input field
		NumberField.numberField(winningField);
		
		//add user search table
		FriendView friendUsername = new FriendView(true);
		
		//setting up label
		Label winning = new Label("Winning");
		Label receiver = new Label("Receiver");
		Text alert = new Text();

		
		// Configure options and add them
		Button sendButton = new Button("Send");
		sendButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override 
			public void handle(ActionEvent e) 
			{
				new SendMoneyController(dialog, alert, friendUsername.selected(), Double.parseDouble(winningField.getText()));
			}
		});
				
		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override 
			public void handle(ActionEvent e) 
			{
				dialog.hide();
			}
		});
				
		btnBox.getChildren().addAll(sendButton, cancelButton);
		vBox.getChildren().addAll(alert, receiver, friendUsername, winning, winningField, btnBox);
		pane.setCenter(vBox);
		
		
		// Configure modal functionality and display
		dialog.setScene(scene);
		dialog.initOwner(this.getStage());
		dialog.initModality(Modality.APPLICATION_MODAL); 
		dialog.showAndWait();
	}
	
	private void displaySendMessageModal()
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
		Scene scene = new Scene(pane, POPUP_WIDTH, 600);
		
		
		//setting up label
		Label message = new Label("Message");
		Label receiver = new Label("Receiver");
		Text alert = new Text();

		//add user search table
		FriendView friendUsername = new FriendView(true);
		
		// Configure options and add them
		Button sendButton = new Button("Send");
		sendButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override 
			public void handle(ActionEvent e) 
			{
				new SendMessageController(dialog, alert, friendUsername.selected(), messageField.getText());
			}
		});
				
		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override 
			public void handle(ActionEvent e) 
			{
				dialog.hide();
			}
		});
				
		btnBox.getChildren().addAll(sendButton, cancelButton);
		vBox.getChildren().addAll(alert, receiver, friendUsername, message, messageField, btnBox);
		pane.setCenter(vBox);
		
		
		// Configure modal functionality and display
		dialog.setScene(scene);
		dialog.initOwner(this.getStage());
		dialog.initModality(Modality.APPLICATION_MODAL); 
		dialog.showAndWait();
	}
	
	private void displaySendFriendRequestModal()
	{
		// Create frame for modal window
		Stage dialog = new Stage();
		dialog.setResizable(false);
		dialog.setTitle("Friend Request");
		
		BorderPane pane = new BorderPane();
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(10.0f);
		HBox btnBox = new HBox();
		btnBox.setAlignment(Pos.CENTER);
		btnBox.setSpacing(5.0f);
		Scene scene = new Scene(pane, POPUP_WIDTH, 600);
		
		//setting up label
		Label receiver = new Label("Username");
		Text alert = new Text();
		
		//add user search table
		FriendView userList = new FriendView(false);

		// Configure options and add them
		Button sendButton = new Button("Send");
		sendButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override 
			public void handle(ActionEvent e) 
			{
				new SendFriendRequestController(dialog, alert, userList.selected());
			}
		});
				
		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override 
			public void handle(ActionEvent e) 
			{
				dialog.hide();
			}
		});
				
		btnBox.getChildren().addAll(sendButton, cancelButton);
		vBox.getChildren().addAll(alert, receiver, userList, btnBox);
		pane.setCenter(vBox);
		
		// Configure modal functionality and display
		dialog.setScene(scene);
		dialog.initOwner(this.getStage());
		dialog.initModality(Modality.APPLICATION_MODAL); 
		dialog.showAndWait();
	}
}
