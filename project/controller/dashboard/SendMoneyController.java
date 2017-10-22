package controller.dashboard;

import java.util.Date;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.MoneyTransaction;
import model.TransactionType;
import model.UserDetails;

public class SendMoneyController extends Controller
{
	final private int NOT_USED = 0;
	final private String NOT_ENOUGH = "Not enough winning";
	
	public SendMoneyController (Stage dialog, Text alert, String receiverUsername, double winning)
	{
		int transactionId = NOT_USED;
		String sender = getModel().getSessionDetails().getUsername();
		double currentWinning = getModel().getSessionStats().getCurrentEarnings();
		double postWinning = currentWinning - winning;
		//will be generated by database
		Date time = null;
		
		String error;
		
		MoneyTransaction sendTransaction =
				new MoneyTransaction(transactionId,
							sender, 
							TransactionType.Send,
							getModel().getPlayerUserId(new UserDetails(receiverUsername, null)),
							receiverUsername, 
							winning,
							postWinning,
							time);
		
		//check if sending more winning than available
		if (postWinning<0)
		{
			alert.setText(NOT_ENOUGH);
			return;
		}
		
		//store the transaction on cloud database
		if ((error = getModel().executeTransaction(sendTransaction)) == null)
			dialog.hide();
		else
			alert.setText(error);
	}

	@Override
	public void handle(ActionEvent event) {		
	}
}
