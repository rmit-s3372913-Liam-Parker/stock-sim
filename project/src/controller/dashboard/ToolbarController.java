package controller.dashboard;

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
import model.SendReceiveTransaction;
import model.TransactionType;
import view.DashboardView;
import view.LoginView;

/**
 * Responsible for handling interaction with the tool-bar on the main dash-board.
 */
public class ToolbarController extends Controller
{
	private static final String LOG_OUT_CONFIRMATION_MESSAGE = "Are you sure you want to log out?";
	
	private DashboardView view;
	private Text logoutTitle = new Text(LOG_OUT_CONFIRMATION_MESSAGE);
	private boolean send;
	public TextField winningField= new TextField();
	
	public ToolbarController(DashboardView view)
	{
		this.view = view;
	}
	
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
		} else if(!this.displaySendModal())
		{
			//error handling/message
		}
	}

	private boolean displaySendModal()
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
		
		ComboBox<String> comboBox = new ComboBox<String>();
		comboBox.getItems().addAll("n", "q");
		Label winning = new Label("Winning");
		Label receiver = new Label("Receiver");

		// Configure options and add them
		Button sendButton = new Button("Send");
		sendButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override 
			public void handle(ActionEvent e) 
			{
				dialog.hide();
				new SendController(comboBox.getValue(), Double.parseDouble(winningField.getText()));
				send = true;
			}
		});
				
		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override 
			public void handle(ActionEvent e) 
			{
				dialog.hide();
				send = false;
			}
		});
				
		btnBox.getChildren().addAll(sendButton, cancelButton);
		vBox.getChildren().addAll(new Text(), receiver, comboBox, winning, winningField, btnBox);
		pane.setCenter(vBox);
		
		
		// Configure modal functionality and display
		dialog.setScene(scene);
		dialog.initOwner(this.getStage());
		dialog.initModality(Modality.APPLICATION_MODAL); 
		dialog.showAndWait();

		return send;
	}
}
