package controller.dashboard;

import java.util.List;
import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;
import ultilities.NumberField;
import view.DashboardView;
import view.LoginView;

/**
 * Responsible for handling interaction with the tool-bar on the main dash-board.
 */
public class ToolbarController extends Controller
{
	private static final String LOG_OUT_CONFIRMATION_MESSAGE = "Are you sure you want to log out?";
	private static final String NO_FRIEND = "No friend was found or server error, please try again";
	
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
				displaySendMessageModal();
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
		
		//setting up input field and select box
		NumberField.numberField(winningField);
		ComboBox<String> friendUsername = new ComboBox<String>();
		friendUsername.setVisibleRowCount(3);
		
		//get list of user from database
		List<String> friend = getModel().getFriends(getModel().getSessionDetails().getUsername());
		
		//setting up label
		Label winning = new Label("Winning");
		Label receiver = new Label("Receiver");
		Text alert = new Text();

		//check if user list is empty
		if (!friend.isEmpty())
		{
			//insert username into slect box
			int count = 0;
			friendUsername.setValue(friend.get(count));
			do 
				friendUsername.getItems().add(friend.get(count++));
			while(friend.size()>count);
		}
		else
		{
			//alert the user
			alert.setText(NO_FRIEND);
			Button cancelButton = new Button("Cancel");
			cancelButton.setOnAction(new EventHandler<ActionEvent>() 
			{
				@Override 
				public void handle(ActionEvent e) 
				{
					dialog.hide();
				}
			});
			
			btnBox.getChildren().addAll(cancelButton);
			vBox.getChildren().addAll(alert, btnBox);
			pane.setCenter(vBox);
			
			
			// Configure modal functionality and display
			dialog.setScene(scene);
			dialog.initOwner(this.getStage());
			dialog.initModality(Modality.APPLICATION_MODAL); 
			dialog.showAndWait();
			return;
		}
		
		// Configure options and add them
		Button sendButton = new Button("Send");
		sendButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override 
			public void handle(ActionEvent e) 
			{
				new SendMoneyController(dialog, alert, friendUsername.getValue(), Double.parseDouble(winningField.getText()));
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
		
		//setting up input field and select box
		ComboBox<String> friendUsername = new ComboBox<String>();
		friendUsername.setVisibleRowCount(3);
		
		//get list of user from database
		List<String> friend = getModel().getFriends(getModel().getSessionDetails().getUsername());
		
		//setting up label
		Label message = new Label("Message");
		Label receiver = new Label("Receiver");
		Text alert = new Text();

		//check if user list is empty
		if (!friend.isEmpty())
		{
			//insert username into slect box
			int count = 0;
			friendUsername.setValue(friend.get(count));
			do 
				friendUsername.getItems().add(friend.get(count++));
			while(friend.size()>count);
		}
		else
		{
			//alert the user
			alert.setText(NO_FRIEND);
			Button cancelButton = new Button("Cancel");
			cancelButton.setOnAction(new EventHandler<ActionEvent>() 
			{
				@Override 
				public void handle(ActionEvent e) 
				{
					dialog.hide();
				}
			});
			
			btnBox.getChildren().addAll(cancelButton);
			vBox.getChildren().addAll(alert, btnBox);
			pane.setCenter(vBox);
			
			
			// Configure modal functionality and display
			dialog.setScene(scene);
			dialog.initOwner(this.getStage());
			dialog.initModality(Modality.APPLICATION_MODAL); 
			dialog.showAndWait();
			return;
		}
		
		// Configure options and add them
		Button sendButton = new Button("Send");
		sendButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override 
			public void handle(ActionEvent e) 
			{
				new SendMessageController(dialog, alert, friendUsername.getValue(), messageField.getText());
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
}
