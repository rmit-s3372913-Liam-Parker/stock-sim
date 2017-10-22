package controller.admin;

import controller.Controller;
import javafx.event.ActionEvent;

public class DeletePlayerController extends Controller{

	int id;
	
	public DeletePlayerController(int id) {
		this.id = id;
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public String deletePlayer() {
		return getModel().deletePlayer(id);
	}

}
