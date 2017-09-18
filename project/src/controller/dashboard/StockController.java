package controller.dashboard;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.PlayerStats;
import model.Stock;
import model.TransactionType;
import model.UserDetails;

public class StockController extends Controller 
{
	Button buyBtn;
	Button sellBtn;
	TextField quantityField;
	
	Stock targetStock;
	
	double FAKE_EARNINGS = 10000.0;
	
	public StockController(Button buyBtn, Button sellBtn, TextField quantity)
	{
		this.buyBtn = buyBtn;
		this.sellBtn = sellBtn;
		this.quantityField = quantity;
	}
	
	@Override
	public void handle(ActionEvent event) 
	{
		TransactionType type = TransactionType.Buy;
		
		if(event.getSource() == buyBtn)
			type = TransactionType.Buy;
		else if(event.getSource() == sellBtn)
			type = TransactionType.Sell;
		
		try
		{
			UserDetails curUser = getModel().getSessionDetails();
			PlayerStats stats = getModel().getSessionStats();
			
			int quantity = Integer.parseInt(quantityField.getText());
			double transactionCost = quantity * targetStock.getLastPrice();
			
			//double postWinnings = stats.getCurrentEarnings() + transactionCost;
			double postWinnings = FAKE_EARNINGS + transactionCost;
			
			getModel().getCloudDatabase().insertTransaction(
					curUser,
					targetStock.getCode(),
					type,
					quantity,
					quantity * targetStock.getLastPrice(),
					postWinnings
					);
		}
		catch(NumberFormatException e) { e.printStackTrace(); }
		
	}
	
	public void setTargetStock(Stock stock)
	{
		targetStock = stock;
	}

}
