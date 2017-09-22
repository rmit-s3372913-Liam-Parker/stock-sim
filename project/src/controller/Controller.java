package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.CoreAPI;
import view.StockApplication;

public abstract class Controller implements EventHandler<ActionEvent> 
{
	private static final int POPUP_WIDTH = 300;
	private static final int POPUP_HEIGHT = 150;
	private boolean hasConfirmedDialog = false;
	
	/**
	 * Switches views in the application window.
	 * @param newView A class inheriting from JavaFX Pane used to represent the new view.
	 */
	protected void switchView(Pane newView)
	{
		Stage stage = StockApplication.getMainStage();
		stage.setScene(new Scene(newView, StockApplication.WINDOW_WIDTH, StockApplication.WINDOW_HEIGHT));
	}
	
	/**
	 * @return The main application stage for displaying modal windows or making other adjustments.
	 */
	protected Stage getStage()
	{
		return StockApplication.getMainStage();
	}
	
	/**
	 * @return Access to the application model.
	 */
	protected CoreAPI getModel()
	{
		return StockApplication.getModel();
	}
	
	protected void displayNotificationModal(String message)
	{
		// Create frame for modal window
		Stage dialog = new Stage();
		dialog.setResizable(false);
		dialog.setTitle("Notification");
		
		BorderPane pane = new BorderPane();
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(10.0f);
		
		Scene scene = new Scene(pane, POPUP_WIDTH, POPUP_HEIGHT);
				
		// Configure options and add them
		Button btn = new Button("OK");
		
		btn.setOnAction(new EventHandler<ActionEvent>() 
		{
		    @Override public void handle(ActionEvent e) 
		    {
		        dialog.hide();
		    }
		});
		
		vBox.getChildren().addAll(new Text(message), btn);
		pane.setCenter(vBox);
		
		// Configure modal functionality and display
		dialog.setScene(scene);
		dialog.initOwner(this.getStage());
		dialog.initModality(Modality.APPLICATION_MODAL); 
		dialog.showAndWait();
	}
	
	protected boolean displayQuestionModal(String message)
	{
		// Create frame for modal window
		Stage dialog = new Stage();
		dialog.setResizable(false);
		dialog.setTitle("User Confirmation");
		
		BorderPane pane = new BorderPane();
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(10.0f);
		HBox btnBox = new HBox();
		btnBox.setAlignment(Pos.CENTER);
		btnBox.setSpacing(5.0f);
		Scene scene = new Scene(pane, POPUP_WIDTH, POPUP_HEIGHT);
						
		// Configure options and add them
		Button okBtn = new Button("OK");
		okBtn.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override 
			public void handle(ActionEvent e) 
			{
				dialog.hide();
				hasConfirmedDialog = true;
			}
		});
				
		Button cancelBtn = new Button("Cancel");
		cancelBtn.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override 
			public void handle(ActionEvent e) 
			{
				dialog.hide();
				hasConfirmedDialog = false;
			}
		});
				
		btnBox.getChildren().addAll(okBtn, cancelBtn);
		vBox.getChildren().addAll(new Text(message), btnBox);
		pane.setCenter(vBox);
		
		
		// Configure modal functionality and display
		dialog.setScene(scene);
		dialog.initOwner(this.getStage());
		dialog.initModality(Modality.APPLICATION_MODAL); 
		dialog.showAndWait();

		return hasConfirmedDialog;
	}
}
