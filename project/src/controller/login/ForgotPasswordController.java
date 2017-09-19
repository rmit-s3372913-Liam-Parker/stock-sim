package controller.login;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.LoginView;

public class ForgotPasswordController extends Controller implements EventHandler<ActionEvent>{

	public ForgotPasswordController()
	{
		
	}
	
	private static final String CONFIRM_TEXT = "Submit";
	private static final String CANCEL_TEXT = "Cancel";
	private static final String CONFIRMATION_MSG = "";
	
	Text title = new Text(CONFIRMATION_MSG);
	Stage dialog;
	Button confirmBtn;
	Button submitBtn;
	
	@Override
	public void handle(ActionEvent event) 
	{
		Object src = event.getSource();
		
		if(src == confirmBtn)
		{
			this.getModel().endSession();
			this.switchView(new LoginView());
			dialog.hide();
		}
		else if(src == submitBtn)
		{
			dialog.hide();
		}
		else
		{
			// Create frame for modal window
			dialog = new Stage();
			HBox buttonBox = new HBox();
			BorderPane pane = new BorderPane();
			Scene scene = new Scene(pane, 300, 150);
			
			// Configure options and add them
			confirmBtn = new Button(CONFIRM_TEXT);
			confirmBtn.setOnAction(this);
			buttonBox.getChildren().add(confirmBtn);
			
			submitBtn = new Button(CANCEL_TEXT);
			submitBtn.setOnAction(this);
			buttonBox.getChildren().add(submitBtn);
			
			pane.setTop(title);
			pane.setCenter(buttonBox);
			
			// Configure modal functionality and display
			dialog.setScene(scene);
			dialog.initOwner(this.getStage());
			dialog.initModality(Modality.APPLICATION_MODAL); 
			dialog.showAndWait();
		
		}
	

}
}
