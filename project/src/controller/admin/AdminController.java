package controller.admin;

import controller.Controller;
import javafx.event.ActionEvent;
import view.AdminLoginView;
import view.LoginView;

public class AdminController extends Controller{

	private static final String LOG_OUT_CONFIRMATION_MESSAGE = "Are you sure you want to log out?";
	int id;
	
	public AdminController() {
		id = 0;
	}
	
	public AdminController(int id) {
		this.id = id;
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * Logout User and expire session
	 * */
	public void logoutUser() {
		if(this.displayConfirmationModal("Logout Confirmation", LOG_OUT_CONFIRMATION_MESSAGE))
		{
			this.getModel().endSession();
			this.switchView(new LoginView());
		}
	}
	
	/*
	 * Delete PlayerFrom Database
	 * */
	public String deletePlayer() {
		return getModel().deletePlayer(id);
	}

}
