package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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
}
