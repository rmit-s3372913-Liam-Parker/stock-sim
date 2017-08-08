package view;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * StockApplication serves as the main entry point for the program.
 * */
public class StockApplication extends Application 
{
	private static final String PROGRAM_NAME =  "ASX Sim";
	public static final int WINDOW_WIDTH = 1920/2;
	public static final int WINDOW_HEIGHT = 1080/2;
	
	private static Stage stage;
	
	@Override
	public void start(Stage stage) throws Exception 
	{
		StockApplication.stage = stage;
		
		// We begin the application on the login page.
		LoginView initialView = new LoginView();
		
		// Set frame data
		Scene scene = new Scene(initialView, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setTitle(PROGRAM_NAME);
        stage.setScene(scene);
        stage.show();
	}
	
	/**
	 * @return Access to the JavaFX stage object for changing views.
	 */
	public static Stage getMainStage()
	{
		return stage;
	}
	
	public static void main(String[] args) 
	{
		// Because we're using JavaFX, we must tell the application to launch.
		launch(args);
	}

}
