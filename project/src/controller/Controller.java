package controller;

import interfaces.CoreAPI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import view.StockApplication;

import java.util.Optional;

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
	 * @return The main application stage for or making other adjustments.
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
	
	protected void displayNotificationModal(final String header, final String message)
	{
		// Create our alert and set content
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Notification Window");
		alert.setHeaderText(header);
		alert.setContentText(message);

		// Ensure expected behaviour of window
		alert.setResizable(false);
		alert.initOwner(this.getStage());
		alert.initModality(Modality.APPLICATION_MODAL);

		// We show the modal and wait for input
		alert.showAndWait();
	}

	protected void displayErrorModal(final String header, final String message)
	{
		// Create our alert and set content
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Window");
		alert.setHeaderText(header);
		alert.setContentText(message);

		// Ensure expected behaviour of window
		alert.setResizable(false);
		alert.initOwner(this.getStage());
		alert.initModality(Modality.APPLICATION_MODAL);

		// We show the modal and wait for input
		alert.showAndWait();
	}

	protected boolean displayConfirmationModal(final String header, final String message)
	{
		// Create our alert and set content
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Window");
		alert.setHeaderText(header);
		alert.setContentText(message);

		// Ensure expected behaviour of window
		alert.setResizable(false);
		alert.initOwner(this.getStage());
		alert.initModality(Modality.APPLICATION_MODAL);

		// Create buttons for each of our expected inputs
		ButtonType okBtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(okBtn, cancelBtn);

		// We show the modal and wait for input
		Optional<ButtonType> result = alert.showAndWait();

		// We check input to the modal
		if(result.isPresent())
		{
			if (result.get() == okBtn) { return true; }
			else if (result.get() == cancelBtn) { return false; }
		}
		return false;
	}

	protected Optional<String> displayTextInputModal(final String header, final String message, final String defaultText)
	{
		// Create out dialog
		TextInputDialog dialog = new TextInputDialog(defaultText);
		dialog.setTitle("Input Window");
		dialog.setHeaderText(header);
		dialog.setContentText(message);

		// Handle response
		return dialog.showAndWait();
	}

	protected Optional<String> displayTextInputModal(final String header, final String message)
	{
		return displayTextInputModal(header, message, "");
	}

	protected void displayExceptionModal(final Exception ex, final String userReadableString)
	{
		//TODO: Implement a stack trace dialog for runtime debugging.
	}


}
