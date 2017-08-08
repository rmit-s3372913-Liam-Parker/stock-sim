package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.DashboardView;
import view.StockApplication;

public abstract class Controller implements EventHandler<ActionEvent> 
{
	protected void switchView(Pane newView)
	{
		Stage stage = StockApplication.getMainStage();
		stage.setScene(new Scene(new DashboardView(), StockApplication.WINDOW_WIDTH, StockApplication.WINDOW_HEIGHT));
	}
}
