package controller.dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import javafx.event.ActionEvent;
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
	}

	private void displayFriendsWindow()
	{
		if(!friendsWindow.isShowing())
			friendsWindow.show();
		else
			friendsWindow.toFront();
	}
}