package controller.dashboard;

import controller.Controller;
import javafx.event.ActionEvent;
import model.SendReceiveTransaction;
import model.TransactionType;
import model.UserDetails;

public class SendController extends Controller
{
	private String receiver;
	private double winning;
	
	public SendController (String receiver, double winning){
		this.receiver = receiver;
		this.winning = winning;
		SendReceiveTransaction transaction = new SendReceiveTransaction(0, getModel().getSessionDetails().getUsername(), TransactionType.Send, receiver, winning, getModel().getSessionStats().getCurrentEarnings()-winning, null);
		this.getModel().getCloudDatabase().executeTransaction(transaction);
	}

	@Override
	public void handle(ActionEvent event) {		
	}
}
