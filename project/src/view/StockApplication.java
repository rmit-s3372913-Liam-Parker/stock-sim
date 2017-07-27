package view;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/*
 * StockApplication serves as the main entry point to the program.
 * */
public class StockApplication extends Application 
{
	private static final String PROGRAM_NAME =  "ASX Simulator";
	private static final int WINDOW_WIDTH = 1920/2;
	private static final int WINDOW_HEIGHT = 1080/2;
	
	@Override
	public void start(Stage stage) throws Exception 
	{
		// We begin the application on the login page.
		LoginView login = new LoginView();
		
		// Set frame data
		Scene scene = new Scene(login, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setTitle(PROGRAM_NAME);
        stage.setScene(scene);
        stage.show();
	}

	public static void main(String[] args) 
	{
		// Because we're using JavaFX, we must tell the application to launch.
		launch(args);
	}

}