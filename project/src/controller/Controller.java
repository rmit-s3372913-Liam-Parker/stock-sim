package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.CoreAPI;
import view.StockApplication;

public abstract class Controller implements EventHandler<ActionEvent> 
{
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
		dialog.setTitle("Notification");
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		Scene scene = new Scene(pane, 300, 150);
				
		// Configure options and add them
		Button btn = new Button("OK");
		
		btn.setOnAction(new EventHandler<ActionEvent>() 
		{
		    @Override public void handle(ActionEvent e) 
		    {
		        dialog.hide();
		    }
		});
		
		pane.add(new Text(message), 0, 0);
		pane.add(btn,               0, 1);
					
		// Configure modal functionality and display
		dialog.setScene(scene);
		dialog.initOwner(this.getStage());
		dialog.initModality(Modality.APPLICATION_MODAL); 
		dialog.showAndWait();
	}
}
